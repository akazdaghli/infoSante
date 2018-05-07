package edu.esprit.smartInnov.gui.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import edu.esprit.smartInnov.entites.Experience;
import edu.esprit.smartInnov.entites.Utilisateur;
import edu.esprit.smartInnov.services.ExperienceService;
import edu.esprit.smartInnov.utils.Utilitaire;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class AjouterExperienceController {

	@FXML
	private TextField titre;
	@FXML
	private TextArea detail;
	@FXML
	private ImageView imageView;
	private ExperienceService experienceService;
	private InputStream photo = null;
	private Utilisateur user;
	public void initComponents(Utilisateur userConnected) {
		experienceService = new ExperienceService();
		user = userConnected;
	}
	
	public void joindrePhoto() {
		photo = Utilitaire.importerImage(detail, false);
		if(photo != null) {
			Image img = new Image(photo);
			imageView.setImage(img);
			
		}
	}
	
	public void ajouterExperience() {
		Experience e = new Experience();
		if(titre.getText() != null && !titre.getText().isEmpty()) {
			e.setTitre(titre.getText());
		}else {
			return;
		}
		
		if(detail.getText() != null && !detail.getText().isEmpty()) {
			e.setDetail(detail.getText());
		}else {
			return;
		}
		
		e.setDatePartage(Utilitaire.getSqlDateFromUtilDate(new Date()));
		e.setUtilisateur(user);
		e.setFlagVisible(true);
		e.setNbrVues(0);
		if(photo != null) {
			e.setPhoto(photo);
		}
		experienceService.ajouter(e);
		
	}
	
	public void retour() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("experienceView.fxml"));
		AnchorPane experienceView = (AnchorPane) loader.load();
		ExperienceController experienceController = loader.getController();
		experienceController.initComponents(Main.getUserConnected());
		Main.getMainLayout().setCenter(experienceView);
	}
}
