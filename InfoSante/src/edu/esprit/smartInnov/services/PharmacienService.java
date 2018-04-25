package edu.esprit.smartInnov.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

import edu.esprit.smartInnov.entites.Pharmacien;
import edu.esprit.smartInnov.utils.ConnectionManager;
import edu.esprit.smartInnov.utils.IConstants;

public class PharmacienService {

	private Connection cnx;
	
	public PharmacienService() {
		cnx = ConnectionManager.getInstance().getCnx();
	}
	
	public void ajouter(Pharmacien pharmacien) {
		String addQuery = "INSERT INTO Utilisateur (nom, prenom, adresse, mail, numTel, flagActif, profil) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement ps = cnx.prepareStatement(addQuery);
			ps.setString(1, pharmacien.getNom());
			ps.setString(2, pharmacien.getPrenom());
			ps.setString(3, pharmacien.getAdresse());
			ps.setString(4, pharmacien.getMail());
			ps.setString(5, pharmacien.getNumTel());
			ps.setBoolean(6, pharmacien.getFlagActif());
			ps.setString(7, IConstants.Profils.PHARMACIEN);
			ps.executeUpdate();
			ps.close();
			System.out.println("pharmacien ajouté");
			System.out.println("****************");
		} catch (SQLException e) {
			System.out.println("pharmacien non ajouté");
			System.out.println("****************");
			e.printStackTrace();
		}
		
	}
}
