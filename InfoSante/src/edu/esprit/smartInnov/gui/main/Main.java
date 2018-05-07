package edu.esprit.smartInnov.gui.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.esprit.smartInnov.entites.Specialite;
import edu.esprit.smartInnov.entites.Utilisateur;
import edu.esprit.smartInnov.scheduler.CronTrigger;
import edu.esprit.smartInnov.services.SpecialiteService;
import edu.esprit.smartInnov.utils.IConstants;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

	private static Stage primaryStage;
	private static BorderPane mainLayout;
	private static BorderPane login;
	private static Utilisateur userConnected;
//	private InscriptionController inscriptionController;
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("InfoSanté");
		showLoginView();
		CronTrigger.run();
		
	}
	
	public static void showLoginView() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("login.fxml"));
		login = loader.load();
		InscriptionController inscriptionController = loader.getController();
		inscriptionController.initComponents();
		Scene scene = new Scene(login);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	
	
	public static void showAcceuilView(Utilisateur user) throws IOException {
		userConnected = user;
		FXMLLoader loader = new FXMLLoader();
		switch (user.getProfil()) {
		case IConstants.Profils.PATIENT:
			loader.setLocation(Main.class.getResource("acceuilV2.fxml"));
			break;
		case IConstants.Profils.ADMINISTRATUER:
			loader.setLocation(Main.class.getResource("acceuilAdmin.fxml"));
			break;
		case IConstants.Profils.MEDECIN:
			loader.setLocation(Main.class.getResource("acceuilProSante.fxml"));
			break;
		}
		mainLayout = loader.load();
		putAcceuilImg();
		AcceuilController acceuilController = loader.getController();
		acceuilController.initComponents(user);
		Scene scene = new Scene(mainLayout);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void putAcceuilImg() throws IOException {
		FXMLLoader loader2 = new FXMLLoader();
		loader2.setLocation(Main.class.getResource("acceuilImg.fxml"));
		AnchorPane proSanteView = (AnchorPane) loader2.load();
		getMainLayout().setCenter(proSanteView);
	}
	
	public static void main(String[] args) throws Exception {
		launch(args);
		
	}

	public static BorderPane getLogin() {
		return login;
	}

	public static void setLogin(BorderPane login) {
		Main.login = login;
	}

	public static BorderPane getMainLayout() {
		return mainLayout;
	}

	public static void setMainLayout(BorderPane mainLayout) {
		Main.mainLayout = mainLayout;
	}

	public static Utilisateur getUserConnected() {
		return userConnected;
	}

	public static void setUserConnected(Utilisateur userConnected) {
		Main.userConnected = userConnected;
	}
}
