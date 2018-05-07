package edu.esprit.smartInnov.entites;

import java.io.Serializable;
import java.sql.Date;

public class RendezVous implements Serializable{

	
	private ProSante medecin;
	private Patient patient;
	private Date dateRendezVs;
	private String heureRendezVs;
	private String sujet;
	private boolean flagValide;
	
	public RendezVous() {
		// TODO Auto-generated constructor stub
	}
	
	public RendezVous(ProSante medecin, Patient patient, Date dateRendezVs) {
		super();
		this.medecin = medecin;
		this.patient = patient;
		this.dateRendezVs = dateRendezVs;
	}

	public ProSante getMedecin() {
		return medecin;
	}

	public void setMedecin(ProSante medecin) {
		this.medecin = medecin;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Date getDateRendezVs() {
		return dateRendezVs;
	}

	public void setDateRendezVs(Date dateRendezVs) {
		this.dateRendezVs = dateRendezVs;
	}

	public String getHeureRendezVs() {
		return heureRendezVs;
	}

	public void setHeureRendezVs(String heureRendezVs) {
		this.heureRendezVs = heureRendezVs;
	}

	public String getSujet() {
		return sujet;
	}

	public void setSujet(String sujet) {
		this.sujet = sujet;
	}

	public boolean isFlagValide() {
		return flagValide;
	}

	public void setFlagValide(boolean flagValide) {
		this.flagValide = flagValide;
	}
	
	
}
