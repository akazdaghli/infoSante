package edu.esprit.smartInnov.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.esprit.smartInnov.entites.Patient;
import edu.esprit.smartInnov.entites.ProSante;
import edu.esprit.smartInnov.entites.RendezVous;
import edu.esprit.smartInnov.entites.Utilisateur;
import edu.esprit.smartInnov.utils.ConnectionManager;
import edu.esprit.smartInnov.utils.Utilitaire;
import edu.esprit.smartInnov.vues.VRendezVous;

public class RendezVousService {
	private static final Logger LOGGER = Logger.getLogger(UtilisateurService.class.getName());
	private Connection cnx;
	
	public RendezVousService() {
		cnx = ConnectionManager.getInstance().getCnx();
	}
	
	public List<RendezVous> getListRendezVousByProfSante(ProSante p){
		String searchQuery = "SELECT * FROM rendezvous WHERE idMedecin = ? and date  > ? ORDER BY date";
		LOGGER.log(Level.INFO, searchQuery);
		List<RendezVous> rendezvous = new ArrayList<>();
		Calendar c = Calendar.getInstance();
		try(PreparedStatement ps = cnx.prepareStatement(searchQuery);) {
			ps.setLong(1, p.getId());
			
			ps.setDate(2, Utilitaire.getSqlDateFromUtilDate(new Date()));
			ResultSet rs = ps.executeQuery();
			if(rs != null) {
				while(rs.next()) {
					RendezVous rv = new RendezVous();
					rv.setMedecin(new MedecinService().getMedecinById(rs.getLong("idMedecin")));
					rv.setPatient((Patient) new UtilisateurService().getUserById(rs.getLong("idPatient")));
					rv.setDateRendezVs(rs.getDate(3));
					rendezvous.add(rv);
				}
			}
			return rendezvous;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<VRendezVous> getListVRendezVousByProfSante(ProSante p){
		String searchQuery = "SELECT * FROM rendezvous WHERE idMedecin = ? and date  > ? and flagValide = 0 ORDER BY date";
		LOGGER.log(Level.INFO, searchQuery);
		List<VRendezVous> rendezvous = new ArrayList<>();
		Calendar c = Calendar.getInstance();
		try(PreparedStatement ps = cnx.prepareStatement(searchQuery);) {
			ps.setLong(1, p.getId());
			
			ps.setDate(2, Utilitaire.getSqlDateFromUtilDate(new Date()));
			ResultSet rs = ps.executeQuery();
			if(rs != null) {
				while(rs.next()) {
					VRendezVous rv = new VRendezVous();
					Utilisateur d = new UtilisateurService().getUserById(rs.getLong("idMedecin"));
					Utilisateur pa = new UtilisateurService().getUserById(rs.getLong("idPatient"));
					rv.setDocteur(d.getNom()+" "+d.getPrenom());
					rv.setPatient(pa.getNom()+" "+pa.getPrenom());
					rv.setDate(rs.getDate(3).toString());
					rv.setHeure(rs.getInt("heure")+"H");
					rv.setSujet(rs.getString("sujet"));
					rv.setDocteurUser(d);
					rv.setPatientUser(pa);
					if(rs.getBoolean("flagValide"))
						rv.setFlagValide("Validé");
					else
						rv.setFlagValide("En cours");
					rendezvous.add(rv);
				}
			}
			return rendezvous;
		} catch (SQLException e) {
			LOGGER.severe(e.getMessage());
		}
		return null;
	}
	
	public List<VRendezVous> getListVRendezVousByPatient(Patient p){
		String searchQuery = "SELECT * FROM rendezvous WHERE idPatient = ? and date  >= ? ORDER BY date";
		LOGGER.log(Level.INFO, searchQuery);
		List<VRendezVous> rendezvous = new ArrayList<>();
		Calendar c = Calendar.getInstance();
		try(PreparedStatement ps = cnx.prepareStatement(searchQuery);) {
			ps.setLong(1, p.getId());
			
			ps.setDate(2, Utilitaire.getSqlDateFromUtilDate(new Date()));
			ResultSet rs = ps.executeQuery();
			if(rs != null) {
				while(rs.next()) {
					VRendezVous rv = new VRendezVous();
					Utilisateur patient = new UtilisateurService().getUserById(rs.getLong("idPatient"));
					rv.setPatient(patient.getNom()+" "+patient.getPrenom());
					Utilisateur docteur = new UtilisateurService().getUserById(rs.getLong("idMedecin"));
					rv.setDocteur(docteur.getNom() +" " + docteur.getPrenom());
					rv.setDate(rs.getDate(3).toString());
					rv.setHeure(rs.getInt("heure")+"H");
					rv.setSujet(rs.getString("sujet"));
					rv.setDocteurUser(docteur);
					rv.setPatientUser(patient);
					if(rs.getBoolean("flagValide"))
						rv.setFlagValide("Validé");
					else
						rv.setFlagValide("En cours");
					rendezvous.add(rv);
				}
			}
			return rendezvous;
		} catch (SQLException e) {
			LOGGER.severe(e.getMessage());
		}
		return null;
	}
	
	public void ajouter(RendezVous rv) {
		String addQuery = "INSERT INTO rendezvous (idMedecin, idPatient, date, flagValide, heure, sujet) VALUES (?,?,?,?,?,?)";
		try (PreparedStatement ps= cnx.prepareStatement(addQuery);) {
			ps.setLong(1, rv.getMedecin().getId());
			ps.setLong(2, rv.getPatient().getId());
			ps.setDate(3, rv.getDateRendezVs());
			ps.setBoolean(4, rv.isFlagValide());
			ps.setString(5, rv.getHeureRendezVs());
			ps.setString(6, rv.getSujet());
			LOGGER.log(Level.INFO, ps.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			LOGGER.severe(e.getMessage());
		}
	}
	
	public List<RendezVous> getRendezVousByDocteurAndDateAndHeure(ProSante docteur, java.sql.Date date, String heure) {
		List<RendezVous> rendezvous = new ArrayList<>();
		String searchQuery = "SELECT * FROM rendezvous WHERE idMedecin = ? AND date = ? AND heure = ? AND flagValide = ?";
		try (PreparedStatement ps = cnx.prepareStatement(searchQuery);){
			ps.setLong(1, docteur.getId());
			ps.setDate(2, date);
			ps.setString(3, heure);
			ps.setBoolean(4, true);
			LOGGER.log(Level.INFO, ps.toString());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				RendezVous rv = new RendezVous();
				rv.setMedecin(docteur);
				rv.setPatient((Patient) new UtilisateurService().getUserById(rs.getLong("idPatient")));
				rv.setDateRendezVs(date);
				rv.setHeureRendezVs(heure);
				rv.setFlagValide(true);
				rv.setSujet(rs.getString("sujet"));
				rendezvous.add(rv);
			}
		} catch (SQLException e) {
			LOGGER.severe(e.getMessage());
		}
		return rendezvous;
	}
	
	public void cancelRendezVous(VRendezVous v) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String deleteQuery = "DELETE FROM rendezvous WHERE idMedecin = ? AND idPatient = ? AND date = ? AND heure = ?";
		try (PreparedStatement ps = cnx.prepareStatement(deleteQuery);){
			ps.setLong(1, v.getDocteurUser().getId());
			ps.setLong(2, v.getPatientUser().getId());
			try {
				ps.setDate(3, Utilitaire.getSqlDateFromUtilDate(sdf.parse(v.getDate())));
			} catch (ParseException e) {
				LOGGER.severe(e.getMessage());
			}
			ps.setString(4, v.getHeure());
			LOGGER.info(ps.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			LOGGER.severe(e.getMessage());
		}
	}
	
	public void confirmRendezVous(VRendezVous v) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String updateQuery = "UPDATE rendezvous SET flagValide = ? WHERE idMedecin = ? AND idPatient = ? AND date = ? AND heure = ?";
		try (PreparedStatement ps = cnx.prepareStatement(updateQuery);){
			ps.setBoolean(1, true);
			ps.setLong(2, v.getDocteurUser().getId());
			ps.setLong(3, v.getPatientUser().getId());
			try {
				ps.setDate(4, Utilitaire.getSqlDateFromUtilDate(sdf.parse(v.getDate())));
			} catch (ParseException e) {
				LOGGER.severe(e.getMessage());
			}
			ps.setString(5, v.getHeure());
		} catch (SQLException e) {
			LOGGER.severe(e.getMessage());
		}
	}
}
