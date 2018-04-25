package edu.esprit.smartInnov.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.esprit.smartInnov.entites.Patient;
import edu.esprit.smartInnov.entites.ProSante;
import edu.esprit.smartInnov.entites.RendezVous;
import edu.esprit.smartInnov.utils.ConnectionManager;

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
			
			ps.setDate(2, new Date(new java.util.Date().getTime()));
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
}
