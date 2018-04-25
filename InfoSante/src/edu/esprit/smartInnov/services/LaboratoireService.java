package edu.esprit.smartInnov.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.esprit.smartInnov.entites.Laboratoire;
import edu.esprit.smartInnov.utils.ConnectionManager;

public class LaboratoireService {
	
	private Connection cnx;
	
	public LaboratoireService() {
		cnx = ConnectionManager.getInstance().getCnx();
	}
	
	public void ajouter(Laboratoire labo) {
		String addQuery = "INSERT INTO Laboratoire (libelle) VALUES (?)";
		try {
			PreparedStatement ps = cnx.prepareStatement(addQuery);
			ps.setString(1, labo.getLibelle());
			ps.executeUpdate();
			ps.close();
			System.out.println("Labo ajouté");
		} catch (SQLException e) {
			System.out.println("Labo non ajouté");
			e.printStackTrace();
		}
		
	}
	
	public Laboratoire findById(Long id) {
		Laboratoire l = new Laboratoire();
		String searchQuery = "SELECT * FROM Laboratoire WHERE id=?";
		try {
			PreparedStatement ps = cnx.prepareStatement(searchQuery);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				
				l.setId(rs.getLong(1));
				l.setLibelle(rs.getString(2));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return l;
		
	}
	
	public Laboratoire findByLibelle(String libelle) {
		Laboratoire l = new Laboratoire();
		String searchQuery = "SELECT * FROM Laboratoire WHERE libelle like '%"+libelle+"%'";;
		try {
			Statement ps = cnx.createStatement();
			ResultSet rs = ps.executeQuery(searchQuery);
			while(rs.next()) {
				
				l.setId(rs.getLong(1));
				l.setLibelle(rs.getString(2));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return l;
		
	}
	
	public List<Laboratoire> findAll(){
		List<Laboratoire> labos = new ArrayList<>();
		String searchQuery = "SELECT * FROM Laboratoire";
		Statement s;
		try {
			s = cnx.createStatement();
			ResultSet rs = s.executeQuery(searchQuery);
			while(rs.next()) {
				Laboratoire l = new Laboratoire();
				l.setId(rs.getLong(1));
				l.setLibelle(rs.getString(2));
				labos.add(l);
			}
			rs.close();
			s.close();
			System.out.println(labos.size()+" laboratoires trouvés!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return labos;
		
	}
	
	
	
	

}
