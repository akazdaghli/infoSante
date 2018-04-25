package edu.esprit.smartInnov.gui.main;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;

import edu.esprit.smartInnov.entites.ProSante;
import edu.esprit.smartInnov.entites.Utilisateur;
import edu.esprit.smartInnov.services.UtilisateurService;
import edu.esprit.smartInnov.utils.EnvoiMailUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class SendMailController {

	private static final Logger LOGGER = Logger.getLogger(SendMailController.class.getName());
	@FXML
	private RadioButton destPro;
	@FXML
	private RadioButton destPar;
	@FXML
	private RadioButton destAll;
	@FXML
	private TextField objetTextField;
	@FXML
	private TextArea msgTextArea;
	@FXML
	private Button sendbtn;
	private UtilisateurService utilisateurService;
	private List<Utilisateur> destinataires;
	public void initComponents() {
		final ToggleGroup group = new ToggleGroup();
		destPro.setToggleGroup(group);
		destPar.setToggleGroup(group);
		destAll.setToggleGroup(group);
		destAll.setSelected(true);
		utilisateurService = new UtilisateurService();
	}
	
	public void sendMail() {
		if(destPro.isSelected()) {
			destinataires = utilisateurService.getMedecins();
		}
		if(destPar.isSelected()) {
			destinataires = utilisateurService.getPatients();
		}
		if(destAll.isSelected()) {
			destinataires = utilisateurService.getAllUsers();
		}
		
		if(objetTextField != null && objetTextField.getText().isEmpty() && !msgTextArea.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Attention");
			alert.setHeaderText("Vous n'avez pas saisi l'objet de votre mail");
			alert.setContentText("Voule-vous envoyer le mail quand même?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
			    for(Utilisateur user : destinataires) {
			    	try {
						EnvoiMailUtil.envoiMail(user.getMail(), "", "", msgTextArea.getText());
					} catch (MessagingException e) {
						LOGGER.log(Level.SEVERE, e.getMessage());
					}
			    }
			} else {
				alert.close();
			}
		}else if(!objetTextField.getText().isEmpty() && !msgTextArea.getText().isEmpty()) {
			  for(Utilisateur user : destinataires) {
			    	try {
						EnvoiMailUtil.envoiMail(user.getMail(), objetTextField.getText(), "", msgTextArea.getText());
					} catch (MessagingException e) {
						LOGGER.log(Level.SEVERE, e.getMessage());
					}
			    }
		}else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Attention");
			alert.setHeaderText("Tous les champs sont obligatoires");
			alert.setContentText("Veuillez saisir un object et un message pour votre mail!");
			alert.showAndWait();
		}
	}
}
