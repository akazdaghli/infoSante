package edu.esprit.smartInnov.entites;

import java.io.Serializable;

public class Specialite implements Serializable {
	private Long id;
	private String libelle;
	private String type;

	public Specialite() {
	}
	
	public Specialite(Long id, String libelle) {
		super();
		this.id = id;
		this.libelle = libelle;
	}

	public Specialite(String libelle) {
		super();
		this.libelle = libelle;
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
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	
}
