package edu.esprit.smartInnov.gui.main;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

import javax.mail.MessagingException;

import edu.esprit.smartInnov.entites.Patient;
import edu.esprit.smartInnov.entites.ProSante;
import edu.esprit.smartInnov.entites.RendezVous;
import edu.esprit.smartInnov.entites.Utilisateur;
import edu.esprit.smartInnov.services.ExperienceService;
import edu.esprit.smartInnov.services.RendezVousService;
import edu.esprit.smartInnov.services.UtilisateurService;
import edu.esprit.smartInnov.utils.EnvoiMailUtil;
import edu.esprit.smartInnov.utils.IConstants;
import edu.esprit.smartInnov.utils.Utilitaire;
import edu.esprit.smartInnov.vues.VProSante;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;

public class PriseRendezVousController {

	private static final Logger LOGGER = Logger.getLogger(PriseRendezVousController.class.getName());
	@FXML
	private Label docteur;
	@FXML
	private Label patient;
	@FXML
	private DatePicker date;
	@FXML
	private ComboBox<Integer> heure;
	@FXML
	private TextArea sujet;
	private ProSante proSante;
	private UtilisateurService service;
	private RendezVousService rendezVousService;
	public void initComponents(VProSante medecin) {
		service = new UtilisateurService();
		rendezVousService = new RendezVousService();
		proSante = (ProSante) service.getUserById(medecin.getIdProSante());
		docteur.setText(medecin.getNom()+" "+medecin.getPrenom());
		patient.setText(Main.getUserConnected().getNom()+" "+Main.getUserConnected().getPrenom());
		heure.getItems().clear();
		for(int i=8;i<=17;i++) {
			heure.getItems().add(i);
		}
	}
	
	public void valider() {
		RendezVous rv = new RendezVous();
		if(date.getValue() != null) {
			LOGGER.info(date.getValue().getDayOfWeek().getValue()+"");
			if(date.getValue().getDayOfWeek().getValue() == IConstants.DaysOfWeek.SUNDAY){
				LOGGER.info("dimache ");
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erreur");
				alert.setHeaderText("Dimache");
				alert.setContentText("Impossible de prendre un rendez-vous le Dimache");
				alert.showAndWait();
				return;
			}else if(date.getValue().getDayOfWeek().getValue() == IConstants.DaysOfWeek.SATURDAY) {
				LOGGER.info("samedi ");
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erreur");
				alert.setHeaderText("Dimache");
				alert.setContentText("Impossible de prendre un rendez-vous le Samedi");
				alert.showAndWait();
				return;
			}else {
				List<RendezVous> rendExist = rendezVousService.getRendezVousByDocteurAndDateAndHeure(proSante, Utilitaire.getDateFromLocalDate(date.getValue()), heure.getValue()+"");
				if(rendExist != null && !rendExist.isEmpty()) {
					LOGGER.info("samedi ");
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information");
					alert.setHeaderText("Occupé");
					alert.setContentText("Le docteur "+proSante.getNom() +" a déjà un rendez-vous à cette date");
					alert.showAndWait();
					return;
				}else {
					rv.setDateRendezVs(Utilitaire.getDateFromLocalDate(date.getValue()));
				}
			}
			
		}else {
			return;
		}
		
		if(heure.getValue() != null) {
			rv.setHeureRendezVs(heure.getValue()+"");
		}else {
			return;
		}
		
		if(sujet.getText() != null && !sujet.getText().isEmpty()) {
			rv.setSujet(sujet.getText());
		}else {
			return;
		}
		
		rv.setMedecin(proSante);
		rv.setPatient((Patient) Main.getUserConnected());
		rv.setFlagValide(false);
		rendezVousService.ajouter(rv);
		try {
			EnvoiMailUtil.envoiMail(proSante.getMail(), "Demande de rendez-vous", "Bonjour,", Main.getUserConnected().getNom()+" "+Main.getUserConnected().getPrenom()+" a demandé de prendre "
			+"prendre un rendez vous avec vous le "+rv.getDateRendezVs().toString()+" à "+rv.getHeureRendezVs() +" Heure "+". \n Veuillez vous connecter à votre compte  pour confirmer ce rendez-vous");
		} catch (MessagingException e) {
			LOGGER.severe(e.getMessage());
		}
		// ajouter notification
		
		sujet.getScene().getWindow().hide();
	}
}
