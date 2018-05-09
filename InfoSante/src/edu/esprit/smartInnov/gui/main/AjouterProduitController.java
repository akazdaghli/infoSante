package edu.esprit.smartInnov.gui.main;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;

import edu.esprit.smartInnov.entites.Laboratoire;
import edu.esprit.smartInnov.entites.Produit;
import edu.esprit.smartInnov.services.LaboratoireService;
import edu.esprit.smartInnov.services.ProduitService;
import edu.esprit.smartInnov.utils.IConstants;
import edu.esprit.smartInnov.utils.Utilitaire;
import eu.hansolo.enzo.notification.Notification.Notifier;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class AjouterProduitController {

	LaboratoireService laboratoireService;
	ProduitService produitService;
	List<Laboratoire> labos;
	Alert alert;
	@FXML
	private TextField libelleText;
	@FXML
	private Button importBtn;
	@FXML
	private Label imgName;
	@FXML
	private TextArea modeEmploi;
	@FXML
	private ComboBox<String> categorieCombo;
	@FXML
	private ComboBox<String> laboCombo;
	private InputStream photo;
	public void initComponents() {
		laboratoireService = new LaboratoireService();
		imgName.setText("");
		produitService = new ProduitService();
		labos = laboratoireService.findAll();
		categorieCombo.getItems().clear();
		categorieCombo.getItems().add("");
		categorieCombo.getItems().addAll(IConstants.categorieProduit);
		laboCombo.getItems().clear();
		laboCombo.getItems().add("");
		for(Laboratoire l : labos) {
			laboCombo.getItems().add(l.getLibelle());
		}
	}
	
	public void ajouter() {
		Produit p = new Produit();
		if(categorieCombo.getValue() != null && !categorieCombo.getValue().isEmpty()) {
			p.setCategorie(categorieCombo.getValue());
		}else {
			Notifier.INSTANCE.notifyError("Erreur", "Vous n'etes pas encore inscris, veuillez vous inscrire pour pouvoir se connecter");
//			alert = new Alert(AlertType.ERROR);
//			alert.setTitle("Erreur");
//			alert.setHeaderText("Champs obligatoire");
//			alert.setContentText("Le champ catégorie est obligatoire, veuillez le remplir");
//			alert.showAndWait();
			return;
		}
		if(laboCombo.getValue() != null && !laboCombo.getValue().isEmpty()) {
			p.setLaboratoire(laboratoireService.findByLibelle(laboCombo.getValue()));
		}else {
//			alert = new Alert(AlertType.ERROR);
//			alert.setTitle("Erreur");
//			alert.setHeaderText("Champs obligatoire");
//			alert.setContentText("Le champ laboratoire est obligatoire, veuillez le remplir");
//			alert.showAndWait();
			Notifier.INSTANCE.notifyError("Erreur", "Le champ laboratoire est obligatoire, veuillez le remplir");
			return;
		}
		if(libelleText.getText() != null && !libelleText.getText().isEmpty()) {
			p.setLibelle(libelleText.getText());
		}else {
//			alert = new Alert(AlertType.ERROR);
//			alert.setTitle("Erreur");
//			alert.setHeaderText("Champs obligatoire");
//			alert.setContentText("Le champ libellé est obligatoire, veuillez le remplir");
//			alert.showAndWait();
			Notifier.INSTANCE.notifyError("Erreur", "Le champ libellé est obligatoire, veuillez le remplir");
			return;
		}
		if(modeEmploi.getText() != null && !modeEmploi.getText().isEmpty()) {
			p.setModeEmploi(modeEmploi.getText());
		}else {
//			alert = new Alert(AlertType.ERROR);
//			alert.setTitle("Erreur");
//			alert.setHeaderText("Champs obligatoire");
//			alert.setContentText("Le champ mode d'emploi est obligatoire, veuillez le remplir");
//			alert.showAndWait();
			Notifier.INSTANCE.notifyError("Erreur", "Le champ mode d'emploi est obligatoire, veuillez le remplir");
			return;
		}
		if(photo != null) {
			p.setImage(photo);
		}
		produitService.ajouter(p);
		categorieCombo.getScene().getWindow().hide();
	}
	
	public void importerImage() {
		photo = Utilitaire.importerImage(imgName, true);
	}
	
	
	
}
