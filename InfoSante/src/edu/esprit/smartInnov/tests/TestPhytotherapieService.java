package edu.esprit.smartInnov.tests;

import edu.esprit.smartInnov.entites.Phytotherapie;
import edu.esprit.smartInnov.services.PhytotherapieService;

public class TestPhytotherapieService {

	public static void main(String[] args) {
		PhytotherapieService ps = new PhytotherapieService();
//		Phytotherapie p = new Phytotherapie();
//		p.setDetail("XXXXXXX");
//		p.setLibelle("YYYYYYYY");
//		ps.ajouter(p);
		
		for(Phytotherapie p : ps.getAllPhytotherapie()) {
			System.out.println(p);
		}
	}
}
