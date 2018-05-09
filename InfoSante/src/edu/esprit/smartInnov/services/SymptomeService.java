package edu.esprit.smartInnov.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.esprit.smartInnov.entites.Maladie;
import edu.esprit.smartInnov.entites.Symptome;
import edu.esprit.smartInnov.utils.ConnectionManager;

public class SymptomeService {

	
	private Connection cnx;
	private static final Logger LOGGER = Logger.getLogger(SymptomeService.class.getName());
	public SymptomeService() {
		cnx = ConnectionManager.getInstance().getCnx();
	}
	
	public void ajouter(Symptome s) {
		String addQuery = "INSERT INTO Symptome(libelle) VALUES (?)";
		LOGGER.log(Level.INFO, addQuery);
		try(PreparedStatement ps = cnx.prepareStatement(addQuery)){
			Long genKey = null;
			ps.setString(1, s.getDesignation());
			ps.executeUpdate();
			ResultSet genKeys = ps.getGeneratedKeys();
			while(genKeys.next()) {
				genKey=genKeys.getLong(1);
			}
			ajouterListMaladieToSymptome(genKey, s.getMaladies());
			LOGGER.log(Level.INFO, "symptome ajoutée!");
		} catch (SQLException e) {
			LOGGER.log(Level.INFO, "symptome non ajoutée!");
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}
	
	public void ajouterListMaladieToSymptome(Long idSymptome, List<Maladie> maladies) {
		String addQuery = "INSERT INTO maladie_symptome (idMaladie, idSymptome) VALUES (?, ?)";
		LOGGER.log(Level.INFO, addQuery);
		for(Maladie m : maladies) {
			try {
				PreparedStatement ps = cnx.prepareStatement(addQuery);
				ps.setLong(2, idSymptome);
				ps.setLong(1, m.getId());
				ps.executeUpdate();
				ps.close();
				LOGGER.log(Level.INFO, "ligne maladie symptome ajoutée!");
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "ligne maladie symptome non ajoutée!");
				LOGGER.log(Level.SEVERE, e.getMessage());
			}
		}
	}
	
	public List<Symptome> getListSymptomes(){
		List<Symptome> symptomes = new ArrayList<>();
		MaladieService service = new MaladieService();
		List<Maladie> maladies ;
		String searchQuery = "SELECT * FROM symptome";
		LOGGER.log(Level.INFO, searchQuery);
		try (Statement ps = cnx.createStatement();){
			ResultSet rs = ps.executeQuery(searchQuery);
			while (rs.next()) {
				Symptome s = new Symptome();
				s.setId(rs.getLong(1));
				s.setDesignation(rs.getString(2));
				maladies = service.getListMaladiesBySymptome(s);
				s.setMaladies(maladies);
				symptomes.add(s);
			}
			LOGGER.log(Level.INFO, symptomes.size()+" symptomes trouvées!!");
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error while getting symptomes by maladie");
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		return symptomes;
	}
	
	public List<Symptome> getListSymptomesByMaladie(Maladie m){
		List<Symptome> symptomes = new ArrayList<>();
		String searchQuery = "SELECT * FROM symptome WHERE id IN (SELECT idSymptome FROM maladie_symptome WHERE idMaladie = ?)";
		LOGGER.log(Level.INFO, searchQuery);
		try (PreparedStatement ps = cnx.prepareStatement(searchQuery);){
			ps.setLong(1, m.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Symptome s = new Symptome();
				s.setId(rs.getLong(1));
				s.setDesignation(rs.getString(2));
				symptomes.add(s);
			}
			LOGGER.log(Level.INFO, symptomes.size()+" symptomes trouvées!!");
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error while getting symptomes by maladie");
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		return symptomes;
	}
	
	public Symptome getSymptomeByDesignation(String des) {
		String searchQuery = "SELECT * FROM symptome WHERE libelle like '%"+des+"%'";
		try(Statement ps = cnx.createStatement()){
			LOGGER.log(Level.INFO, searchQuery);
			ResultSet rs = ps.executeQuery(searchQuery);
			if(rs != null) {
				while(rs.next()) {
					Symptome s = new Symptome();
					s.setId(rs.getLong(1));
					s.setDesignation(rs.getString(2));
					return s;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Symptome> getSymptomesByDesignation(String des) {
		String searchQuery = "SELECT * FROM symptome WHERE libelle like '%"+des+"%'";
		List<Symptome> symps = new ArrayList<>();
		try(Statement ps = cnx.createStatement()){
			LOGGER.log(Level.INFO, searchQuery);
			ResultSet rs = ps.executeQuery(searchQuery);
			if(rs != null) {
				while(rs.next()) {
					Symptome s = new Symptome();
					s.setId(rs.getLong(1));
					s.setDesignation(rs.getString(2));
					symps.add(s);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return symps;
	}
	
	public void supprimer(Symptome s) {
		String deleteQuery = "DELETE FROM symptome WHERE id = ?";
		try (PreparedStatement ps = cnx.prepareStatement(deleteQuery);){
			ps.setLong(1, s.getId());
			LOGGER.info(ps.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			LOGGER.severe(e.getMessage());
		}
	}
	
	public void update(Symptome s) {
		String updateQuery = "UPDATE symptome set libelle = ? WHERE id = ?";
		try (PreparedStatement ps = cnx.prepareStatement(updateQuery);){
			ps.setString(1, s.getDesignation());
			ps.setLong(2, s.getId());
			LOGGER.info(ps.toString());
			ps.executeUpdate();
			updateLigneSymptomeMaladie(s);
		} catch (SQLException e) {
			LOGGER.severe(e.getMessage());
		}
		
	}
	
	public void updateLigneSymptomeMaladie(Symptome s) {
		String deleteQuery = "DELETE FROM maladie_symptome WHERE idSymptome = ?";
		try (PreparedStatement ps = cnx.prepareStatement(deleteQuery);){
			ps.setLong(1, s.getId());
			ps.executeUpdate();
			ajouterListMaladieToSymptome(s.getId(), s.getMaladies());
		} catch (SQLException e) {
			LOGGER.severe(e.getMessage());
		}
	}
}
