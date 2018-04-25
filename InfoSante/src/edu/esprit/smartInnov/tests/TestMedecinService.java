package edu.esprit.smartInnov.tests;

import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.esprit.smartInnov.entites.ProSante;
import edu.esprit.smartInnov.entites.Admin;
import edu.esprit.smartInnov.entites.Local;
import edu.esprit.smartInnov.entites.Patient;
import edu.esprit.smartInnov.entites.Specialite;
import edu.esprit.smartInnov.entites.Utilisateur;
import edu.esprit.smartInnov.services.MedecinService;
import edu.esprit.smartInnov.services.PatientService;
import edu.esprit.smartInnov.services.SpecialiteService;
import edu.esprit.smartInnov.services.UtilisateurService;
import edu.esprit.smartInnov.utils.Utilitaire;

public class TestMedecinService {

	public static void main(String[] args) {

		// SpecialiteService specialiteService = new SpecialiteService();
		// MedecinService medecinService = new MedecinService();
		// PatientService patientService = new PatientService();
		// Specialite s = specialiteService.getSpeciliteById(1L);
		//
		// ProSante med = new ProSante();
		// med.setAdresse("Manouba");
		// med.setNom("Nader");
		// med.setFlagActif(Boolean.TRUE);
		// med.setPrenom("Kazdaghli");
		// med.setMail("nader@yahoo.com");
		// med.setNumTel("55544520");
		// med.setAdresseCabinet("Bardo");
		// med.setSpecialite(s);
		// med.setLogin("abc");
		// try {
		// med.setPwd(Utilitaire.hashMD5Crypt("aymen123"));
		// } catch (NoSuchAlgorithmException e) {
		// System.out.println("erreur de cryptage du password");
		// e.printStackTrace();
		// }
		// medecinService.ajouter(med);

		// Pharmacien p = new Pharmacien();
		// p.setNom("Belaili");
		// p.setPrenom("Youssef");
		// p.setMail("yBelaili@adaming.fr");
		// p.setNumTel("22222222");
		// p.setDateCreation(new Date());
		// p.setAdresse("Bardo");
		// p.setFlagActif(Boolean.TRUE);
		// pharmacienService.ajouter(p);

		// Patient patient = new Patient();
		// patient.setAdresse("Tunis");
		// patient.setFlagActif(Boolean.TRUE);
		// patient.setMail("foulen@gmail.com");
		// patient.setNom("foulen");
		// patient.setPrenom("ben foulen");
		// patient.setNumTel("xxxxxxxx");
		// patient.setDateCreation(new java.sql.Date(2018, 12, 10));
		// patientService.ajouter(patient);

		// medecinService.supprimeMedecin(1L);

		// List<Medecin> meds = medecinService.getMedecins();
		// for(Medecin m : meds) {
		// System.out.println(m);
		// }

		// System.out.println(medecinService.getMedecinById(6L));

		UtilisateurService us = new UtilisateurService();
//		Utilisateur u = us.getUserByLoginAndPassword("aymen", "aymen");
		Utilisateur u = new Admin();
		u.setNom("Khalil");
		u.setPrenom("Chammem");
		u.setAdresse("marsa");
		u.setDateCreation(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
		u.setFlagActif(Boolean.TRUE);
		u.setNumTel("20697023");
		u.setLogin("kchammem");
		u.setPwd("kchammem");
//		((ProSante) u).setSpecialite(new Specialite(1L,null));
//		((ProSante) u).setLocal(new Local(1L,null,null));
		us.ajouter(u);
		System.out.println(u);

	}

}
