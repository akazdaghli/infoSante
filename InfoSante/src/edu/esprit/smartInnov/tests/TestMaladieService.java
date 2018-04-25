package edu.esprit.smartInnov.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.esprit.smartInnov.entites.Maladie;
import edu.esprit.smartInnov.entites.Symptome;
import edu.esprit.smartInnov.services.MaladieService;
import edu.esprit.smartInnov.services.SymptomeService;

public class TestMaladieService {

	public static void main(String[] args) {
		Symptome s1 = new Symptome();
		s1.setId(7L);
		s1.setDesignation("congestion");
		Symptome s2 = new Symptome();
		s2.setId(2L);
		s1.setDesignation("fievre");
		Symptome s3 = new Symptome();
		s3.setId(5L);
		s1.setDesignation("frissons");
		List<Symptome> symps = new ArrayList<>();
		symps.add(s1);
		symps.add(s2);
		symps.add(s3);
		MaladieService ms = new MaladieService();
		ms.calculerPourcentage(symps);
		
	}

}
