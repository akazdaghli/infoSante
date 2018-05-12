package edu.esprit.smartInnov.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.esprit.smartInnov.entites.Admin;
import edu.esprit.smartInnov.entites.Patient;
import edu.esprit.smartInnov.entites.ProSante;
import edu.esprit.smartInnov.entites.Utilisateur;
import edu.esprit.smartInnov.services.LocalService;
import edu.esprit.smartInnov.services.SpecialiteService;

public class UtilisateurRowMapper {

	private static final String IDCOLUMN ="id";
	private static final String NOMCOLUMN ="nom";
	private static final String PRENOMCOLUMN ="prenom";
	private static final String MAILCOLUMN ="mail";
	private static final String ADRESSECOLUMN ="adresse";
	private static final String ACTIFCOLUMN ="flagActif";
	private static final String DATECREATIONCOLUMN ="dateCreation";
	private static final String LOGINCOLUMN ="login";
	private static final String PWDCOLUMN ="pwd";
	private static final String NUMTELCOLUMN ="numTel";
	private static final String SPECIALITECOLUMN ="idSpecialite";
	private static final String LOCALCOLUMN ="idLocal";
	private static final String PHOTO ="photo";
	private static final String PROFIL ="profil";
	private Utilisateur user ;
	private List<Utilisateur> users ;
	private SpecialiteService ss ;
	private LocalService ls;

	public UtilisateurRowMapper(ResultSet rs) {
		users = new ArrayList<>();
		try {
			while(rs.next()) {
				if(rs.getString(PROFIL).equals(IConstants.Profils.ADMINISTRATUER)) {
					user = new Admin();
					user.setProfil(IConstants.Profils.ADMINISTRATUER);
				}else if(rs.getString(PROFIL).equals(IConstants.Profils.MEDECIN)) {
					user = new ProSante();
					user.setProfil(IConstants.Profils.MEDECIN);
				}else if(rs.getString(PROFIL).equals(IConstants.Profils.PATIENT)) {
					user = new Patient();
					user.setProfil(IConstants.Profils.PATIENT);
				}
				user.setId(rs.getLong(IDCOLUMN));
				user.setNom(rs.getString(NOMCOLUMN));
				user.setPrenom(rs.getString(PRENOMCOLUMN));
				user.setMail(rs.getString(MAILCOLUMN));
				user.setAdresse(rs.getString(ADRESSECOLUMN));
				user.setFlagActif(rs.getBoolean(ACTIFCOLUMN));
				try {
					user.setDateCreation(rs.getDate(DATECREATIONCOLUMN));
				} catch (Exception e) {
					user.setDateCreation(Utilitaire.getSqlDateFromUtilDate(new Date()));
				}
				user.setLogin(rs.getString(LOGINCOLUMN));
				user.setPwd(rs.getString(PWDCOLUMN));
				user.setNumTel(rs.getString(NUMTELCOLUMN));
				user.setPhoto(rs.getBinaryStream(PHOTO));
				if(user instanceof ProSante) {
					ss = new SpecialiteService();
					ls = new LocalService();
					((ProSante) user).setSpecialite(ss.getSpeciliteById(rs.getLong(SPECIALITECOLUMN)));
					((ProSante) user).setLocal(ls.getLocalById(rs.getLong(LOCALCOLUMN)));
				}
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Utilisateur getFirstUtilisateur() {
		if(users.isEmpty()) {
			return null;
		}
		return users.get(0);
	}
	
	public List<Utilisateur> getUtilisateurs() {
		return users;
	}


}
