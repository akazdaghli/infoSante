package edu.esprit.smartInnov.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.esprit.smartInnov.entites.Maladie;
import edu.esprit.smartInnov.entites.Medicament;
import edu.esprit.smartInnov.entites.Symptome;
import edu.esprit.smartInnov.utils.ConnectionManager;
import edu.esprit.smartInnov.vues.VMedicament;

public class MaladieService {
	
	Connection cnx ;
	private static final Logger LOGGER = Logger.getLogger(MaladieService.class.getName());
	public MaladieService() {
		cnx = ConnectionManager.getInstance().getCnx();
	}
	
	public  List<Maladie> getAll(){
		String searchQuery = "SELECT * FROM maladie";
		LOGGER.log(Level.INFO, searchQuery);
		List<Maladie> maladies = new ArrayList<>();
		try (Statement s = cnx.createStatement();
			ResultSet rs = s.executeQuery(searchQuery);){
			while(rs.next()) {
				Maladie m = new Maladie();
				m.setId(rs.getLong(1));
				m.setLibelle(rs.getString(2));
				maladies.add(m);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE,e.getMessage());
		}
		return maladies;
	}
	
	public void ajouter(Maladie m) {
		String addQuery = "INSERT INTO Maladie(libelle) VALUES (?)";
		try {
			PreparedStatement ps = cnx.prepareStatement(addQuery);
			ps.setString(1, m.getLibelle());
			LOGGER.log(Level.INFO, ps.toString());
			ps.executeUpdate();
			ps.close();
			LOGGER.log(Level.INFO,"maladie ajoutée!");
		} catch (SQLException e) {
			LOGGER.log(Level.INFO,"maladie non ajoutée!");
			LOGGER.log(Level.SEVERE,e.getMessage());
		}
	}
	
	public List<Maladie> getListMaladiesByMedicament(Medicament med){
		List<Maladie> maladies = new ArrayList<>();
		String searchQuery = "SELECT * FROM maladie WHERE id IN (SELECT idMaladie FROM medicament_maladie WHERE idMedicament = ?)";
		try (PreparedStatement ps = cnx.prepareStatement(searchQuery);){
			ps.setLong(1, med.getId());
			LOGGER.log(Level.INFO, ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Maladie m = new Maladie();
				m.setId(rs.getLong(1));
				m.setLibelle(rs.getString(2));
				maladies.add(m);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LOGGER.log(Level.SEVERE,e.getMessage());
		}
		return maladies;
	}
	
	public List<Maladie> getListMaladiesByVMedicament(VMedicament med){
		List<Maladie> maladies = new ArrayList<>();
		String searchQuery = "SELECT * FROM maladie WHERE id IN (SELECT idMaladie FROM medicament_maladie WHERE idMedicament = ?)";
		try (PreparedStatement ps = cnx.prepareStatement(searchQuery);){
			ps.setLong(1, med.getId());
			LOGGER.log(Level.INFO, ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Maladie m = new Maladie();
				m.setId(rs.getLong(1));
				m.setLibelle(rs.getString(2));
				maladies.add(m);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LOGGER.log(Level.SEVERE,e.getMessage());
		}
		return maladies;
	}
	
	public List<Maladie> getListMaladiesBySymptome(Symptome s){
		List<Maladie> maladies = new ArrayList<>();
		String searchQuery = "SELECT * FROM maladie WHERE id IN (SELECT idMaladie FROM maladie_symptome WHERE idSymptome = ?)";
		try (PreparedStatement ps = cnx.prepareStatement(searchQuery);){
			ps.setLong(1, s.getId());
			LOGGER.log(Level.INFO, ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Maladie m = new Maladie();
				m.setId(rs.getLong(1));
				m.setLibelle(rs.getString(2));
				maladies.add(m);
			}
			LOGGER.log(Level.INFO,maladies.size()+" maladies trouvées!!");
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE,e.getMessage());
		}
		return maladies;
	}
	
	public List<Maladie> getListMaladiesByListSymptomes(List<Symptome> symptomes){
		List<Maladie> maladies = new ArrayList<>();
		StringBuilder searchQuery = new StringBuilder("SELECT * FROM Maladie WHERE id IN(SELECT idMaladie FROM Maladie_symptome WHERE idSymptome IN (");
		for(Symptome symptome:symptomes) {
			 searchQuery.append(symptome.getId());
			 if(!symptome.getId().equals(symptomes.get(symptomes.size()-1).getId())){
				 searchQuery.append(" ,");
			 }
		}
		searchQuery.append("))");
		LOGGER.log(Level.INFO, searchQuery.toString());
		try(Statement st = cnx.createStatement()){
			ResultSet rs = st.executeQuery(searchQuery.toString());
			while(rs.next()) {
				Maladie m = new Maladie();
				m.setId(rs.getLong(1));
				m.setLibelle(rs.getString(2));
				maladies.add(m);
			}
			LOGGER.log(Level.INFO,maladies.size()+" maladies trouvés");
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE,e.getMessage());
		}
		return maladies;
	}
	
	public Map<Maladie, Double> calculerPourcentage(List<Symptome> symps){
		SymptomeService ss = new SymptomeService();
		List<Maladie> mals = getListMaladiesByListSymptomes(symps);
		Map<Maladie, Double> map = new HashMap<>();
		for(Maladie m : mals) {
			List<Symptome> symptomes = ss.getListSymptomesByMaladie(m);
			double nbrSympts = symps.size();
			double nbrSymptsEx = 0;
			for(Symptome s : symptomes) {
				if(symps.contains(s)) {
					nbrSymptsEx ++;
				}
			}
			double p = nbrSymptsEx / nbrSympts;
			Double pourcentage =new Double(Math.round(p*100));
			map.put(m, pourcentage);
		}
		return map;
	}
	
	public Maladie getMaladieByLibelle(String libelle) {
		String searchQuery = "SELECT * FROM Maladie WHERE libelle like '%"+libelle+"%'";
		LOGGER.log(Level.INFO, searchQuery);
		try(Statement st = cnx.createStatement()){
			ResultSet rs = st.executeQuery(searchQuery);
			while(rs.next()) {
				Maladie m = new Maladie();
				m.setId(rs.getLong(1));
				m.setLibelle(rs.getString(2));
				return m;
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE,e.getMessage());
		}
		return null;
	}
	
	public List<Maladie> getMaladieByObjectSearch(Maladie maladie) {
		String searchQuery = "SELECT * FROM Maladie WHERE libelle like '%"+maladie.getLibelle()+"%'";
		List<Maladie> maladies = new ArrayList<>();
		LOGGER.log(Level.INFO, searchQuery);
		try(Statement st = cnx.createStatement()){
			ResultSet rs = st.executeQuery(searchQuery);
			while(rs.next()) {
				Maladie m = new Maladie();
				m.setId(rs.getLong(1));
				m.setLibelle(rs.getString(2));
				maladies.add(m);
			}
			return maladies;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE,e.getMessage());
		}
		return null;
	}
	
	

}
