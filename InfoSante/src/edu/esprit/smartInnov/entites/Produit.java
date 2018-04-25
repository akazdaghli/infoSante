package edu.esprit.smartInnov.entites;

import java.io.InputStream;
import java.io.Serializable;

import com.mysql.jdbc.Blob;

public class Produit implements Serializable{
	
	private Long id;
	private String libelle;
	private String categorie;
	private String modeEmploi;
	private Laboratoire laboratoire;
	private InputStream image;
	
	public Produit() {
		// TODO Auto-generated constructor stub
	}

	public Produit(String libelle, String categorie, Laboratoire laboratoire) {
		super();
		this.libelle = libelle;
		this.categorie = categorie;
		this.laboratoire = laboratoire;
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

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public String getModeEmploi() {
		return modeEmploi;
	}

	public void setModeEmploi(String modeEmploi) {
		this.modeEmploi = modeEmploi;
	}

	public Laboratoire getLaboratoire() {
		return laboratoire;
	}

	public void setLaboratoire(Laboratoire laboratoire) {
		this.laboratoire = laboratoire;
	}

	public InputStream getImage() {
		return image;
	}

	public void setImage(InputStream image) {
		this.image = image;
	}
	
	

}
