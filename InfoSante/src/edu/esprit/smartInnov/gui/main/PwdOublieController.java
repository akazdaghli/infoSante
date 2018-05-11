package edu.esprit.smartInnov.gui.main;

import java.security.NoSuchAlgorithmException;

import javax.mail.MessagingException;

import edu.esprit.smartInnov.entites.Utilisateur;
import edu.esprit.smartInnov.services.UtilisateurService;
import edu.esprit.smartInnov.utils.EnvoiMailUtil;
import edu.esprit.smartInnov.utils.Utilitaire;
import eu.hansolo.enzo.notification.Notification.Notifier;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class PwdOublieController {
	
	@FXML
	private TextField mailTF;
	private UtilisateurService utilisateurService;
	public void valider() {
		utilisateurService = new UtilisateurService();
		if(mailTF.getText()!=null && !mailTF.getText().isEmpty() && Utilitaire.isValidMail(mailTF.getText()) ) {
			if(utilisateurService.isUtilisateurExistByMail(mailTF.getText())) {
			Utilisateur user = utilisateurService.getUtilisateurExistByMail(mailTF.getText());
			String generatedPwd = Utilitaire.generateRandomPassword();
			try {
				EnvoiMailUtil.envoiMail(user.getMail(), "Nouveau mot de passe", "Info santé", "Votre mot passe a été changé. Nouveau mot de passe : "+generatedPwd);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				user.setPwd(Utilitaire.hashMD5Crypt(generatedPwd));
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			utilisateurService.update(user);
			mailTF.getScene().getWindow().hide();
			Notifier.INSTANCE.notifySuccess("Succès", "Mot de passe réinitialisé, veuillez consulter votre boite mails");
			}else {
				Notifier.INSTANCE.notifyError("Erreur", "Le mail saisi n'existe pas");
			}
		}else {
			Notifier.INSTANCE.notifyError("Erreur", "Veuillez saisir votre mail!");
		}
	}
}
