package edu.esprit.smartInnov.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.esprit.smartInnov.entites.Local;
import edu.esprit.smartInnov.utils.ConnectionManager;

public class LocalService {

	private static final Logger LOGGER = Logger.getLogger(LocalService.class.getName());
	private Connection cnx;
	public LocalService() {
		cnx = ConnectionManager.getInstance().getCnx();
	}
	
	public Local getLocalById(Long idLocal) {
		return new Local();
	}
	
	public Local ajouter(Local local) {
		String addQuery = "INSERT INTO Local(adresse, type) VALUES (?, ?)";
		LOGGER.log(Level.INFO, addQuery);
		try(PreparedStatement ps = cnx.prepareStatement(addQuery,Statement.RETURN_GENERATED_KEYS)){
			ps.setString(1, local.getAdresse());
			ps.setString(2, local.getType());
			ps.executeUpdate();
			ResultSet genKeys = ps.getGeneratedKeys();
			while(genKeys.next()) {
				local.setId(genKeys.getLong(1));
			}
			return local;
		} catch (SQLException e) {
			LOGGER.severe(e.getMessage());
		}
		return null;
	}
}


