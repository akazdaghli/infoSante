package edu.esprit.smartInnov.entites;

import java.util.Date;

public class EtatPharmacie {

	private Long id;
	private Date dateDebEtat;
	private Date dateFinEtat;
	private Boolean flagOuvert;
	
	public EtatPharmacie() {
		// TODO Auto-generated constructor stub
	}

	public EtatPharmacie(Date dateDebEtat, Date dateFinEtat, Boolean flagOuvert) {
		super();
		this.dateDebEtat = dateDebEtat;
		this.dateFinEtat = dateFinEtat;
		this.flagOuvert = flagOuvert;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateDebEtat() {
		return dateDebEtat;
	}

	public void setDateDebEtat(Date dateDebEtat) {
		this.dateDebEtat = dateDebEtat;
	}

	public Date getDateFinEtat() {
		return dateFinEtat;
	}

	public void setDateFinEtat(Date dateFinEtat) {
		this.dateFinEtat = dateFinEtat;
	}

	public Boolean getFlagOuvert() {
		return flagOuvert;
	}

	public void setFlagOuvert(Boolean flagOuvert) {
		this.flagOuvert = flagOuvert;
	}
	
	
}
