package edu.esprit.smartInnov.entites;

import java.io.Serializable;
import java.util.List;

public class Maladie implements Serializable{

	private Long id;
	private String libelle;
	private List<Symptome> symptomes;
	private List<Medicament> medicaments;
	
	
	public Maladie() {
		// TODO Auto-generated constructor stub
	}


	public Maladie(String libelle, List<Symptome> symptomes, List<Medicament> medicaments) {
		super();
		this.libelle = libelle;
		this.symptomes = symptomes;
		this.medicaments = medicaments;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getLibelle() {
		return libelle;
	}


	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}


	public List<Symptome> getSymptomes() {
		return symptomes;
	}


	public void setSymptomes(List<Symptome> symptomes) {
		this.symptomes = symptomes;
	}


	public List<Medicament> getMedicaments() {
		return medicaments;
	}


	public void setMedicaments(List<Medicament> medicaments) {
		this.medicaments = medicaments;
	}
	
	
}
