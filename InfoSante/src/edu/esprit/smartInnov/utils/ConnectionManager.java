package edu.esprit.smartInnov.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionManager {

	private static ConnectionManager connectionManager;
	private Connection cnx;
	private static final Logger LOGGER = Logger.getLogger(ConnectionManager.class.getName());
	private ConnectionManager() {
			String url = IConstants.Connection.urlDb;
			String username = IConstants.Connection.userNameBd;
			String pwd = IConstants.Connection.pwdDb;
			try {
				cnx = DriverManager.getConnection(url, username, pwd);
				LOGGER.log(Level.INFO, "Connection établie!!");
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "Connection non établie!!");
				LOGGER.log(Level.SEVERE, e.getMessage());
			}
	}
	
	public static ConnectionManager getInstance() {
		if(connectionManager == null) {
			connectionManager = new ConnectionManager();
		}
		return connectionManager;
	}

	public Connection getCnx() {
		return cnx;
	}
}
