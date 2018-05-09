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

import edu.esprit.smartInnov.entites.Patient;
import edu.esprit.smartInnov.entites.Specialite;
import edu.esprit.smartInnov.utils.ConnectionManager;

public class SpecialiteService {
	private static final Logger LOGGER = Logger.getLogger(SpecialiteService.class.getName());
	private Connection cnx;
	
	public SpecialiteService() {
		cnx = ConnectionManager.getInstance().getCnx();
	}
	
	public void ajouter(Specialite s) {
		String addQuery = "INSERT INTO specialite (libelle, type) VALUES (?, ?)";
		try (PreparedStatement ps = cnx.prepareStatement(addQuery);){
			ps.setString(1, s.getLibelle());
			ps.setString(2, s.getType());
			LOGGER.info(ps.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			LOGGER.severe(e.getMessage());
		}
	}
	
	public void delete(Specialite s) {
		String deleteQuery = "DELETE FROM specialite WHERE id = ?";
		try (PreparedStatement ps = cnx.prepareStatement(deleteQuery);){
			ps.setLong(1, s.getId());
			LOGGER.info(ps.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			LOGGER.severe(e.getMessage());
		}
	}
	
	public void update(Specialite s) {
		String updateQuery = "UPDATE specialite SET libelle =  ?, type = ? WHERE id = ?";
		try (PreparedStatement ps = cnx.prepareStatement(updateQuery);){
			ps.setString(1, s.getLibelle());
			ps.setString(2, s.getType());
			ps.setLong(3, s.getId());
			LOGGER.info(ps.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			LOGGER.severe(e.getMessage());
		}
	}
	
	public List<Specialite> getSpecialitesByObjectSearch(Specialite s){
		List<Specialite> specialites = new ArrayList<>();
		StringBuilder searchQuery = new StringBuilder("SELECT * FROM specialite WHERE 1=1 ") ;
		if(s.getLibelle() != null && !s.getLibelle().isEmpty()) {
			searchQuery.append(" AND libelle LIKE '%"+s.getLibelle()+"%'");
		}
		if(s.getType() != null && !s.getType().isEmpty()) {
			searchQuery.append(" AND type LIKE '%"+s.getType()+"%'");
		}
		LOGGER.info(searchQuery.toString());
		try (Statement st = cnx.createStatement();
				ResultSet rs = st.executeQuery(searchQuery.toString());){
			while(rs.next()) {
				Specialite sp = new Specialite();
				sp.setId(rs.getLong("id"));
				sp.setLibelle(rs.getString("libelle"));
				sp.setType(rs.getString("type"));
				specialites.add(sp);
			}
			return specialites;
		} catch (SQLException e) {
			LOGGER.severe(e.getMessage());
		}
		return null;
	}
	
	public Specialite getSpeciliteById(Long id) {
		Specialite s = new Specialite();
		String searchQuery = "SELECT * FROM Specialite WHERE id=?";
		PreparedStatement ps;
		try {
			ps = cnx.prepareStatement(searchQuery);
			ps.setLong(1, id);
			LOGGER.info(ps.toString());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				s.setId(rs.getLong("id"));
				s.setLibelle(rs.getString("libelle"));
			}
			return s;
		} catch (SQLException e) {
			LOGGER.severe(e.getMessage());
		}
		return null;
	}
	
	public List<Specialite> getAllSpecialites(){
		String searchQuery = "SELECT * FROM Specialite";
		LOGGER.log(Level.INFO, searchQuery);
		List<Specialite> specs = new ArrayList<>();
		try(Statement st = cnx.createStatement()){
			ResultSet rs = st.executeQuery(searchQuery);
			while(rs.next()) {
				Specialite s = new Specialite();
				s.setId(rs.getLong(1));
				s.setLibelle(rs.getString(2));
				s.setType(rs.getString(3));
				specs.add(s);
			}
			return specs;
		} catch (SQLException e) {
			LOGGER.severe(e.getMessage());
		}
		return null;
	}
	
	public Specialite getSpecialiteByLibelle(String libelle) {
		String searchQuery = "SELECT * FROM Specialite WHERE libelle like ?";
		Specialite s = new Specialite();
		try(PreparedStatement st = cnx.prepareStatement(searchQuery)){
			st.setString(1, libelle);
			LOGGER.info(st.toString());
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				s.setId(rs.getLong(1));
				s.setLibelle(rs.getString(2));
				s.setType(rs.getString(3));
			}
			return s;
		} catch (SQLException e) {
			LOGGER.severe(e.getMessage());
		}
		return null;
	}
}
