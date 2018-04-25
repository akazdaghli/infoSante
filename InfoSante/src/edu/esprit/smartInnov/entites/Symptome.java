package edu.esprit.smartInnov.entites;

import java.io.Serializable;
import java.util.List;

public class Symptome implements Serializable{

	private Long id;
	private String designation;
	private List<Maladie> maladies;
	
	public Symptome() {
		// TODO Auto-generated constructor stub
	}

	public Symptome(String designation) {
		super();
		this.designation = designation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public List<Maladie> getMaladies() {
		return maladies;
	}

	public void setMaladies(List<Maladie> maladies) {
		this.maladies = maladies;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Symptome other = (Symptome) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
