package edu.esprit.smartInnov.gui.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.esprit.smartInnov.entites.Maladie;
import edu.esprit.smartInnov.entites.Medicament;
import edu.esprit.smartInnov.services.LaboratoireService;
import edu.esprit.smartInnov.services.MaladieService;
import edu.esprit.smartInnov.services.MedicamentService;
import eu.hansolo.enzo.notification.Notification.Notifier;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;

public class AjouterMedicamentController {

	@FXML
	private ComboBox<String> applicationCombo;
	@FXML
	private ComboBox<String> typeCombo;
	@FXML
	private ComboBox<String> laboCombo;
	@FXML
	private TextField libelleTextField;
	@FXML
	private MenuButton choices;
	private LaboratoireService laboratoireService;
	private MedicamentService medicamentService;
	private MaladieService maladieService;
	private List<Maladie> maladies;
	final ListView<String> selectedmaladies = new ListView<>();
	final List<CheckMenuItem> items = Arrays.asList(new CheckMenuItem(""));
	public void initComponents() {
		laboratoireService = new LaboratoireService();
		medicamentService = new MedicamentService();
		maladieService = new MaladieService();
		maladies = maladieService.getAll();
		for(Maladie m : maladies) {
			items.add(new CheckMenuItem(m.getLibelle()));
		}
		choices.getItems().addAll(items);
		for (final CheckMenuItem item : items) {
	         item.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
	            if (newValue) {
	            	selectedmaladies.getItems().add(item.getText());
	            } else {
	            	selectedmaladies.getItems().remove(item.getText());
	            }
	         });
	      }
	}
	
	public void ajouter() {
		Medicament m = new Medicament();
		if(libelleTextField.getText() != null && !libelleTextField.getText().isEmpty()) {
			m.setLibelle(libelleTextField.getText());
		}else {
			Notifier.INSTANCE.notifyError("Erreur", "Veuillez saisir un libellé.");
			return;
		}
		m.setApplication(applicationCombo.getValue());
		m.setType(typeCombo.getValue());
		m.setLaboratoire(laboratoireService.findByLibelle(laboCombo.getValue()));
		
	}
}
