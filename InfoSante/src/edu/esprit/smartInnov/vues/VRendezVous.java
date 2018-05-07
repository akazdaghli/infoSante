package edu.esprit.smartInnov.vues;

import edu.esprit.smartInnov.entites.Utilisateur;

public class VRendezVous {

	private String docteur;
	private String patient;
	private String date;
	private String heure;
	private String flagValide;
	private String sujet;
	private Utilisateur docteurUser;
	private Utilisateur patientUser;
	
	public VRendezVous() {
		
	}

	public String getDocteur() {
		return docteur;
	}

	public void setDocteur(String docteur) {
		this.docteur = docteur;
	}

	public String getPatient() {
		return patient;
	}

	public void setPatient(String patient) {
		this.patient = patient;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHeure() {
		return heure;
	}

	public void setHeure(String heure) {
		this.heure = heure;
	}

	public String getFlagValide() {
		return flagValide;
	}

	public void setFlagValide(String flagValide) {
		this.flagValide = flagValide;
	}

	public String getSujet() {
		return sujet;
	}

	public void setSujet(String sujet) {
		this.sujet = sujet;
	}

	public Utilisateur getDocteurUser() {
		return docteurUser;
	}

	public void setDocteurUser(Utilisateur docteurUser) {
		this.docteurUser = docteurUser;
	}

	public Utilisateur getPatientUser() {
		return patientUser;
	}

	public void setPatientUser(Utilisateur patientUser) {
		this.patientUser = patientUser;
	}
	
	
}
