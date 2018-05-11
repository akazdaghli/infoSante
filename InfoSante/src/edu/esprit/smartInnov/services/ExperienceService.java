package edu.esprit.smartInnov.services;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.esprit.smartInnov.entites.Commentaire;
import edu.esprit.smartInnov.entites.Experience;
import edu.esprit.smartInnov.entites.Utilisateur;
import edu.esprit.smartInnov.utils.ConnectionManager;
import edu.esprit.smartInnov.utils.IConstants;
import edu.esprit.smartInnov.vues.VExperience;

public class ExperienceService implements Serializable{
	
	private Connection cnx;
	private UtilisateurService service;
	private static final Logger LOGGER = Logger.getLogger(ExperienceService.class.getName());
	public ExperienceService() {
		cnx = ConnectionManager.getInstance().getCnx();
	}
	
	public Experience getExperienceById(Long id) {
		service = new UtilisateurService();
		String searchQuery = "SELECT * FROM experience WHERE id = ?";
		try (PreparedStatement ps = cnx.prepareStatement(searchQuery);){
			ps.setLong(1, id);
			LOGGER.info(searchQuery.toString());
			ResultSet rs = ps.executeQuery();
			Experience e = null;
			while(rs.next()) {
				e = new Experience();
				e.setId(rs.getLong("id"));
				e.setTitre(rs.getString("titre"));
				e.setDetail(rs.getString("detail"));
				e.setNbrVues(rs.getInt("nbrVues"));
				e.setDatePartage(rs.getDate("datePartage"));
				e.setUtilisateur(service.getUserById(rs.getLong("idUser")));
				e.setFlagVisible(rs.getBoolean("flagVisible"));
			}
			return e;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		return null;
	}
	
	public List<Experience> getExperienceByUser(Utilisateur user, boolean visible){
		String searchQuery = "SELECT * FROM experience WHERE idUser = ? AND flagVisible = ?";
		List<Experience> experiences = new ArrayList<>();
		try (PreparedStatement ps = cnx.prepareStatement(searchQuery);){
			ps.setLong(1, user.getId());
			ps.setBoolean(2, visible);
			LOGGER.info(searchQuery.toString());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Experience e = new Experience();
				e.setId(rs.getLong("id"));
				e.setTitre(rs.getString("titre"));
				e.setDetail(rs.getString("detail"));
				e.setNbrVues(rs.getInt("nbrVues"));
				e.setDatePartage(rs.getDate("datePartage"));
				e.setUtilisateur(user);
				e.setFlagVisible(visible);
				experiences.add(e);
			}
			return experiences;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		return null;
	}
	
	public List<VExperience> getVExperienceByUser(Utilisateur user){
		SimpleDateFormat sdf = new SimpleDateFormat(IConstants.DATEPATTERN);
		UtilisateurService utilisateurService = new UtilisateurService();
		String searchQuery = "SELECT * FROM experience WHERE idUser = ? ORDER BY datePartage, nbrVues";
		List<VExperience> experiences = new ArrayList<>();
		try (PreparedStatement ps = cnx.prepareStatement(searchQuery);){
			ps.setLong(1, user.getId());
			LOGGER.info(searchQuery.toString());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				VExperience e = new VExperience();
				e.setId(rs.getLong("id"));
				e.setTitre(rs.getString("titre"));
//				e.setDetail(rs.getString("detail"));
				e.setNbrVues(rs.getInt("nbrVues")+"");
				e.setDate(sdf.format(rs.getDate("datePartage")));
				user = utilisateurService.getUserById(rs.getLong("idUser"));
				e.setUser(user.getNom() +" " + user.getPrenom());
				e.setNbrComm(getNbrCommByExperience(rs.getLong("id"))+"");
				e.setDetail(rs.getString("detail"));
				e.setPhoto(rs.getString("photo"));
				experiences.add(e);
			}
			return experiences;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		return null;
	}
	
	public List<Experience> getAllExperiences(){
		UtilisateurService utilisateurService = new UtilisateurService();
		String searchQuery = "SELECT * FROM experience ORDER BY datePartage, nbrVues ";
		LOGGER.info(searchQuery);
		List<Experience> experiences = new ArrayList<>();
		try (Statement ps = cnx.createStatement();){
			ResultSet rs = ps.executeQuery(searchQuery);
			while(rs.next()) {
				Experience e = new Experience();
				e.setId(rs.getLong("id"));
				e.setTitre(rs.getString("titre"));
				e.setDetail(rs.getString("detail"));
				e.setNbrVues(rs.getInt("nbrVues"));
				e.setDatePartage(rs.getDate("datePartage"));
				e.setUtilisateur(utilisateurService.getUserById(rs.getLong("idUser")));
				e.setFlagVisible(rs.getBoolean("flagVisible"));
				experiences.add(e);
			}
			return experiences;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		return null;
	}
	
	public List<VExperience> getAllVExperiences(){
		SimpleDateFormat sdf = new SimpleDateFormat(IConstants.DATETIMEPATTERN);
		UtilisateurService utilisateurService = new UtilisateurService();
		
		Utilisateur user;
		String searchQuery = "SELECT * FROM experience ORDER BY datePartage, nbrVues ";
		LOGGER.info(searchQuery);
		List<VExperience> experiences = new ArrayList<>();
		try (Statement ps = cnx.createStatement();){
			ResultSet rs = ps.executeQuery(searchQuery);
			while(rs.next()) {
				VExperience e = new VExperience();
				e.setId(rs.getLong("id"));
				e.setTitre(rs.getString("titre"));
//				e.setDetail(rs.getString("detail"));
				e.setNbrVues(rs.getInt("nbrVues")+"");
				e.setDate(sdf.format(rs.getDate("datePartage")));
				user = utilisateurService.getUserById(rs.getLong("idUser"));
				e.setUser(user.getNom() +" " + user.getPrenom());
				e.setNbrComm(getNbrCommByExperience(rs.getLong("id"))+"");
				e.setDetail(rs.getString("detail"));
				e.setPhoto(rs.getString("photo"));
				experiences.add(e);
			}
			return experiences;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		return null;
	}
	
	public int getNbrExperienceLastWeek() {
		String countQuery = "SELECT COUNT(*) AS NBR_EXPERIENCE FROM Experience";
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
	
	public int getNbrCommByExperience(Experience e) {
		String countQuery = "SELECT COUNT(*) AS NBR_COMM FROM commentaire WHERE idExperience = "+e.getId();
		LOGGER.log(Level.INFO, countQuery);
		try(Statement st = cnx.createStatement()) {
			ResultSet rs = st.executeQuery(countQuery);
			if(rs != null) {
				while(rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (SQLException ex) {
			LOGGER.log(Level.SEVERE, ex.getMessage());
		}
		return -1;
	}
	
	public int getNbrCommByExperience(Long idExperience) {
		String countQuery = "SELECT COUNT(*) AS NBR_COMM FROM commentaire WHERE idExperience = "+idExperience;
		LOGGER.log(Level.INFO, countQuery);
		try(Statement st = cnx.createStatement()) {
			ResultSet rs = st.executeQuery(countQuery);
			if(rs != null) {
				while(rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (SQLException ex) {
			LOGGER.log(Level.SEVERE, ex.getMessage());
		}
		return -1;
	}
	
	public void incrementExperienceVues(VExperience v) {
		String updateQuery = "UPDATE experience set nbrVues = ? Where id = ?";
		try (PreparedStatement ps = cnx.prepareStatement(updateQuery);){
			ps.setInt(1, new Integer(v.getNbrVues())+1);
			ps.setLong(2, v.getId());
			LOGGER.info(ps.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			LOGGER.severe(e.getMessage());
		}
	}
	
	public void ajouter(Experience e) throws SQLException {
		String addQuery =  "INSERT INTO experience (idUser, titre, detail, nbrVues, photo, flagVisible, datePartage) VALUES (?, ?, ?, ?,? ,?, ?)";
		try (PreparedStatement ps = cnx.prepareStatement(addQuery);){
			ps.setLong(1, e.getUtilisateur().getId());
			ps.setString(2, e.getTitre());
			ps.setString(3, e.getDetail());
			ps.setInt(4, e.getNbrVues());
			ps.setString(5, e.getPhoto());
			ps.setBoolean(6, e.isFlagVisible());
			ps.setDate(7, e.getDatePartage());
			LOGGER.info(ps.toString());
			ps.executeUpdate();
		} catch (SQLException e1) {
			LOGGER.info(e1.getMessage());
			throw e1;
			
		}
	}
	
	public List<Commentaire> getListCommByExperience(VExperience e){
		String searchQuery = "SELECT * FROM commentaire WHERE idExperience = "+e.getId();
		service = new UtilisateurService();
		List<Commentaire> comms = new ArrayList<>();
		try (Statement ps = cnx.createStatement();
				ResultSet rs = ps.executeQuery(searchQuery)){
			while(rs.next()) {
				Commentaire c = new Commentaire();
				c.setDateCommentaire(rs.getDate(4));
				c.setId(rs.getLong(1));
				c.setExperience(getExperienceById(rs.getLong(2)));
				c.setUser(service.getUserById(rs.getLong(3)));
				c.setDetail(rs.getString(5));
				comms.add(c);
			}
			return comms;
		} catch (SQLException e1) {
			LOGGER.info(e1.getMessage());
		}
		return null;
	}
	
	public void ajouterCommentaire(Commentaire c) {
		String addQuery = "INSERT INTO commentaire (idExperience, idUser, dateCommentaire, detail) VALUES (?, ?, ?, ?)";
		try (PreparedStatement ps = cnx.prepareStatement(addQuery);){
			ps.setLong(1, c.getExperience().getId());
			ps.setLong(2, c.getUser().getId());
			ps.setDate(3, c.getDateCommentaire());
			ps.setString(4, c.getDetail());
			LOGGER.info(ps.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			LOGGER.info(e.getMessage());
		}
	}
		
	

}
