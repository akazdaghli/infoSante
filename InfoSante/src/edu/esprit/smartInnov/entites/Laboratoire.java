package edu.esprit.smartInnov.entites;

import java.io.Serializable;

public class Laboratoire implements Serializable {
	
	private Long id;
	private String libelle;

	public Laboratoire() {
		// TODO Auto-generated constructor stub
	}

	public Laboratoire(Long id, String libelle) {
		super();
		this.id = id;
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
	
	
}
