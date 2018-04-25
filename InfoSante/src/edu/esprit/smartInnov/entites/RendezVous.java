package edu.esprit.smartInnov.entites;

import java.io.Serializable;
import java.util.Date;

public class RendezVous implements Serializable{

	
	private ProSante medecin;
	private Patient patient;
	private Date dateRendezVs;
	
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
	
	
}
