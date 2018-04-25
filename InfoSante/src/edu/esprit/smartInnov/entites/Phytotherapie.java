package edu.esprit.smartInnov.entites;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

public class Phytotherapie implements Serializable{
	private Long id;
	private String libelle;
	private String detail;
	private InputStream photo;
	private List<Maladie> maladies;
	
	public Phytotherapie() {
		// TODO Auto-generated constructor stub
	}

	public Phytotherapie(String libelle, String detail, List<Maladie> maladies) {
		super();
		this.libelle = libelle;
		this.detail = detail;
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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public InputStream getPhoto() {
		return photo;
	}

	public void setPhoto(InputStream photo) {
		this.photo = photo;
	}

	public List<Maladie> getMaladies() {
		return maladies;
	}

	public void setMaladies(List<Maladie> maladies) {
		this.maladies = maladies;
	}
	
	

}
