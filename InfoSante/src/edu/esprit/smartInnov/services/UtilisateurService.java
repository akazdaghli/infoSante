package edu.esprit.smartInnov.services;

import java.security.NoSuchAlgorithmException;
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

import edu.esprit.smartInnov.entites.Admin;
import edu.esprit.smartInnov.entites.Patient;
import edu.esprit.smartInnov.entites.ProSante;
import edu.esprit.smartInnov.entites.Utilisateur;
import edu.esprit.smartInnov.utils.ConnectionManager;
import edu.esprit.smartInnov.utils.IConstants;
import edu.esprit.smartInnov.utils.UtilisateurRowMapper;
import edu.esprit.smartInnov.utils.Utilitaire;
import edu.esprit.smartInnov.utils.VProSanteRowMapper;
import edu.esprit.smartInnov.vues.VProSante;

public class UtilisateurService {

	private Connection cnx;
	private SpecialiteService specialiteService = new SpecialiteService();
	
	private static final Logger LOGGER = Logger.getLogger(UtilisateurService.class.getName());
	public UtilisateurService() {
		cnx = ConnectionManager.getInstance().getCnx();
	}
	
	public List<Utilisateur> getMedecins(){
		String getAllQuery = "SELECT * FROM Utilisateur WHERE profil = '"+IConstants.Profils.MEDECIN +"'";
		LOGGER.log(Level.INFO, getAllQuery);
		List<Utilisateur> medecins = new ArrayList();
		Statement st;
		try {
			st = cnx.createStatement();
			ResultSet rs = st.executeQuery(getAllQuery);
			while(rs.next()) {
				ProSante m = new ProSante();
				m.setNom(rs.getString("nom"));
				m.setPrenom(rs.getString("prenom"));
				m.setId(rs.getLong("id"));
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
			e.printStackTrace();
		}
		
		return medecins;
	}
	
	public void updateUserPhoto(Utilisateur user) {
		String updateQuery = "UPDATE Utilisateur SET photo = ? WHERE id= ?";
		try (PreparedStatement ps = cnx.prepareStatement(updateQuery);){
			
			ps.setBlob(1, user.getPhoto());
			ps.setLong(2, user.getId());
			LOGGER.log(Level.INFO, ps.toString());
			ps.executeUpdate();
			LOGGER.log(Level.INFO, "User photo updated");
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}
	
	public List<VProSante> getAllVProSante(){
		String searchQuery = "SELECT * FROM v_pro_sante";
		LOGGER.log(Level.INFO, searchQuery);
		try(PreparedStatement ps = cnx.prepareStatement(searchQuery)){
			ResultSet rs = ps.executeQuery();
			return new VProSanteRowMapper(rs).getVProSantes();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		return null;
	}
	
	public List<Utilisateur> getPatients(){
		String getAllQuery = "SELECT * FROM Utilisateur WHERE profil = '"+IConstants.Profils.PATIENT +"'";
		LOGGER.log(Level.INFO, getAllQuery);
		List<Utilisateur> patients = new ArrayList();
		Statement st;
		try {
			st = cnx.createStatement();
			ResultSet rs = st.executeQuery(getAllQuery);
			if(rs != null) {
				return new UtilisateurRowMapper(rs).getUtilisateurs();
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		
		return patients;
	}
	
	public List<Utilisateur> getAllUsers(){
		String getAllQuery = "SELECT * FROM Utilisateur ";
		LOGGER.log(Level.INFO, getAllQuery);
		List<Utilisateur> patients = new ArrayList();
		Statement st;
		try {
			st = cnx.createStatement();
			ResultSet rs = st.executeQuery(getAllQuery);
			if(rs != null) {
				return new UtilisateurRowMapper(rs).getUtilisateurs();
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		
		return patients;
	}
	
	public Utilisateur getUserByLoginAndPassword(String login, String pwd) {
		String searchQuery = "SELECT * FROM UTILISATEUR WHERE login = ? AND pwd = ?";
		try(PreparedStatement ps = cnx.prepareStatement(searchQuery)){
			ps.setString(1, login);
			ps.setString(2, pwd);
			LOGGER.log(Level.INFO, ps.toString());
			ResultSet rs = ps.executeQuery();
			return new UtilisateurRowMapper(rs).getFirstUtilisateur();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		return null;
	}
	
	public Utilisateur getUserByLogin(String login) {
		String searchQuery = "SELECT * FROM UTILISATEUR WHERE login = ?";
		try(PreparedStatement ps = cnx.prepareStatement(searchQuery)){
			ps.setString(1, login);
			LOGGER.log(Level.INFO, ps.toString());
			ResultSet rs = ps.executeQuery();
			return new UtilisateurRowMapper(rs).getFirstUtilisateur();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		return null;
	}
	
	public Utilisateur getUserById(Long id) {
		String searchQuery = "SELECT * FROM UTILISATEUR WHERE id = ?";
		LOGGER.log(Level.INFO, searchQuery);
		try(PreparedStatement ps = cnx.prepareStatement(searchQuery)){
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			return new UtilisateurRowMapper(rs).getFirstUtilisateur();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		return null;
	}
	
	public boolean isUtilisateurExistByLogin(String login) {
		String searchQuery = "SELECT * FROM UTILISATEUR WHERE login = ?";
		LOGGER.log(Level.INFO, searchQuery);
		try(PreparedStatement ps = cnx.prepareStatement(searchQuery)){
			ps.setString(1, login);
			ResultSet rs = ps.executeQuery();
			return new UtilisateurRowMapper(rs).getUtilisateurs().size() > 0;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		return true;
	}
	
	public boolean isUtilisateurExistByMail(String mail) {
		String searchQuery = "SELECT * FROM UTILISATEUR WHERE mail = ?";
		LOGGER.log(Level.INFO, searchQuery);
		try(PreparedStatement ps = cnx.prepareStatement(searchQuery)){
			ps.setString(1, mail);
			ResultSet rs = ps.executeQuery();
			return new UtilisateurRowMapper(rs).getUtilisateurs().size() > 0;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		return true;
	}
	
	
	public void ajouter(Utilisateur user) {
		String addProQuery = "INSERT INTO Utilisateur (nom, prenom, adresse, mail, numTel, dateCreation, flagActif, profil, login, pwd, idSpecialite, idLocal, photo) VALUES (?, ?, ?, ?,?,?,?,?,?,?, ?, ?, ?)";
		String addParQuery = "INSERT INTO Utilisateur (nom, prenom, adresse, mail, numTel, dateCreation, flagActif, profil, login, pwd, photo) VALUES (?, ?, ?, ?,?,?,?,?, ?, ?, ?)";
		try {
			PreparedStatement ps = null;
			
			if(user instanceof ProSante) {
				ps = cnx.prepareStatement(addProQuery);
				ps.setLong(11, ((ProSante) user).getSpecialite().getId());
				ps.setLong(12, ((ProSante) user).getLocal().getId());
				ps.setBinaryStream(13, user.getPhoto());
				ps.setString(8, IConstants.Profils.MEDECIN);
			}
			if(user instanceof Admin) {
				ps = cnx.prepareStatement(addParQuery);
				ps.setString(8, IConstants.Profils.ADMINISTRATUER);
				ps.setBinaryStream(11, user.getPhoto());
			}
			if(user instanceof Patient) {
				ps = cnx.prepareStatement(addParQuery);
				ps.setString(8, IConstants.Profils.PATIENT);
				ps.setBinaryStream(11, user.getPhoto());
			}
			
			ps.setString(1, user.getNom());
			ps.setString(2, user.getPrenom());
			ps.setString(3, user.getAdresse());
			ps.setString(4, user.getMail());
			ps.setString(5, user.getNumTel());
			ps.setDate(6, (Date) user.getDateCreation());
			ps.setBoolean(7, user.getFlagActif());
			ps.setString(9, user.getLogin());
			try {
				ps.setString(10, Utilitaire.hashMD5Crypt(user.getPwd()));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			LOGGER.log(Level.INFO, ps.toString());
			ps.executeUpdate();
			ps.close();
			
			LOGGER.log(Level.INFO, "user ajouté");
		} catch (SQLException e) {
			LOGGER.log(Level.INFO, "user non ajouté");
			e.printStackTrace();
		}
		
	}
	
	public List<VProSante> getListMedecinsFiltred(VProSante v){
		StringBuilder searchQuery = new StringBuilder("SELECT * FROM v_pro_sante WHERE 1=1 ");
		if(v.getAdresse() != null && !v.getAdresse().isEmpty()) {
			searchQuery.append("AND adresse like '%"+v.getAdresse()+"%'");
		}
		if(v.getLibelle() != null && !v.getLibelle().isEmpty()) {
			searchQuery.append("AND libelle like '"+v.getLibelle()+"'");
		}
		if(v.getNom()!= null && !v.getNom().isEmpty()) {
			searchQuery.append("AND nom like '"+ v.getNom()+"'");
		}
		if(v.getPrenom() != null && !v.getPrenom().isEmpty()) {
			searchQuery.append("AND prenom like '"+v.getPrenom()+"'");
		}
		LOGGER.log(Level.INFO, searchQuery.toString());
		try(Statement st = cnx.createStatement()) {
			ResultSet rs = st.executeQuery(searchQuery.toString());
			if(rs != null) {
				return new VProSanteRowMapper(rs).getVProSantes();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public int getNbrNewUtilisateurs() {
		String countQuery = "SELECT COUNT(*) AS NBR FROM Utilisateur";
		
		try(Statement st = cnx.createStatement()) {
			ResultSet rs = st.executeQuery(countQuery);
			if(rs != null) {
				while(rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		return -1;
	}
	
	public int getNbrInscriEnAttente() {
		String countQuery = "SELECT COUNT(*) AS NBR FROM Utilisateur WHERE Profil = '"+IConstants.Profils.MEDECIN +
				" 'AND flagActif = 0";
		LOGGER.log(Level.INFO, countQuery);
		try(Statement st = cnx.createStatement()) {
			ResultSet rs = st.executeQuery(countQuery);
			if(rs != null) {
				while(rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		return -1;
	}
	
	public List<VProSante> getListProSanteEnAttente(){
		String searchQuery = "SELECT * FROM v_pro_sante where flagActif = ?";
		try (PreparedStatement ps = cnx.prepareStatement(searchQuery);){
			ps.setBoolean(1, false);
			LOGGER.log(Level.INFO, searchQuery.toString());
			ResultSet rs = ps.executeQuery();
			return new VProSanteRowMapper(rs).getVProSantes();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		return null;
	}
	
	public void confirmerProSante(VProSante v) {
		String query = "UPDATE Utilisateur SET flagActif = 1 WHERE id = "+v.getIdProSante();
		LOGGER.log(Level.INFO, query);
		try(Statement st = cnx.createStatement()) {
			st.executeUpdate(query);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}
	
}
