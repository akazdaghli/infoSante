package edu.esprit.smartInnov.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.esprit.smartInnov.vues.VProSante;

public class VProSanteRowMapper {

	private List<VProSante> pros;
	public VProSanteRowMapper(ResultSet rs) {
		pros = new ArrayList<>();
		if(rs != null) {
			try {
				while(rs.next()) {
					VProSante v = new VProSante();
					v.setIdProSante(rs.getLong("id"));
					v.setAdresse(rs.getString("adresse"));
					v.setNom(rs.getString("nom"));
					v.setPrenom(rs.getString("prenom"));
					v.setLibelle(rs.getString("libelle"));
					v.setMail(rs.getString("mail"));
					v.setNumTel(rs.getString("numTel"));
					pros.add(v);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public List<VProSante> getVProSantes(){
		return pros;
	}
}
