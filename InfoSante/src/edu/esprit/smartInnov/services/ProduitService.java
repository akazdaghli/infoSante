package edu.esprit.smartInnov.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.esprit.smartInnov.entites.Produit;
import edu.esprit.smartInnov.utils.ConnectionManager;
import edu.esprit.smartInnov.vues.VProduit;

public class ProduitService {

	private Connection cnx ;
	private static final Logger LOGGER = Logger.getLogger(ProduitService.class.getName());
	public ProduitService() {
		cnx = ConnectionManager.getInstance().getCnx();
	}
	
	public void ajouter (Produit p) {
		String addQuery = "INSERT INTO Produit (libelle, categorie, idLaboratoire, image, modeEmploi) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement ps ;
		try {
			ps = cnx.prepareStatement(addQuery);
			ps.setString(1, p.getLibelle());
			ps.setString(2, p.getCategorie());
			ps.setLong(3, p.getLaboratoire().getId());
			ps.setBinaryStream(4, p.getImage());
			ps.setString(5, p.getModeEmploi());
			ps.executeUpdate();
			ps.close();
			LOGGER.log(Level.INFO,"Produit ajouté");
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE,"Produit non ajouté");
			LOGGER.log(Level.SEVERE,e.getMessage());
		}
				
	}
	
	public Produit findById(Long id) {
		Produit p = null;
		LaboratoireService ls = new LaboratoireService();
		String searchQuery = "SELECT * FROM Produit WHERE id=?";
		LOGGER.log(Level.INFO,searchQuery);
		try {
			PreparedStatement ps = cnx.prepareStatement(searchQuery);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				p = new Produit();
				p.setCategorie(rs.getString(3));
				p.setId(rs.getLong(1));
				p.setLibelle(rs.getString(2));
				p.setLaboratoire(ls.findById(rs.getLong(4)));
				p.setImage(rs.getBinaryStream(5));
				p.setModeEmploi(rs.getString(6));
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE,"Problem retrieving product by id");
			LOGGER.log(Level.SEVERE,e.getMessage());
		}
		return p;
	}
	
	public List<Produit> findAll(){
		Produit p = null;
		LaboratoireService ls = new LaboratoireService();
		List<Produit> produits = new ArrayList<>();
		String searchQuery = "SELECT * FROM Produit";
		LOGGER.log(Level.INFO,searchQuery);
		try(Statement s = cnx.createStatement();
			ResultSet rs = s.executeQuery(searchQuery);) {
			while (rs.next()) {
				p = new Produit();
				p.setCategorie(rs.getString(3));
				p.setId(rs.getLong(1));
				p.setLibelle(rs.getString(2));
				p.setLaboratoire(ls.findById(rs.getLong(4)));
				p.setImage(rs.getBinaryStream(5));
				p.setModeEmploi(rs.getString(6));
				produits.add(p);
			}
			LOGGER.log(Level.INFO,produits.size()+ " produits trouvés");
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE,"Problem retrieving products");
			
		}
		return produits;
	}
	
	public List<VProduit> getAllVProduit(){
		String searchQuery = "SELECT * FROM v_produit";
		List<VProduit> produits = new ArrayList<>();
		VProduit p;
		LOGGER.log(Level.INFO,searchQuery);
		try (Statement st = cnx.createStatement();
			ResultSet rs = st.executeQuery(searchQuery);){
			while (rs.next()) {
				p= new VProduit();
				p.setId(rs.getLong(1));
				p.setLibelle(rs.getString(2));
				p.setCategorie(rs.getString(3));
				p.setImage(rs.getBinaryStream("image"));
				p.setLabo(rs.getString("labo"));
				p.setModeEmploi(rs.getString("modeEmploi"));
				produits.add(p);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE,e.getMessage());
		}
		return produits;
	}
	
	public List<VProduit> getListVProduitFiltred(VProduit v){
		StringBuilder searchQuery = new StringBuilder("SELECT * FROM v_produit WHERE 1=1 ");
		if(v.getLibelle() != null && !v.getLibelle().isEmpty()) {
			searchQuery.append(" AND libelle like '%"+v.getLibelle()+"%'");
		}
		if(v.getCategorie() != null && !v.getCategorie().isEmpty()) {
			searchQuery.append(" AND categorie like '%"+v.getCategorie()+"%'");
		}
		if(v.getLabo() != null && !v.getLabo().isEmpty()) {
			searchQuery.append(" AND labo like '%"+v.getLabo()+"%'");
		}
		LOGGER.log(Level.INFO,searchQuery.toString());
		List<VProduit> produits = new ArrayList<>();
		VProduit p;
		try (Statement st = cnx.createStatement();
				ResultSet rs = st.executeQuery(searchQuery.toString());){
				while (rs.next()) {
					p= new VProduit();
					p.setId(rs.getLong(1));
					p.setLibelle(rs.getString(2));
					p.setCategorie(rs.getString(3));
					p.setImage(rs.getBinaryStream("image"));
					p.setLabo(rs.getString("labo"));
					p.setModeEmploi(rs.getString("modeEmploi"));
					produits.add(p);
				}
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE,e.getMessage());
			}
			return produits;
	}
	
	public void supprimerProduit(Long id) {
		String deleteQuery = "DELETE FROM produit WHERE id=?";
		LOGGER.log(Level.INFO,deleteQuery.toString());
		try (PreparedStatement st = cnx.prepareStatement(deleteQuery);){
			st.setLong(1, id);
			st.executeUpdate();
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE,e.getMessage());
			}
	}
}
