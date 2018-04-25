package edu.esprit.smartInnov.vues;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;

public class VProduit implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String libelle;
	private String categorie;
	private String modeEmploi;
	private InputStream image;
	private String labo;
	
	public VProduit() {
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

	public InputStream getImage() {
		return image;
	}

	public void setImage(InputStream image) {
		this.image = image;
	}

	public String getLabo() {
		return labo;
	}

	public void setLabo(String labo) {
		this.labo = labo;
	}
	
	
}
