package edu.esprit.smartInnov.entites;

import java.util.Date;

public class Experience {

	private Long id;
	private Utilisateur utilisateur;
	private String titre;
	private String detail;
	private Date datePartage;
	
	public Experience() {
		// TODO Auto-generated constructor stub
	}

	public Experience(Utilisateur utilisateur, String titre, String detail, Date datePartage) {
		super();
		this.utilisateur = utilisateur;
		this.titre = titre;
		this.detail = detail;
		this.datePartage = datePartage;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Date getDatePartage() {
		return datePartage;
	}

	public void setDatePartage(Date datePartage) {
		this.datePartage = datePartage;
	}
	
	
}
