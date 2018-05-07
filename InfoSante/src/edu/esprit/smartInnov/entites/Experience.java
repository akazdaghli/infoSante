package edu.esprit.smartInnov.entites;

import java.io.InputStream;
import java.sql.Date;

public class Experience {

	private Long id;
	private Utilisateur utilisateur;
	private String titre;
	private String detail;
	private int nbrVues;
	private boolean flagVisible;
	private Date datePartage;
	private InputStream photo;
	
	public Experience() {
		// TODO Auto-generated constructor stub
	}

	public Experience(Utilisateur utilisateur, String titre, String detail, Date datePartage, int nbrVues, InputStream photo) {
		super();
		this.utilisateur = utilisateur;
		this.titre = titre;
		this.detail = detail;
		this.datePartage = datePartage;
		this.nbrVues = nbrVues;
		this.photo = photo;
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
	
	public int getNbrVues() {
		return nbrVues;
	}

	public void setNbrVues(int nbrVues) {
		this.nbrVues = nbrVues;
	}

	public boolean isFlagVisible() {
		return flagVisible;
	}

	public void setFlagVisible(boolean flagVisible) {
		this.flagVisible = flagVisible;
	}

	public Date getDatePartage() {
		return datePartage;
	}

	public void setDatePartage(Date datePartage) {
		this.datePartage = datePartage;
	}

	public InputStream getPhoto() {
		return photo;
	}

	public void setPhoto(InputStream photo) {
		this.photo = photo;
	}
	
	
}
