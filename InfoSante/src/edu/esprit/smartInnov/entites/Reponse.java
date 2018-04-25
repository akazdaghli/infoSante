package edu.esprit.smartInnov.entites;

import java.io.Serializable;
import java.util.Date;

public class Reponse implements Serializable{

	private Long id;
	private Utilisateur repondeur;
	private Date dateReponse;
	
	public Reponse() {
		// TODO Auto-generated constructor stub
	}

	public Reponse(Utilisateur repondeur, Date dateReponse) {
		super();
		this.repondeur = repondeur;
		this.dateReponse = dateReponse;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Utilisateur getRepondeur() {
		return repondeur;
	}

	public void setRepondeur(Utilisateur repondeur) {
		this.repondeur = repondeur;
	}

	public Date getDateReponse() {
		return dateReponse;
	}

	public void setDateReponse(Date dateReponse) {
		this.dateReponse = dateReponse;
	}
	
	
}
