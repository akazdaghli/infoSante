package edu.esprit.smartInnov.entites;

import java.io.Serializable;
import java.util.List;

public class ProSante extends Utilisateur implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8929880227371208568L;
	private Specialite specialite;
	private Local local;
//	private List<Patient> patients;
	
	
	public ProSante() {
		super();
	}


	public ProSante(Specialite specialite, Local local) {
		super();
		this.specialite = specialite;
		this.local = local;
//		this.patients = patients;
	}
	
	public ProSante(Utilisateur user) {
		super(user.getNom(), user.getPrenom(), user.getAdresse(), user.getMail(), user.getNumTel(), user.getDateCreation(), user.getFlagActif(),user.getLogin(), user.getPwd());
	}


	public Local getLocal() {
		return local;
	}


	public void setLocal(Local local) {
		this.local = local;
	}


	public Specialite getSpecialite() {
		return specialite;
	}


	public void setSpecialite(Specialite specialite) {
		this.specialite = specialite;
	}


	@Override
	public String toString() {
		return "nom : "+super.getNom()+"-- prenom = "+super.getPrenom();
	}


//	public List<Patient> getPatients() {
//		return patients;
//	}
//
//
//	public void setPatients(List<Patient> patients) {
//		this.patients = patients;
//	}


	
	
	
	

}
