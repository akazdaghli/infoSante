package edu.esprit.smartInnov.services;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.esprit.smartInnov.entites.Maladie;
import edu.esprit.smartInnov.entites.Phytotherapie;
import edu.esprit.smartInnov.utils.ConnectionManager;

public class PhytotherapieService implements Serializable {
	
	Connection cnx;
	private static final Logger LOGGER = Logger.getLogger(PhytotherapieService.class.getName());
	
	public PhytotherapieService() {
		cnx = ConnectionManager.getInstance().getCnx();
	}
	
	public void ajouter(Phytotherapie p) {
		String addQuery = "INSERT INTO Phytotherapie (libelle, detail) VALUES (?, ?)";
		try {
			PreparedStatement ps = cnx.prepareStatement(addQuery);
			ps.setString(1, p.getLibelle());
			ps.setString(2, p.getDetail());
			ps.executeUpdate();
			LOGGER.log(Level.INFO, ps.toString());
			ps.close();
			LOGGER.log(Level.INFO,"Phytotherapie ajoutée");
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE,"Phytotherapie non ajoutée");
			LOGGER.log(Level.SEVERE,e.getMessage());
		}
		
	}
	
	public Phytotherapie getNextPhyto(Long id) {
		String searchQuery="SELECT * FROM phytotherapie WHERE id = ?";
		try {
			PreparedStatement ps = cnx.prepareStatement(searchQuery);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Phytotherapie p = new Phytotherapie();
				p.setId(rs.getLong(1));
				p.setLibelle(rs.getString(2));
				p.setDetail(rs.getString(3));
				p.setPhoto(rs.getBinaryStream(5));
				return p;
			}
			LOGGER.log(Level.INFO, ps.toString());
			ps.close();
			LOGGER.log(Level.INFO,"Phytotherapie ajoutée");
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE,"Phytotherapie non ajoutée");
			LOGGER.log(Level.SEVERE,e.getMessage());
		}
		return null;
	}
	
	public List<Phytotherapie> getAllPhytotherapie(){
		List<Phytotherapie> phytos = new ArrayList<>();
		String searchQuery = "SELECT * FROM Phytotherapie";
		try {
			Statement s = cnx.createStatement();
			ResultSet rs = s.executeQuery(searchQuery);
			while(rs.next()) {
				Phytotherapie p = new Phytotherapie();
				p.setDetail(rs.getString(3));
				p.setLibelle(rs.getString(2));
				p.setId(rs.getLong(1));
				phytos.add(p);
			}
			LOGGER.log(Level.INFO,phytos.size()+" phytotherapies trouvées!!");
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE,e.getMessage());
		}
		return phytos;
	}
	
	public List<Phytotherapie> getListPhytotherapieByMaladie(Maladie m){
		List<Phytotherapie> phytos = new ArrayList<>();
		String searchQuery = "SELECT * FROM Phytotherapie WHERE id in(SELECT idPhytotherapie FROM maladie_phytotherapie WHERE idMaladie = ?)";
		
		try(PreparedStatement ps = cnx.prepareStatement(searchQuery);) {
			ps.setLong(1, m.getId());
			LOGGER.log(Level.INFO,ps.toString());
			ResultSet rs = ps.executeQuery(searchQuery);
			while(rs.next()) {
				Phytotherapie p = new Phytotherapie();
				p.setDetail(rs.getString(3));
				p.setLibelle(rs.getString(2));
				p.setId(rs.getLong(1));
				phytos.add(p);
			}
			LOGGER.log(Level.INFO,phytos.size()+" phytotherapies trouvées!!");
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE,e.getMessage());
		}
		return phytos;
	}

}
