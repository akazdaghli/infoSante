package edu.esprit.smartInnov.gui.main;

import edu.esprit.smartInnov.vues.VProSante;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProSanteDetailsController {

	@FXML
	private Label nom;
	@FXML
	private Label prenom;
	public void initComponents(VProSante vProSante) {
		nom.setText(vProSante.getNom());
		prenom.setText(vProSante.getPrenom());
	}
}
