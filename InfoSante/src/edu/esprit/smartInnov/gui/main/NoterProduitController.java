package edu.esprit.smartInnov.gui.main;

import java.util.Date;
import java.util.logging.Logger;

import org.controlsfx.control.Rating;

import edu.esprit.smartInnov.entites.Avis;
import edu.esprit.smartInnov.entites.Produit;
import edu.esprit.smartInnov.services.AvisService;
import edu.esprit.smartInnov.utils.Utilitaire;
import edu.esprit.smartInnov.vues.VProduit;
import eu.hansolo.enzo.notification.Notification.Notifier;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class NoterProduitController {

	private final static Logger LOGGER = Logger.getLogger(NoterProduitController.class.getName());
	@FXML
	private Button validerBtn;
	@FXML
	private AnchorPane pane;
	Rating rate;
	private VProduit produit;
	private AvisService avisService;
	boolean update = false;
	public void initComponents(VProduit p) {
		avisService = new AvisService();
		produit = p;
		Avis a = avisService.getAvisByProduitAndUser(produit, Main.getUserConnected());
			rate = new Rating();
			rate.setMax(5);
			rate.setMaxWidth(200);
			rate.setMinWidth(200);
			rate.setCursor(Cursor.HAND);
			rate.setLayoutX(65);
			rate.setLayoutY(65);
			rate.setUpdateOnHover(true);
			pane.getChildren().add(rate);
		if(a != null) {
			update = true;
			Label lbl = new Label("Vous avez déjà noté ce produit mais\nvous pouvez toujours changer d'avis");
			pane.getChildren().add(lbl);
			rate.setRating(new Double(a.getRate()));
		}
		
	}
	
	public void valider() {
		if(!update) {
			Avis a = new Avis();
			a.setProduit(produit);
			a.setUtilisateur(Main.getUserConnected());
			a.setRate((int) rate.getRating());
			a.setDateAvis(Utilitaire.getSqlDateFromUtilDate(new Date()));
			avisService.ajouter(a);
		}else {
			avisService.changeAvis(rate.getRating(),produit.getId(), Main.getUserConnected().getId());
		}
		validerBtn.getScene().getWindow().hide();
		Notifier.INSTANCE.notifySuccess("Succès", "Produit noté ");
	}
	
}
