package edu.esprit.smartInnov.vues;

import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import edu.esprit.smartInnov.entites.Experience;
import edu.esprit.smartInnov.services.ExperienceService;
import edu.esprit.smartInnov.utils.IConstants;

public class VExperience implements Serializable{
	
	private Long id;
	private String titre;
	private String date;
	private String user;
	private String nbrComm;
	private String nbrVues;
	private String detail;
	private String photo;
	
	private ExperienceService experienceService;
	private SimpleDateFormat sdf = new SimpleDateFormat(IConstants.DATEPATTERN);
	public VExperience() {
		
	}
	
	public VExperience(Experience e) {
		experienceService = new ExperienceService();
		this.id = e.getId();
		this.titre =  e.getTitre();
		this.date = sdf.format(e.getDatePartage());
		this.user = e.getUtilisateur().getNom() +" "+e.getUtilisateur().getPrenom(); 
		this.nbrVues = e.getNbrVues()+"";
		this.nbrComm = experienceService.getNbrCommByExperience(e)+"";
		this.detail = e.getDetail();
		this.photo = e.getPhoto();
	}
	
	public static List<VExperience> getListVExperienceFromListExperience(List<Experience> experiences){
		List<VExperience> list = new ArrayList<>();
		for (Experience experience : experiences) {
			list.add(new VExperience(experience));
		}
		return list;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getNbrComm() {
		return nbrComm;
	}

	public void setNbrComm(String nbrComm) {
		this.nbrComm = nbrComm;
	}

	public String getNbrVues() {
		return nbrVues;
	}

	public void setNbrVues(String nbrVues) {
		this.nbrVues = nbrVues;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

}
