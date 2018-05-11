package edu.esprit.smartInnov.gui.main;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;

import edu.esprit.smartInnov.entites.Local;
import edu.esprit.smartInnov.services.LocalService;
import edu.esprit.smartInnov.services.UtilisateurService;
import edu.esprit.smartInnov.utils.EnvoiMailUtil;
import edu.esprit.smartInnov.utils.Utilitaire;
import edu.esprit.smartInnov.vues.VProSante;
import eu.hansolo.enzo.notification.Notification.Notifier;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LatLongModalController {

	private static final Logger LOGGER = Logger.getLogger(LatLongModalController.class.getName());
	
	@FXML
	private TextField latTF;
	@FXML
	private TextField longTF;
	
	private LocalService localService;
	private UtilisateurService utilisateurService;
	private Local local;
	private VProSante proSante;
	public void initComponents(VProSante v) {
		localService = new LocalService();
		utilisateurService = new UtilisateurService();
		proSante = v;
		local = localService.getLocalByVProSante(v);
	}
	
	public void valider() {
		if(latTF.getText() != null && !latTF.getText().isEmpty() && Utilitaire.isNumeric(longTF.getText()) && Utilitaire.isNumeric(latTF.getText()) && longTF.getText() != null && !longTF.getText().isEmpty()) {
			local.setLongitude(new Double(longTF.getText()));
			local.setLatitude(new Double(latTF.getText()));
			localService.update(local);
			utilisateurService.confirmerProSante(proSante);
			Notifier.INSTANCE.notifySuccess("Succ�s", "Inscription valid�e");
			latTF.getScene().getWindow().hide();
			try {
				EnvoiMailUtil.envoiMail(proSante.getMail(), "InfoSant�", "Inscription confirm�e", "Votre inscription a �t� valid�e par nos administrateurs, vous pouvez vous-connectez � votre compte");
			} catch (MessagingException e) {
				LOGGER.log(Level.SEVERE, e.getMessage());
			}
		}else {
			Notifier.INSTANCE.notifyError("Erreur", "Veuillez v�rifier les valeurs saisies");
		}
	}
}
