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

import edu.esprit.smartInnov.entites.ProSante;
import edu.esprit.smartInnov.utils.ConnectionManager;
import edu.esprit.smartInnov.utils.IConstants;

public class MedecinService {

	private Connection cnx;
	private static final Logger LOGGER = Logger.getLogger(MedecinService.class.getName());
	private SpecialiteService specialiteService = new SpecialiteService();
	
	public MedecinService() {
		cnx = ConnectionManager.getInstance().getCnx();
	}
	
	public void ajouter(ProSante medecin) {
		String addQuery = "INSERT INTO Utilisateur (nom, prenom, adresse, mail, numTel, dateCreation, flagActif, idSpecialite, adresseCabinet, profil, login, pwd) VALUES (?, ?, ?, ?,?,?,?,?,?,?, ?, ?)";
		LOGGER.log(Level.INFO, addQuery);
		try {
			PreparedStatement ps = cnx.prepareStatement(addQuery);
			ps.setString(1, medecin.getNom());
			ps.setString(2, medecin.getPrenom());
			ps.setString(3, medecin.getAdresse());
			ps.setString(4, medecin.getMail());
			ps.setString(5, medecin.getNumTel());
			ps.setDate(6, (Date) medecin.getDateCreation());
			ps.setBoolean(7, medecin.getFlagActif());
			ps.setLong(8, medecin.getSpecialite().getId());
//			ps.setString(9, medecin.getAdresseCabinet());
			ps.setString(10, IConstants.Profils.MEDECIN);
			ps.setString(11, medecin.getLogin());
			ps.setString(12, medecin.getPwd());
			ps.executeUpdate();
			ps.close();
			LOGGER.log(Level.INFO, "Medecin ajouté");
		} catch (SQLException e) {
			LOGGER.log(Level.INFO, "Medecin non ajouté");
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		
	}
	
	public boolean supprimeMedecin(Long id) {
		String deleteQuery = "DELETE FROM Utilisateur WHERE id=? and profil ='"+IConstants.Profils.MEDECIN+"'";
		PreparedStatement ps;
		try {
			ps = cnx.prepareStatement(deleteQuery);
			ps.setLong(1, id);
			ps.executeUpdate();
			ps.close();
			LOGGER.log(Level.INFO, "Medecin supprimé");
		} catch (SQLException e) {
			LOGGER.log(Level.INFO, "Medecin non supprimé");
			LOGGER.log(Level.SEVERE, e.getMessage());
			return false;
		}
		
		return true;
	}
	
	public List<ProSante> getMedecins(){
		String getAllQuery = "SELECT * FROM Utilisateur WHERE profil = '"+IConstants.Profils.MEDECIN +"'";
		LOGGER.log(Level.INFO, getAllQuery);
		List<ProSante> medecins = new ArrayList();
		Statement st;
		try {
			st = cnx.createStatement();
			ResultSet rs = st.executeQuery(getAllQuery);
			while(rs.next()) {
				ProSante m = new ProSante();
				m.setNom(rs.getString("nom"));
				m.setPrenom(rs.getString("prenom"));
				m.setAdresse(rs.getString("adresse"));
//				m.setAdresseCabinet(rs.getString("adresseCabinet"));
				m.setFlagActif(rs.getBoolean("flagActif"));
				m.setDateCreation(rs.getDate("dateCreation"));
				m.setMail(rs.getString("mail"));
				m.setNumTel(rs.getString("numTel"));
				m.setSpecialite(specialiteService.getSpeciliteById(rs.getLong("idSpecialite")));
				medecins.add(m);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		
		return medecins;
	}
	
	public ProSante getMedecinById(Long id) {
		ProSante m = new ProSante();
		String searchQuery = "SELECT * FROM Utilisateur WHERE id=? and profil ='"+IConstants.Profils.MEDECIN+"'";
		LOGGER.log(Level.INFO, searchQuery);
		PreparedStatement ps;
		try {
			ps = cnx.prepareStatement(searchQuery);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				m.setNom(rs.getString("nom"));
				m.setPrenom(rs.getString("prenom"));
				m.setAdresse(rs.getString("adresse"));
//				m.setAdresseCabinet(rs.getString("adresseCabinet"));
				m.setFlagActif(rs.getBoolean("flagActif"));
				m.setDateCreation(rs.getDate("dateCreation"));
				m.setMail(rs.getString("mail"));
				m.setNumTel(rs.getString("numTel"));
				m.setSpecialite(specialiteService.getSpeciliteById(rs.getLong("idSpecialite")));
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		return m;
	}
}
