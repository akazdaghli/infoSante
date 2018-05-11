package edu.esprit.smartInnov.gui.main;

import java.util.ArrayList;
import java.util.List;

import org.controlsfx.control.CheckComboBox;

import edu.esprit.smartInnov.entites.Maladie;
import edu.esprit.smartInnov.entites.Medicament;
import edu.esprit.smartInnov.services.MaladieService;
import edu.esprit.smartInnov.services.MedicamentService;
import eu.hansolo.enzo.notification.Notification.Notifier;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AjouterModifierMaladieController {

	@FXML
	private TextField libelleTF;
	@FXML
	private AnchorPane pane;
	
	private MaladieService maladieService;
	private MedicamentService medicamentService;
	private Maladie maladie;
	private List<Medicament> allMedicament;
	private List<Medicament> selectedMedicament;
	public void initComponents(Maladie m) {
		maladieService = new MaladieService();
		medicamentService = new MedicamentService();
		allMedicament = medicamentService.getAll();
		selectedMedicament = new ArrayList<>();
		maladie = m;
		if(m != null) {
			libelleTF.setText(m.getLibelle());
		}
		CheckComboBox<String> check = new CheckComboBox<>();
		check.setLayoutY(65);
		check.setLayoutX(57);
		check.setPrefWidth(149);
		check.setStyle("-fx-background-radius:5em");
		check.setCursor(Cursor.HAND);
		for (Medicament med : allMedicament) {
			check.getItems().add(med.getLibelle());
		}
		check.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
		     public void onChanged(ListChangeListener.Change<? extends String> c) {
		         for (String lib : check.getCheckModel().getCheckedItems()) {
		        	 selectedMedicament.add(medicamentService.getMedicamentByLibelle(lib));
				}
		     }
		 });
		pane.getChildren().add(check);
		
	}
	
	public void valider() {
		Maladie mal = new Maladie();
		if(libelleTF.getText()!= null && !libelleTF.getText().isEmpty()) {
			mal.setLibelle(libelleTF.getText());
		}else {
			Notifier.INSTANCE.notifyError("Erreur", "Veuillez saisir un libellé");
			return;
		}
		if(maladie != null) {
			mal.setId(maladie.getId());
		}else {
			maladieService.ajouter(mal);
		}
	}
}
