package edu.esprit.smartInnov.gui.main;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import edu.esprit.smartInnov.entites.Commentaire;
import edu.esprit.smartInnov.services.ExperienceService;
import edu.esprit.smartInnov.utils.Utilitaire;
import edu.esprit.smartInnov.vues.VExperience;
import eu.hansolo.enzo.notification.Notification.Notifier;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ExperienceDetailController {

	private static final Logger LOGGER = Logger.getLogger(ExperienceDetailController.class.getName());
	@FXML
	private Label titre;
	@FXML
	private Label datePartage;
	@FXML
	private Label user;
	@FXML
	private TextFlow detail;
	@FXML
	private ImageView image;
	@FXML
	private Label nbrVues;
	@FXML
	private Label nbrComms;
	@FXML
	private ScrollPane commPane;
	
	private int nbrComm;
	private List<Commentaire> comms;
	private VExperience experience;
	private ExperienceService experienceService;
	public void initComponents(VExperience experience) {
		experienceService = new ExperienceService();
		this.experience = experience;
		image.imageProperty().set(null);
		nbrComm = new Integer(experience.getNbrComm());
		fillExperienceDetails();
	}
	
	public void fillExperienceDetails() {
		titre.setText(experience.getTitre());
		datePartage.setText(experience.getDate());
		user.setText(experience.getUser());
		
		nbrComms.setText(nbrComm +" commentaires");
		nbrVues.setText(experience.getNbrVues()+" vues");
		Text t = new Text(experience.getDetail());
		detail.getChildren().add(t);
		if(experience.getPhoto() != null) {
			Image img = new Image(experience.getPhoto());
			image.imageProperty().set(img);
		}
		comms = experienceService.getListCommByExperience(experience);
		AnchorPane p = new AnchorPane();
		double y = 0;
		for (Commentaire c : comms) {
			String nom = c.getUser().getNom()+" "+c.getUser().getPrenom();
			String comm = " : "+ c.getDetail()+"\n";
			Label nomLbl = new Label(nom);
			Label commLbl = new Label(comm);
			nomLbl.setLayoutY(y);
			nomLbl.setUnderline(true);
			nomLbl.setStyle("-fx-font-weight : bold;");
			commLbl.setLayoutY(y);
			commLbl.setLayoutX(100);
			y=y+20;
			p.getChildren().add(nomLbl);
			p.getChildren().add(commLbl);
		}
		TextField tf = new TextField();
		tf.setPromptText("Ajouter un commentaire");
		tf.setLayoutY(y);
		tf.setPrefWidth(300);
		Button btn = new Button("Ajouter");
		btn.setCursor(Cursor.HAND);
		btn.setLayoutY(y);
		btn.setLayoutX(320);
		String btnStyle= "-fx-background-color:#00A2D3;\r\n" + 
				"-fx-background-radius: 5em; \r\n" + 
				"-fx-text-fill: white; \r\n" + 
				"-fx-cursor: hand;";
		btn.setStyle(btnStyle);
		btn.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				addCommentaire(tf.getText());
				tf.clear();
			}
		});
		p.getChildren().add(tf);
		p.getChildren().add(btn);
		commPane.setContent(p);
		
	}
	
	public void addCommentaire(String detail) {
		if(detail != null && !detail.isEmpty()) {
			Commentaire c = new Commentaire();
			c.setDateCommentaire(Utilitaire.getSqlDateFromUtilDate(new Date()));
			c.setExperience(experienceService.getExperienceById(experience.getId()));
			c.setUser(Main.getUserConnected());
			c.setDetail(detail);
			this.nbrComm++;
			experienceService.ajouterCommentaire(c);
			Notifier.INSTANCE.notifySuccess("Succès", "Commentaire ajoutée");
		}else {
			Notifier.INSTANCE.notifyError("Erreur", "Vous ne pouvez pas ajouter un commentaire vide");
		}
	}
	
	public void backButton() throws IOException {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("experienceView.fxml"));
			AnchorPane experienceView = (AnchorPane) loader.load();
			ExperienceController experienceController = loader.getController();
			experienceController.initComponents(Main.getUserConnected());
			Main.getMainLayout().setCenter(experienceView);
	}
}
