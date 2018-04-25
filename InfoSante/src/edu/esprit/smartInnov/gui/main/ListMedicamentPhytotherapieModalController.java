package edu.esprit.smartInnov.gui.main;

import java.util.List;

import edu.esprit.smartInnov.entites.Maladie;
import edu.esprit.smartInnov.entites.Medicament;
import edu.esprit.smartInnov.entites.Phytotherapie;
import edu.esprit.smartInnov.services.MedicamentService;
import edu.esprit.smartInnov.services.PhytotherapieService;

public class ListMedicamentPhytotherapieModalController {

	private MedicamentService medicamentService;
	private PhytotherapieService phytotherapieService;
	private List<Medicament> meds;
	private List<Phytotherapie> phytos;
	public void initComponents(Maladie m) {
		medicamentService = new MedicamentService();
		phytotherapieService = new PhytotherapieService();
		meds = medicamentService.getListMedicamentsByMaladie(m);
		phytos = phytotherapieService.getListPhytotherapieByMaladie(m);
	}
}
