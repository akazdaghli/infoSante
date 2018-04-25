package edu.esprit.smartInnov.entites;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Conseil implements Serializable{

	private Long id;
	private String detail;
	private Utilisateur demandeurConseil;
	private Date date;
	private List<Reponse> reponses;
	
	public Conseil() {
		// TODO Auto-generated constructor stub
	}

	public Conseil(String detail, Utilisateur demandeurConseil, Date date, List<Reponse> reponses) {
		super();
		this.detail = detail;
		this.demandeurConseil = demandeurConseil;
		this.date = date;
		this.reponses = reponses;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Utilisateur getDemandeurConseil() {
		return demandeurConseil;
	}

	public void setDemandeurConseil(Utilisateur demandeurConseil) {
		this.demandeurConseil = demandeurConseil;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Reponse> getReponses() {
		return reponses;
	}

	public void setReponses(List<Reponse> reponses) {
		this.reponses = reponses;
	}
	
	
}
