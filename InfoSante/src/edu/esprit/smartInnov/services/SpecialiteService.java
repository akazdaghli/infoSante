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
	
	public Specialite getSpeciliteById(Long id) {
		Specialite s = new Specialite();
		String searchQuery = "SELECT * FROM Specialite WHERE id=?";
		LOGGER.log(Level.INFO, searchQuery);
		PreparedStatement ps;
		try {
			ps = cnx.prepareStatement(searchQuery);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				s.setId(rs.getLong("id"));
				s.setLibelle(rs.getString("libelle"));
			}
			return s;
		} catch (SQLException e) {
			e.printStackTrace();
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
				specs.add(s);
			}
			return specs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Specialite getSpecialiteByLibelle(String libelle) {
		String searchQuery = "SELECT * FROM Specialite WHERE libelle like ?";
		LOGGER.log(Level.INFO, searchQuery);
		Specialite s = new Specialite();
		try(PreparedStatement st = cnx.prepareStatement(searchQuery)){
			st.setString(1, libelle);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				s.setId(rs.getLong(1));
				s.setLibelle(rs.getString(2));
				s.setType(rs.getString(3));
			}
			return s;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
