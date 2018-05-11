package edu.esprit.smartInnov.gui.main;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import edu.esprit.smartInnov.entites.Utilisateur;
import edu.esprit.smartInnov.services.UtilisateurService;
import edu.esprit.smartInnov.utils.Utilitaire;
import eu.hansolo.enzo.notification.Notification.Notifier;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ProfileController {

	@FXML
	public TextField nomTF;
	@FXML
	public TextField prenomTF;
	@FXML
	public TextField mailTF;
	@FXML
	public TextField loginTF;
	@FXML
	public PasswordField actuelPwTF;
	@FXML
	public PasswordField newPwTF;
	@FXML
	public PasswordField confirmNewPwTF;

	private UtilisateurService utilisateurService;
	private Utilisateur user;

	public void initComponents() {
		utilisateurService = new UtilisateurService();
		user = Main.getUserConnected();
		if (user != null) {
			nomTF.setText(user.getNom());
			prenomTF.setText(user.getPrenom());
			mailTF.setText(user.getMail());
			loginTF.setText(user.getLogin());
		}
	}

	public void clearAll() {
		nomTF.clear();
		prenomTF.clear();
		mailTF.clear();
		loginTF.clear();
		actuelPwTF.clear();
		newPwTF.clear();
		confirmNewPwTF.clear();
	}

	public void valider() throws NoSuchAlgorithmException {
		
		if (nomTF.getText() != null && !nomTF.getText().isEmpty() && Utilitaire.isValidName(nomTF.getText())) {
			user.setNom(nomTF.getText());
		} else {
			Notifier.INSTANCE.notifyError("Erreur", "Veuillez saisir un nom valide");
			return;
		}
		
		if (prenomTF.getText() != null && !prenomTF.getText().isEmpty() && Utilitaire.isValidName(prenomTF.getText())) {
			user.setPrenom(prenomTF.getText());
		} else {
			Notifier.INSTANCE.notifyError("Erreur", "Veuillez saisir un prenom valide");
			return;
		}
		
		if (mailTF.getText() != null && !mailTF.getText().isEmpty() && Utilitaire.isValidMail(mailTF.getText())) {
			if(!mailTF.getText().equals(user.getMail()) && utilisateurService.isUtilisateurExistByMail(mailTF.getText())) {
				Notifier.INSTANCE.notifyError("Erreur", "Adresse mail utilisée par un autre utilisateur");
			}else {
				user.setMail(mailTF.getText());
			}
		} else {
			Notifier.INSTANCE.notifyError("Erreur", "Veuillez saisir un mail valide");
			return;
		}
		
		if (loginTF.getText() != null && !loginTF.getText().isEmpty()) {
			if(!loginTF.getText().equals(user.getLogin()) && utilisateurService.isUtilisateurExistByLogin(loginTF.getText())) {
				Notifier.INSTANCE.notifyError("Erreur", "Login utilisé par un autre utilisateur");
				return;
			}else {
				user.setLogin(loginTF.getText());
			}
		} else {
			Notifier.INSTANCE.notifyError("Erreur", "Veuillez saisir un login valide");
			return;
		}
		
		if(actuelPwTF.getText() != null && !actuelPwTF.getText().isEmpty() && newPwTF.getText() != null && !newPwTF.getText().isEmpty() && confirmNewPwTF.getText() != null && !confirmNewPwTF.getText().isEmpty()) {
			if(Utilitaire.verifyHashedPassword(user.getPwd(), Utilitaire.hashMD5Crypt(actuelPwTF.getText()))) {
				if(newPwTF.getText().equals(confirmNewPwTF.getText())) {
					user.setPwd(Utilitaire.hashMD5Crypt(newPwTF.getText()));
				}else {
					Notifier.INSTANCE.notifyError("Erreur", "Les nouveaux mots de passe saisis ne concordent pas");
					return;
				}
			}else {
				Notifier.INSTANCE.notifyError("Erreur", "Mot de passe actuel erroné");
				return;
			}
		}
		
		utilisateurService.update(user);
		Notifier.INSTANCE.notifySuccess("Succès", "Modifications effectuées");
		try {
			Main.showAcceuilView(user);
		} catch (IOException e) {
			
		}
		
	}
}
