package edu.esprit.smartInnov.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.esprit.smartInnov.entites.Patient;
import edu.esprit.smartInnov.utils.ConnectionManager;
import edu.esprit.smartInnov.utils.IConstants;

public class PatientService {

	private Connection cnx;
	private static final Logger LOGGER = Logger.getLogger(PatientService.class.getName());
	public PatientService() {
		cnx = ConnectionManager.getInstance().getCnx();
	}
	
	public void ajouter(Patient patient) {
		String addQuery = "INSERT INTO Utilisateur (nom, prenom, adresse, mail, numTel, dateCreation, flagActif, Profil) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		LOGGER.log(Level.INFO, addQuery);
		try {
			PreparedStatement ps = cnx.prepareStatement(addQuery);
			ps.setString(1, patient.getNom());
			ps.setString(2, patient.getPrenom());
			ps.setString(3, patient.getAdresse());
			ps.setString(4, patient.getMail());
			ps.setString(5, patient.getNumTel());
			ps.setDate(6,patient.getDateCreation());
			ps.setBoolean(7, patient.getFlagActif());
			ps.setString(8, IConstants.Profils.PATIENT);
			ps.executeUpdate();
			ps.close();
			LOGGER.log(Level.INFO, "patient ajouté");
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE,"Problem adding patient");
			LOGGER.log(Level.SEVERE,e.getMessage());
		}
		
	}
	
	public boolean supprimeMedecin(Long id) {
		String deleteQuery = "DELETE FROM Utilisateur WHERE id=? and profil = '"+IConstants.Profils.PATIENT+"'";
		LOGGER.log(Level.INFO, deleteQuery);
		PreparedStatement ps;
		try {
			ps = cnx.prepareStatement(deleteQuery);
			ps.setLong(1, id);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE,"Problem deleting patient");
			LOGGER.log(Level.SEVERE,e.getMessage());
			return false;
		}
		
		return true;
	}
	
	public List<Patient> getPatients(){
		String getAllQuery = "SELECT * FROM Utilisateur WHERE profil = '"+IConstants.Profils.PATIENT+"'";
		LOGGER.log(Level.INFO, getAllQuery);
		List<Patient> patients = new ArrayList();
		Statement st;
		try {
			st = cnx.createStatement();
			ResultSet rs = st.executeQuery(getAllQuery);
			while(rs.next()) {
				Patient p = new Patient();
				p.setNom(rs.getString("nom"));
				p.setPrenom(rs.getString("prenom"));
				p.setFlagActif(rs.getBoolean("flagActif"));
				p.setMail(rs.getString("mail"));
				p.setAdresse(rs.getString("adresse"));
				p.setDateCreation(rs.getDate("dateCreation"));
				p.setNumTel(rs.getString("numTel"));
				p.setId(rs.getLong("id"));
				patients.add(p);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE,"Problem retrieving patients");
			LOGGER.log(Level.SEVERE,e.getMessage());
		}
		
		return patients;
	}
	
	public Patient getPatientById(Long id) {
		Patient p = new Patient();
		String searchQuery = "SELECT * FROM Utilisateur WHERE id=? and profil ='"+IConstants.Profils.PATIENT+"'";
		LOGGER.log(Level.INFO, searchQuery);
		PreparedStatement ps;
		try {
			ps = cnx.prepareStatement(searchQuery);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				p.setNom(rs.getString("nom"));
				p.setPrenom(rs.getString("prenom"));
				p.setFlagActif(rs.getBoolean("flagActif"));
				p.setMail(rs.getString("mail"));
				p.setAdresse(rs.getString("adresse"));
				p.setDateCreation(rs.getDate("dateCreation"));
				p.setNumTel(rs.getString("numTel"));
				p.setId(rs.getLong("id"));
			}
			return p;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE,"Problem retrieving patient by id");
			LOGGER.log(Level.SEVERE,e.getMessage());
		}
		return null;
		
	}
	
	public void modifierMedecin(Patient p,Long id) {
		String updateQuery = "UPDATE Utilisateur set nom = ?, prenom = ?, mail = ?, numTel = ?,adresse = ?, mail = ?,flagActif = ?,dateCreation = ? WHERE id = ? and profil='"+IConstants.Profils.PATIENT+"'";
		LOGGER.log(Level.INFO, updateQuery);
		PreparedStatement ps;
		try {
			ps = cnx.prepareStatement(updateQuery);
			ps.setString(1, p.getNom());
			ps.setString(2, p.getPrenom());
			ps.setString(3, p.getMail());
			ps.setString(4, p.getNumTel());
			ps.setString(5, p.getAdresse());
			ps.setBoolean(6, p.getFlagActif());
			ps.setDate(7, (Date) p.getDateCreation());
			ps.setLong(8, id);
			ps.executeUpdate();
			ps.close();
			
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE,"Problem updating patients");
			LOGGER.log(Level.SEVERE,e.getMessage());
		}
		
	}
}
