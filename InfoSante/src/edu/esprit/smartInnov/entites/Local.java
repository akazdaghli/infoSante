package edu.esprit.smartInnov.entites;

import java.io.Serializable;
import java.util.List;

public class Local implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6652480426992726385L;
	private Long id;
	private String adresse;
	private String type;
	
	public Local() {
		// TODO Auto-generated constructor stub
	}
	
	public Local(String adresse, String type) {
		this.adresse = adresse;
		this.type = type;
	}

	public Local(String adresse, Boolean etatsWeekEnd, String type) {
		super();
	
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
