package edu.esprit.smartInnov.entites;

import java.io.Serializable;
import java.util.List;

public class Medicament implements Serializable {

	
	private Long id;
	private String libelle;
	private String type;
	private String application;
	private Laboratoire laboratoire;
	private String notice;
	private List<Maladie> maladies;
	
	public Medicament() {
		// TODO Auto-generated constructor stub
	}

	public Medicament(String libelle, Laboratoire laboratoire, String notice, List<Maladie> maladies) {
		super();
		this.libelle = libelle;
		this.laboratoire = laboratoire;
		this.notice = notice;
		this.maladies = maladies;
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

	public Laboratoire getLaboratoire() {
		return laboratoire;
	}

	public void setLaboratoire(Laboratoire laboratoire) {
		this.laboratoire = laboratoire;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public List<Maladie> getMaladies() {
		return maladies;
	}

	public void setMaladies(List<Maladie> maladies) {
		this.maladies = maladies;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}
	
	
}
