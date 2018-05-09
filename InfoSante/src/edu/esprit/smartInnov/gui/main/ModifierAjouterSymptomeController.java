package edu.esprit.smartInnov.gui.main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.controlsfx.control.CheckComboBox;

import edu.esprit.smartInnov.entites.Maladie;
import edu.esprit.smartInnov.entites.Symptome;
import edu.esprit.smartInnov.services.MaladieService;
import edu.esprit.smartInnov.services.SymptomeService;
import eu.hansolo.enzo.notification.Notification.Notifier;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class ModifierAjouterSymptomeController {

	@FXML
	private TextField libelleField;
	@FXML
	private AnchorPane pane;
	
	private List<Maladie> allMaladies;
	private List<Maladie> selectedMaladies;
	private boolean add;
	private MaladieService maladieService;
	private SymptomeService symptomeService;
	private Symptome symptome;
	
	public void initComponents(Symptome s, boolean add) {
		selectedMaladies = new ArrayList<>();
		symptomeService = new SymptomeService();
		this.add = add;
		symptome =s;
		maladieService = new MaladieService();
		allMaladies = maladieService.getAll();
		if(!add && s != null) {
			libelleField.setText(s.getDesignation());
		}
		CheckComboBox<String> check = new CheckComboBox<>();
		check.setLayoutY(110);
		check.setLayoutX(92);
		check.setPrefWidth(149);
		check.setStyle("-fx-background-radius:5em");
		check.setCursor(Cursor.HAND);
		for (Maladie maladie : allMaladies) {
			check.getItems().add(maladie.getLibelle());
		}
		if(s!=null) {
			for (Maladie maladie : s.getMaladies()) {
				check.getCheckModel().check(maladie.getLibelle());
			}
		}
		check.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
		     public void onChanged(ListChangeListener.Change<? extends String> c) {
		         for (String lib : check.getCheckModel().getCheckedItems()) {
					selectedMaladies.add(maladieService.getMaladieByLibelle(lib));
				}
		         System.out.println(selectedMaladies);
		     }
		 });
		pane.getChildren().add(check);
	}
	
	public void valider() {
		Symptome s = new  Symptome();
		if(libelleField.getText() != null && !libelleField.getText().isEmpty()) {
			s.setDesignation(libelleField.getText());
		}else {
			Notifier.INSTANCE.notifyError("Erreur", "Veuillez saisir un libellé");
			return;
		}
		
		if(selectedMaladies != null && !selectedMaladies.isEmpty()) {
			s.setMaladies(selectedMaladies);
		}else {
			Notifier.INSTANCE.notifyError("Erreur", "Veuillez choisir au moins une maladie");
			return;
		}
		
		if(add) {
			symptomeService.ajouter(s);
			Notifier.INSTANCE.notifySuccess("Succès", "Symptome ajoutée");
		}else {
			s.setId(symptome.getId());
			symptomeService.update(s);
			Notifier.INSTANCE.notifySuccess("Succès", "Symptome modifiée");
		}
		
		libelleField.getScene().getWindow().hide();
	}
}
