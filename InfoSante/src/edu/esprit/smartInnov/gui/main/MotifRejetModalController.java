package edu.esprit.smartInnov.gui.main;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;

import edu.esprit.smartInnov.entites.Utilisateur;
import edu.esprit.smartInnov.utils.EnvoiMailUtil;
import edu.esprit.smartInnov.vues.VProSante;
import eu.hansolo.enzo.notification.Notification.Notifier;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class MotifRejetModalController {

	@FXML
	private RadioButton motif1;
	@FXML
	private RadioButton motif2;
	@FXML
	private RadioButton motif3;
	@FXML
	private Button valider;
	@FXML
	private Button annuler;
	private VProSante user;
	private static final Logger LOGGER = Logger.getLogger(MotifRejetModalController.class.getName());
	public void initComponents(VProSante user) {
		this.user = user;
		final ToggleGroup group = new ToggleGroup();
		motif1.setToggleGroup(group);
		motif2.setToggleGroup(group);
		motif2.setToggleGroup(group);
		motif1.setSelected(true);
	}
	
	public void validerBtn() {
		String motif;
		String msg =" .Veuillez vérifier votre dossier avant de le renvoyer pour valider votre inscription";
		if(motif1.isSelected()) {
			motif = "à cause de la non lisibilité des  documents que vous avez envoyé";
		}else if(motif2.isSelected()) {
			motif = " à cause de la non certification des documents que vous avez envoyé";
		}else {
			motif =" à cause du dossier incomplet que vous avez envoyé";
		}
		try {
			EnvoiMailUtil.envoiMail(user.getMail(), "Info Santé", "Inscription rejetée", "On vous informe que votre inscription n'a pas été validée par nos administrateurs"+motif+msg);
		} catch (MessagingException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		Notifier.INSTANCE.notifySuccess("Succès", "Inscription rejetée.");
		motif2.getScene().getWindow().hide();
	}
	
	public void annulerbtn() {
		motif2.getScene().getWindow().hide();
	}
}
