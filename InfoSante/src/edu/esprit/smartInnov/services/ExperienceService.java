package edu.esprit.smartInnov.services;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.esprit.smartInnov.utils.ConnectionManager;

public class ExperienceService implements Serializable{
	
	private Connection cnx;
	private static final Logger LOGGER = Logger.getLogger(ExperienceService.class.getName());
	public ExperienceService() {
		cnx = ConnectionManager.getInstance().getCnx();
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
		
	

}
