package edu.esprit.smartInnov.vues;

public class VProSante {

	private Long idProSante;
	private String nom;
	private String prenom;
	private String numTel;
	private String mail;
	private String libelle;
	private String adresse;
	
	public VProSante() {
		// TODO Auto-generated constructor stub
	}

	public VProSante(String nom, String prenom, String numTel, String libelle, String adresse, String mail) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.numTel = numTel;
		this.libelle = libelle;
		this.adresse = adresse;
		this.mail = mail;
	}

	public VProSante(Long idProSante, String nom, String prenom, String numTel, String libelle, String adresse, String mail) {
		super();
		this.idProSante = idProSante;
		this.nom = nom;
		this.prenom = prenom;
		this.numTel = numTel;
		this.libelle = libelle;
		this.adresse = adresse;
		this.mail = mail;
	}

	public Long getIdProSante() {
		return idProSante;
	}

	public void setIdProSante(Long idProSante) {
		this.idProSante = idProSante;
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

	public String getNumTel() {
		return numTel;
	}

	public void setNumTel(String numTel) {
		this.numTel = numTel;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
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
	
	
	
}
