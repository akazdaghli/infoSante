package edu.esprit.smartInnov.tests;

import edu.esprit.smartInnov.entites.Laboratoire;
import edu.esprit.smartInnov.entites.Produit;
import edu.esprit.smartInnov.services.LaboratoireService;

public class TestLaboService {

	public static void main(String[] args) {
		LaboratoireService ls = new LaboratoireService();
//		Laboratoire l = new Laboratoire();
//		l.setLibelle("Labo tunsienne...");
//		ls.ajouter(l);
		
		for(Laboratoire l : ls.findAll()) {
			System.out.println(l);
		}
	}
}
