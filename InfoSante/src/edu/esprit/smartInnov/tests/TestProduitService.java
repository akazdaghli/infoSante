package edu.esprit.smartInnov.tests;

import edu.esprit.smartInnov.entites.Laboratoire;
import edu.esprit.smartInnov.entites.Produit;
import edu.esprit.smartInnov.services.LaboratoireService;
import edu.esprit.smartInnov.services.ProduitService;

public class TestProduitService {

	public static void main(String[] args) {
		
		ProduitService produitService = new ProduitService();
		for(Produit p:produitService.findAll()) {
			System.err.println(p);
		}
	}

}
