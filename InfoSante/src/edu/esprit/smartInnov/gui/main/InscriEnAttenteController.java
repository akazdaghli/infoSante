package edu.esprit.smartInnov.gui.main;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;

import edu.esprit.smartInnov.entites.Utilisateur;
import edu.esprit.smartInnov.services.UtilisateurService;
import edu.esprit.smartInnov.utils.EnvoiMailUtil;
import edu.esprit.smartInnov.utils.Utilitaire;
import edu.esprit.smartInnov.vues.VMedicament;
import edu.esprit.smartInnov.vues.VProSante;
import eu.hansolo.enzo.notification.Notification.Notifier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class InscriEnAttenteController {

	@FXML
	private TableView<VProSante> listProSante;
	@FXML
	private TableColumn<VProSante, String> nomCol;
	@FXML
	private TableColumn<VProSante, String> prenomCol;
	@FXML
	private TableColumn<VProSante, String> numTelCol;
	@FXML
	private TableColumn<VProSante, String> specialiteCol;
	@FXML
	private TableColumn<VProSante, String> adresseCol;
	@FXML
	private TableColumn<VProSante, String> accepterCol;
	@FXML
	private TableColumn<VProSante, String> rejectCol;
	
	private List<VProSante> users;
	private UtilisateurService utilisateurService;
	private static final Logger LOGGER = Logger.getLogger(InscriEnAttenteController.class.getName());
	public void initComponents() {
		utilisateurService = new UtilisateurService();
		users = utilisateurService.getListProSanteEnAttente();
		fillTableView(users);
	}
	
	public void fillTableView(List<VProSante> dataList) {
		ObservableList<VProSante> data = FXCollections.observableArrayList(dataList);
		nomCol.setCellValueFactory( new PropertyValueFactory<VProSante,String>("nom"));
		prenomCol.setCellValueFactory( new PropertyValueFactory<VProSante,String>("prenom"));
		numTelCol.setCellValueFactory( new PropertyValueFactory<VProSante,String>("numTel"));
		specialiteCol.setCellValueFactory( new PropertyValueFactory<VProSante,String>("libelle"));
		adresseCol.setCellValueFactory( new PropertyValueFactory<VProSante,String>("adresse"));
		accepterCol.setCellValueFactory(new PropertyValueFactory<VProSante,String>("action"));
//		rejetCol.setCellValueFactory(new PropertyValueFactory<VMedicament,String>("rejet"));
		
		Callback<TableColumn<VProSante, String>, TableCell<VProSante, String>> cellFactory =
				new Callback<TableColumn<VProSante, String>, TableCell<VProSante, String>>(){

					@Override
					public TableCell<VProSante, String> call(TableColumn<VProSante, String> param) {
						final TableCell<VProSante, String> cell = new TableCell<VProSante, String>(){
							final Button confirm = new Button();
							
							@Override
							public void updateItem(String item, boolean empty) {
								Image confirmImg = new Image(getClass().getResourceAsStream("img/tick-inside-circle.png"));
								super.updateItem(item, empty);
								if(empty) {
									setGraphic(null);
									setText(null);
								}else {
									String btnStyle= "-fx-background-color:#00ff04;\r\n" + 
											"-fx-background-radius: 5em; \r\n" + 
											"-fx-text-fill: white; \r\n" + 
											"-fx-cursor: hand;";
									confirm.setStyle(btnStyle);
									confirm.setGraphic(new ImageView(confirmImg));
									confirm.setOnMouseClicked(event ->{
										VProSante v = getTableView().getItems().get(getIndex());
										try {
											showLatLongModal(event, v);
											getTableView().getItems().remove(getIndex());
										} catch (IOException e1) {
											LOGGER.log(Level.SEVERE, e1.getMessage());
										}
//										utilisateurService.confirmerProSante(v);
//										Notifier.INSTANCE.notifySuccess("Succès", "Inscription validée.");
										
									});
									setGraphic(confirm);
									setText(null);
									
								}
							}
						};
						return cell;
					}
			
		};
		
		Callback<TableColumn<VProSante, String>, TableCell<VProSante, String>> cellFactoryRejet =
				new Callback<TableColumn<VProSante, String>, TableCell<VProSante, String>>(){

					@Override
					public TableCell<VProSante, String> call(TableColumn<VProSante, String> param) {
						final TableCell<VProSante, String> cell = new TableCell<VProSante, String>(){
							final Button rejet = new Button();
							
							@Override
							public void updateItem(String item, boolean empty) {
								Image rejectImg = new Image(getClass().getResourceAsStream("img/signal.png"));
								super.updateItem(item, empty);
								if(empty) {
									setGraphic(null);
									setText(null);
								}else {
									String btnStyle= "-fx-background-color:#ff0000;\r\n" + 
											"-fx-background-radius: 5em; \r\n" + 
											"-fx-text-fill: white; \r\n" + 
											"-fx-cursor: hand;";
									rejet.setStyle(btnStyle);
									rejet.setGraphic(new ImageView(rejectImg));
									rejet.setOnMouseClicked(event ->{
										VProSante v = getTableView().getItems().get(getIndex());
										try {
											showModalMotifRejet(event, v);
										} catch (IOException e) {
											LOGGER.log(Level.SEVERE, e.getMessage());
										}
									});
									setGraphic(rejet);
									setText(null);
									
								}
							}
						};
						return cell;
					}
			
		};
		accepterCol.setCellFactory(cellFactory);
		rejectCol.setCellFactory(cellFactoryRejet);
		listProSante.setItems(data);
	}
	
	public void showLatLongModal(MouseEvent event, VProSante v) throws IOException {
		Stage stage = new Stage();
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(Main.class.getResource("latLongModal.fxml"));
	    Parent root = loader.load();
	    LatLongModalController ctrl = loader.getController();
	    stage.setScene(new Scene(root));
	    stage.setTitle("Confirmation Professionnel de santé");
	    stage.initModality(Modality.WINDOW_MODAL);
	    stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
	    stage.show();
	    ctrl.initComponents(v);
	}
	
	public void showModalMotifRejet(MouseEvent event, VProSante v) throws IOException {
	 	Stage stage = new Stage();
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(Main.class.getResource("motifRejetModal.fxml"));
	    Parent root = loader.load();
	    MotifRejetModalController ctrl = loader.getController();
	    stage.setScene(new Scene(root));
	    stage.setTitle("Motif rejet");
	    stage.initModality(Modality.WINDOW_MODAL);
	    stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
	    stage.show();
	    ctrl.initComponents(v);
}
}
