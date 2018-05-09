package edu.esprit.smartInnov.gui.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.controlsfx.control.CheckComboBox;

import edu.esprit.smartInnov.entites.Laboratoire;
import edu.esprit.smartInnov.entites.Maladie;
import edu.esprit.smartInnov.entites.Medicament;
import edu.esprit.smartInnov.services.LaboratoireService;
import edu.esprit.smartInnov.services.MaladieService;
import edu.esprit.smartInnov.services.MedicamentService;
import edu.esprit.smartInnov.utils.IConstants;
import eu.hansolo.enzo.notification.Notification.Notifier;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

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
	private AnchorPane pane;
	private StringBuilder noticeStr;
	private LaboratoireService laboratoireService;
	private MedicamentService medicamentService;
	private MaladieService maladieService;
	private List<Maladie> maladies;
	private List<Maladie> selectedMaladies;
	private List<Laboratoire> labos;
	public void initComponents() {
		laboratoireService = new LaboratoireService();
		typeCombo.getItems().clear();
		typeCombo.getItems().add("");
		typeCombo.getItems().addAll(IConstants.typesMedicament);
		applicationCombo.getItems().clear();
		applicationCombo.getItems().add("");
		applicationCombo.getItems().addAll(IConstants.applicationMedicament);
		labos = laboratoireService.findAll();
		laboCombo.getItems().clear();
		laboCombo.getItems().add("");
		for (Laboratoire laboratoire : labos) {
			laboCombo.getItems().add(laboratoire.getLibelle());
		}
		
		medicamentService = new MedicamentService();
		maladieService = new MaladieService();
		selectedMaladies = new ArrayList<>();
		maladies = maladieService.getAll();
		CheckComboBox<String> maladieCheckList = new CheckComboBox<>();
		maladieCheckList.setLayoutY(267);
		maladieCheckList.setLayoutX(86);
		maladieCheckList.setPrefWidth(149);
		maladieCheckList.setPrefHeight(25);
		maladieCheckList.setStyle("-fx-background-radius:5em");
		maladieCheckList.setCursor(Cursor.HAND);
		for (Maladie maladie : maladies) {
			maladieCheckList.getItems().add(maladie.getLibelle());
		}
		maladieCheckList.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
		     public void onChanged(ListChangeListener.Change<? extends String> c) {
		         for (String lib : maladieCheckList.getCheckModel().getCheckedItems()) {
					selectedMaladies.add(maladieService.getMaladieByLibelle(lib));
				}
		     }
		 });
		pane.getChildren().add(maladieCheckList);
	}
	
	public void uploadFileNotice() {
		String line = "";
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Importer Notice");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Toutes les fichiers", "*.*"),
				new FileChooser.ExtensionFilter("TXT", "*.txt"));
		File notice = fileChooser.showOpenDialog(libelleTextField.getScene().getWindow());
		if(notice != null) {
			FileReader fileReader;
			try {
				fileReader = new FileReader(notice);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				
				try {
					line = bufferedReader.readLine();
					noticeStr = new StringBuilder(line);
					line = bufferedReader.readLine();
					while(line != null) {
						noticeStr.append(bufferedReader.readLine());
						line = bufferedReader.readLine();
					}
				} catch (IOException e) {
					Notifier.INSTANCE.notifyError("Erreur", "Erreur lors de la lecture du fichier.");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
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
		if(applicationCombo.getValue() != null && !applicationCombo.getValue().isEmpty()) {
			m.setApplication(applicationCombo.getValue());
		}else {
			Notifier.INSTANCE.notifyError("Erreur", "Veuillez choisir une application.");
			return;
		}
		if(typeCombo.getValue() != null && !typeCombo.getValue().isEmpty()) {
			m.setType(typeCombo.getValue());
		}else {
			Notifier.INSTANCE.notifyError("Erreur", "Veuillez choisir un type.");
			return;
		}
		if(laboCombo.getValue() != null && !laboCombo.getValue().isEmpty()) {
			m.setLaboratoire(laboratoireService.findByLibelle(laboCombo.getValue()));
		}else {
			Notifier.INSTANCE.notifyError("Erreur", "Veuillez choisir une laboratoire.");
			return;
		}
		
		if(selectedMaladies != null && !selectedMaladies.isEmpty()) {
			m.setMaladies(selectedMaladies);
		}else {
			Notifier.INSTANCE.notifyError("Erreur", "Veuillez choisir au moins une maladie.");
			return;
		}
		if(noticeStr != null && !noticeStr.toString().isEmpty()) {
			m.setNotice(noticeStr.toString());
		}
		medicamentService.ajouter(m);
		applicationCombo.getScene().getWindow().hide();
		Notifier.INSTANCE.notifySuccess("Succès", "Médicament ajouté.");
	}
}
