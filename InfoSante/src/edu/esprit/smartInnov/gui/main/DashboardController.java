package edu.esprit.smartInnov.gui.main;

import edu.esprit.smartInnov.services.ExperienceService;
import edu.esprit.smartInnov.services.UtilisateurService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {

	private static final String NEWUSERSLABEL = " Nouvels utilisateurs";
	private static final String EXPERERIENCELABEL = " Nouvelles expériences partagées";
	private static final String INSCRIENATTENTE = " Inscriptions en attente";
	@FXML
	private Label newUsersLabel;
	@FXML
	private Label experiencePartageesLabel;
	@FXML
	private Label usersQueueLabel;
	
	UtilisateurService utilisateurService;
	ExperienceService experienceService;
	public void initComponents() {
		utilisateurService = new UtilisateurService();
		experienceService = new ExperienceService();
		int nbrNewUsers = utilisateurService.getNbrNewUtilisateurs();
		int nbrExp = experienceService.getNbrExperienceLastWeek();
		int inscriAttente = utilisateurService.getNbrInscriEnAttente();
		if(nbrNewUsers != -1 && nbrNewUsers != 0) {
			newUsersLabel.setText(nbrNewUsers + NEWUSERSLABEL);
		}else {
			newUsersLabel.setText("Aucun nouvels utilisateurs ");
		}
		experiencePartageesLabel.setText(nbrExp + EXPERERIENCELABEL);
		usersQueueLabel.setText(inscriAttente + INSCRIENATTENTE);
	}
}
