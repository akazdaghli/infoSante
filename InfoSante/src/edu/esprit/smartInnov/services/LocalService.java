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
import edu.esprit.smartInnov.vues.VProSante;

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
	
	public Local getLocalByVProSante(VProSante pro) {
		String addQuery = "SELECT * FROM local WHERE id = (SELECT idLocal FROM utilisateur WHERE id= ?)";
		try(PreparedStatement ps = cnx.prepareStatement(addQuery)){
			ps.setLong(1, pro.getIdProSante());
			LOGGER.log(Level.INFO, ps.toString());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Local l = new Local();
				l.setAdresse(rs.getString("adresse"));
				l.setId(rs.getLong("id"));
				l.setType(rs.getString("type"));
				l.setLatitude(rs.getDouble("lat"));
				l.setLongitude(rs.getDouble("longitude"));
				return l;
			}
		} catch (SQLException e) {
			LOGGER.severe(e.getMessage());
		}
		return null;
	}
	
	public void update(Local local) {
		String updateQuery = "UPDATE local SET adresse = ?, type = ?, lat = ?, longitude = ? WHERE id = ?";
		try (PreparedStatement ps = cnx.prepareStatement(updateQuery);){
			ps.setString(1, local.getAdresse());
			ps.setString(2, local.getType());
			ps.setDouble(3, local.getLatitude());
			ps.setDouble(4, local.getLongitude());
			ps.setLong(5, local.getId());
			LOGGER.log(Level.INFO, ps.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			LOGGER.severe(e.getMessage());
		}
	}
}


