package edu.esprit.smartInnov.gui.main;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.imageio.ImageIO;

import edu.esprit.smartInnov.entites.Experience;
import edu.esprit.smartInnov.entites.Utilisateur;
import edu.esprit.smartInnov.services.ExperienceService;
import edu.esprit.smartInnov.utils.Utilitaire;
import eu.hansolo.enzo.notification.Notification.Notifier;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

public class AjouterExperienceController {

	@FXML
	private TextField titre;
	@FXML
	private TextArea detail;
	@FXML
	private ImageView imageView;
	private ExperienceService experienceService;
	private String photo = null;
	private Utilisateur user;
	public void initComponents(Utilisateur userConnected) {
		experienceService = new ExperienceService();
		user = userConnected;
	}
	
	public void joindrePhoto() throws IOException {
//		photo = Utilitaire.importerImage(detail, false);
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Importer image");
		Utilitaire.setExtFiltersToImg(fileChooser);
		File img = fileChooser.showOpenDialog(titre.getScene().getWindow());
		photo = img.getAbsolutePath();
		if(photo != null) {
			BufferedImage bufferedImage = ImageIO.read(img);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "png", baos);
			Image i = new Image(new ByteArrayInputStream(baos.toByteArray()));
			imageView.setImage(i);
		}
	}
	
	public void ajouterExperience() {
		Experience e = new Experience();
		if(titre.getText() != null && !titre.getText().isEmpty()) {
			e.setTitre(titre.getText());
		}else {
			Notifier.INSTANCE.notifyError("Erreur", "Veuillez saisir un titre.");
			return;
		}
		
		if(detail.getText() != null && !detail.getText().isEmpty()) {
			e.setDetail(detail.getText());
		}else {
			Notifier.INSTANCE.notifyError("Erreur", "Veuillez saisir les détail de votre expérience.");
			return;
		}
		
		e.setDatePartage(Utilitaire.getSqlDateFromUtilDate(new Date()));
		e.setUtilisateur(user);
		e.setFlagVisible(true);
		e.setNbrVues(0);
		if(photo != null) {
			e.setPhoto(photo);
		}
		try {
			experienceService.ajouter(e);
			Notifier.INSTANCE.notifySuccess("Succès", "Expérience partagée.");
		} catch (Exception e1) {
			Notifier.INSTANCE.notifyError("Erreur", "Une Erreur est survenue.");
		}
		
		
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
