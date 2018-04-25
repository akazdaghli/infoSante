package edu.esprit.smartInnov.gui.main;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.esprit.smartInnov.entites.Utilisateur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class AcceuilController {

	@FXML
	private Label userConnectedLabel;
	@FXML
	private Label proSanteLabel;
	@FXML
	private Label medicamentLabel;
	@FXML
	private Label consultationEnLigneLabel;
	@FXML
	private Label dashboardLabel;
	@FXML
	private Label phytoLabel;
	@FXML
	private Label sendMailLabel;
	@FXML
	private Label produitLabel;
	@FXML
	private Label acceuilLabel;
	@FXML
	private Label activeViewLabel;
	@FXML
	private ImageView logoutImg;
	@FXML
	private ImageView userConnectedPhoto;
	@FXML
	private static BorderPane pane;
	@FXML
	private AnchorPane proSanteMenuItem;
	@FXML
	private AnchorPane medicamentMenuItem;
	@FXML
	private AnchorPane consultationEnLigneMenuItem;
	@FXML
	private AnchorPane acceuilMenuItem;
	@FXML
	private AnchorPane dashboardMenuItem;
	@FXML
	private AnchorPane sendMailMenuItem;
	@FXML
	private AnchorPane produitMenuItem;
	@FXML
	private AnchorPane phytoMenuItem;
	@FXML
	private Circle imgShape;

	private Utilisateur userConnected;
	private static final Logger LOGGER = Logger.getLogger(AcceuilController.class.getName());

	public void initComponents(Utilisateur userConnected) {
		this.userConnected = userConnected;
		userConnectedLabel.setText(userConnected.getPrenom() + " " + userConnected.getNom());
		if(userConnected.getPhoto() != null) {
			InputStream img = userConnected.getPhoto();
			Image userPhoto = new Image(img);
			imgShape.setFill(new ImagePattern(userPhoto));
//			userConnectedPhoto.setImage(userPhoto);
		}
	}

	public void showProSanteView() throws IOException {
		activeViewLabel.setText("Professionnels de santé");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("proSanteView.fxml"));
		AnchorPane proSanteView = (AnchorPane) loader.load();
		ProSanteController proSanteController = loader.getController();
		proSanteController.initComponents();
		Main.getMainLayout().setCenter(proSanteView);
		resetBackgroundColorMenuItems();
		setMenuItemStyle(proSanteMenuItem, proSanteLabel);
	}

	public void showMedicamentsView() throws IOException {
		activeViewLabel.setText("Médicaments");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("medicamentsView.fxml"));
		AnchorPane medicamentView = (AnchorPane) loader.load();
		MedicamentController medicamentController = loader.getController();
		medicamentController.initComponents(userConnected);
		Main.getMainLayout().setCenter(medicamentView);
		resetBackgroundColorMenuItems();
		setMenuItemStyle(medicamentMenuItem, medicamentLabel);
	}

	public void showPhytotherapieView() throws IOException {
		activeViewLabel.setText("Phytothérapie");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("phytotherapieView.fxml"));
		AnchorPane phytoView = (AnchorPane) loader.load();
		PhytotherapieController phytoController = loader.getController();
		phytoController.initComponents(userConnected);
		Main.getMainLayout().setCenter(phytoView);
		resetBackgroundColorMenuItems();
		setMenuItemStyle(phytoMenuItem, phytoLabel);
	}

	public void showConsultationEnLigneView() throws IOException {
		activeViewLabel.setText("Consultation en ligne");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("consultationEnLigneView.fxml"));
		AnchorPane consultationEnLigneView = (AnchorPane) loader.load();
		ConsultationEnLigneController consultationEnLigneController = loader.getController();
		consultationEnLigneController.initComponents();
		Main.getMainLayout().setCenter(consultationEnLigneView);
		resetBackgroundColorMenuItems();
		setMenuItemStyle(consultationEnLigneMenuItem, consultationEnLigneLabel);
	}

	public void showProduitView() throws IOException {
		activeViewLabel.setText("Produits");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("produitView.fxml"));
		AnchorPane produitView = (AnchorPane) loader.load();
		ProduitController produitController = loader.getController();
		produitController.initComponents(userConnected);
		Main.getMainLayout().setCenter(produitView);
		resetBackgroundColorMenuItems();
		setMenuItemStyle(produitMenuItem, produitLabel);
	}

	public void showAcceuilView() throws IOException {
		activeViewLabel.setText("Acceuil");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("acceuilImg.fxml"));
		AnchorPane proSanteView = (AnchorPane) loader.load();
		Main.getMainLayout().setCenter(proSanteView);
		resetBackgroundColorMenuItems();
		setMenuItemStyle(acceuilMenuItem, acceuilLabel);
	}

	public void showDashboardView() throws IOException {
		activeViewLabel.setText("Dashboard");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("dashboardView.fxml"));
		AnchorPane dashboardView = (AnchorPane) loader.load();
		DashboardController dashboardController = loader.getController();
		dashboardController.initComponents();
		Main.getMainLayout().setCenter(dashboardView);
		resetBackgroundColorMenuItems();
		setMenuItemStyle(dashboardMenuItem, dashboardLabel);
	}

	public void showSendMailView() throws IOException {
		activeViewLabel.setText("Envoyer un mail");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("sendMail.fxml"));
		AnchorPane sendMail = (AnchorPane) loader.load();
		SendMailController sendMailController = loader.getController();
		sendMailController.initComponents();
		Main.getMainLayout().setCenter(sendMail);
		resetBackgroundColorMenuItems();
		setMenuItemStyle(sendMailMenuItem, sendMailLabel);
	}

	public void resetBackgroundColorMenuItems() {
		String defaultStyle = "-fx-cursor: hand;-fx-background-color : transparent";
		if (proSanteMenuItem != null) {
			proSanteLabel.setStyle(defaultStyle);
			proSanteMenuItem.setStyle(defaultStyle);
		}
		if (medicamentMenuItem != null) {
			medicamentLabel.setStyle(defaultStyle);
			medicamentMenuItem.setStyle(defaultStyle);
		}
		if (acceuilMenuItem != null) {
			acceuilMenuItem.setStyle(defaultStyle);
			acceuilLabel.setStyle(defaultStyle);
		}
		if (consultationEnLigneMenuItem != null) {
			consultationEnLigneLabel.setStyle(defaultStyle);
			consultationEnLigneMenuItem.setStyle(defaultStyle);
		}
		if (sendMailMenuItem != null) {
			sendMailLabel.setStyle(defaultStyle);
			sendMailMenuItem.setStyle(defaultStyle);
		}
		if (dashboardMenuItem != null) {
			dashboardLabel.setStyle(defaultStyle);
			dashboardMenuItem.setStyle(defaultStyle);
		}
		if (produitMenuItem != null) {
			produitLabel.setStyle(defaultStyle);
			produitMenuItem.setStyle(defaultStyle);
		}
		if (phytoMenuItem != null) {
			phytoLabel.setStyle(defaultStyle);
			phytoMenuItem.setStyle(defaultStyle);
		}
	}

	public void setMenuItemStyle(AnchorPane pane, Label lbl) {
		String selectedStyle = "-fx-background-color: #00A2D3;-fx-cursor: hand;";
		pane.setStyle(selectedStyle);
		lbl.setStyle(selectedStyle);
	}

	public void logout() throws IOException {
		Main.showLoginView();
	}

	public Utilisateur getUserConnected() {
		return userConnected;
	}

	public void setUserConnected(Utilisateur userConnected) {
		this.userConnected = userConnected;
	}

	public Label getUserConnectedLabel() {
		return userConnectedLabel;
	}

	public void setUserConnectedLabel(Label userConnectedLabel) {
		this.userConnectedLabel = userConnectedLabel;
	}

	public ImageView getLogoutImg() {
		return logoutImg;
	}

	public void setLogoutImg(ImageView logoutImg) {
		this.logoutImg = logoutImg;
	}

	public static BorderPane getPane() {
		return pane;
	}

	public static void setPane(BorderPane pane) {
		AcceuilController.pane = pane;
	}

}
