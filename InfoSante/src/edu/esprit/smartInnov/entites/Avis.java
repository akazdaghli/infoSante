package edu.esprit.smartInnov.entites;

import java.io.Serializable;
import java.sql.Date;

import edu.esprit.smartInnov.vues.VProduit;

public class Avis implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private int rate;
	private Date dateAvis;
	private VProduit produit;
	private Utilisateur utilisateur;
	
	public Avis() {
	
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public Date getDateAvis() {
		return dateAvis;
	}

	public void setDateAvis(Date dateAvis) {
		this.dateAvis = dateAvis;
	}

	public VProduit getProduit() {
		return produit;
	}

	public void setProduit(VProduit produit) {
		this.produit = produit;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	
	
}
