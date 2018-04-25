package edu.esprit.smartInnov.entites;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Date;

public class Utilisateur implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String nom;
	private String prenom;
	private String adresse;
	private String login;
	private String pwd;
	private String mail;
	private String numTel;
	private Date dateCreation;
	private Boolean flagActif;
	private String profil;
	private InputStream photo;
	
	public InputStream getPhoto() {
		return photo;
	}

	public void setPhoto(InputStream photo) {
		this.photo = photo;
	}

	public Utilisateur() {
	}

	public Utilisateur(String nom, String prenom, String adresse, String mail, String numTel, Date dateCreation,
			Boolean flagActif, String login, String pwd) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.mail = mail;
		this.numTel = numTel;
		this.dateCreation = dateCreation;
		this.flagActif = flagActif;
		this.login = login;
		this.pwd = pwd;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getNumTel() {
		return numTel;
	}

	public void setNumTel(String numTel) {
		this.numTel = numTel;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Boolean getFlagActif() {
		return flagActif;
	}

	public void setFlagActif(Boolean flagActif) {
		this.flagActif = flagActif;
	}

	public String getProfil() {
		return profil;
	}

	public void setProfil(String profil) {
		this.profil = profil;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	
	
	
}
