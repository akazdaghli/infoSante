package edu.esprit.smartInnov.gui.main;

import com.lynden.gmapsfx.GoogleMapView;

import edu.esprit.smartInnov.vues.VProSante;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProSanteDetailsController {

	@FXML
	private Label nom;
	@FXML
	private Label prenom;
	@FXML
	private GoogleMapView map;
	public void initComponents(VProSante vProSante) {
		nom.setText(vProSante.getNom());
		prenom.setText(vProSante.getPrenom());
	}
}
