package edu.esprit.smartInnov.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.esprit.smartInnov.entites.Avis;
import edu.esprit.smartInnov.entites.Produit;
import edu.esprit.smartInnov.entites.Utilisateur;
import edu.esprit.smartInnov.utils.ConnectionManager;
import edu.esprit.smartInnov.vues.VProduit;

public class AvisService {

	private Connection cnx;
	private static final Logger LOGGER  = Logger.getLogger(AvisService.class.getName());
	public AvisService() {
		cnx = ConnectionManager.getInstance().getCnx();
	}
	
	public void ajouter(Avis avis) {
		String addQuery = "INSERT INTO avis (rate, dateRate, idUtilisateur, idProduit) VALUES (?, ?, ?, ?)";
		try (PreparedStatement ps = cnx.prepareStatement(addQuery);){
			ps.setInt(1, avis.getRate());
			ps.setDate(2, avis.getDateAvis());
			ps.setLong(3, avis.getUtilisateur().getId());
			ps.setLong(4, avis.getProduit().getId());
			LOGGER.log(Level.INFO, ps.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}
	
	public Avis getAvisByProduitAndUser(Produit p, Utilisateur user) {
		String searchQuery = "SELECT * FROM avis WHERE idUtilisateur = ? AND idProduit = ?";
		try (PreparedStatement ps = cnx.prepareStatement(searchQuery);){
			ps.setLong(1,user.getId());
			ps.setLong(2, p.getId());
			LOGGER.log(Level.INFO, ps.toString());
			ResultSet rs = ps.executeQuery();
			rs.next();
			Avis a= new Avis();
			a.setId(rs.getLong("id"));
			a.setDateAvis(rs.getDate("dateRate"));
			a.setRate(rs.getInt("rate"));
			a.setProduit(p);
			a.setUtilisateur(user);
			return a;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		return null;
	}
	
	public int getAvgRateByProduit(Produit p) {
		String query = "SELECT AVG(rate) AS rate FROM avis WHERE idProduit = ?";
		try (PreparedStatement ps = cnx.prepareStatement(query);){
			ps.setLong(1, p.getId());
			LOGGER.log(Level.INFO, ps.toString());
			ResultSet rs = ps.executeQuery();
			rs.next();
			return (int) Math.round(rs.getDouble("rate"));
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		return -1;
	}
	
	public int getAvgRateByVProduit(VProduit p) {
		String query = "SELECT AVG(rate) AS rate FROM avis WHERE idProduit = ?";
		try (PreparedStatement ps = cnx.prepareStatement(query);){
			ps.setLong(1, p.getId());
			LOGGER.log(Level.INFO, ps.toString());
			ResultSet rs = ps.executeQuery();
			rs.next();
			return (int) Math.round(rs.getDouble("rate"));
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		return -1;
	}
}
