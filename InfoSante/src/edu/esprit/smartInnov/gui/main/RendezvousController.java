package edu.esprit.smartInnov.gui.main;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.mail.MessagingException;

import edu.esprit.smartInnov.entites.Patient;
import edu.esprit.smartInnov.entites.ProSante;
import edu.esprit.smartInnov.services.RendezVousService;
import edu.esprit.smartInnov.utils.EnvoiMailUtil;
import edu.esprit.smartInnov.utils.IConstants;
import edu.esprit.smartInnov.vues.VExperience;
import edu.esprit.smartInnov.vues.VProduit;
import edu.esprit.smartInnov.vues.VRendezVous;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class RendezvousController {

	private static final Logger LOGGER = Logger.getLogger(RendezvousController.class.getName());
	@FXML
	private TableView<VRendezVous> listRendezv;
	@FXML
	private TableColumn<VRendezVous, String> docteurCol;
	@FXML
	private TableColumn<VRendezVous, String> dateCol;
	@FXML
	private TableColumn<VRendezVous, String> heureCol;
	@FXML
	private TableColumn<VRendezVous, String> etatCol;
	@FXML
	private TableColumn<VRendezVous, String> actionCol;
	
	private List<VRendezVous> listVRV;
	private RendezVousService rendezVousService;
	public void initComponents() {
		rendezVousService = new RendezVousService();
		if(Main.getUserConnected().getProfil().equals(IConstants.Profils.MEDECIN)) {
			docteurCol.setText("Patient");
			listVRV = rendezVousService.getListVRendezVousByProfSante((ProSante) Main.getUserConnected());
			fillTableView(listVRV);
		}else {
			docteurCol.setText("Docteur");
			listVRV = rendezVousService.getListVRendezVousByPatient((Patient) Main.getUserConnected());
			fillTableView(listVRV);
		}
	}
	
	public void fillTableView(List<VRendezVous> dataList){
		ObservableList<VRendezVous> data = FXCollections.observableArrayList(dataList);
		if(Main.getUserConnected().getProfil().equals(IConstants.Profils.MEDECIN)) {
			docteurCol.setCellValueFactory( new PropertyValueFactory<VRendezVous,String>("patient"));
		}else {
			docteurCol.setCellValueFactory( new PropertyValueFactory<VRendezVous,String>("docteur"));
		}
		heureCol.setCellValueFactory( new PropertyValueFactory<VRendezVous,String>("heure"));
		dateCol.setCellValueFactory( new PropertyValueFactory<VRendezVous,String>("date"));
		etatCol.setCellValueFactory( new PropertyValueFactory<VRendezVous,String>("flagValide"));
		actionCol.setCellValueFactory(new PropertyValueFactory<VRendezVous,String>("action"));
		if(Main.getUserConnected().getProfil().equals(IConstants.Profils.PATIENT)) {
			Callback<TableColumn<VRendezVous, String>, TableCell<VRendezVous, String>> cellFactory =
					new Callback<TableColumn<VRendezVous, String>, TableCell<VRendezVous, String>>(){

						@Override
						public TableCell<VRendezVous, String> call(TableColumn<VRendezVous, String> param) {
							final TableCell<VRendezVous, String> cell = new TableCell<VRendezVous, String>(){
								final Button annuler = new Button("Annuler");
								@Override
								public void updateItem(String item, boolean empty) {
									super.updateItem(item, empty);
									if(empty) {
										setGraphic(null);
										setText(null);
									}else {
										String btnStyle= "-fx-background-color:#00A2D3;\r\n" + 
												"-fx-background-radius: 5em; \r\n" + 
												"-fx-text-fill: white; \r\n" + 
												"-fx-cursor: hand;";
										annuler.setStyle(btnStyle);
										annuler.setOnMouseClicked(event ->{
											VRendezVous p = getTableView().getItems().get(getIndex());
											if(p.getFlagValide().equals("En cours")) {
												rendezVousService.cancelRendezVous(p);
												getTableView().getItems().remove(getIndex());
												// ajouter notification
											}else {
												Alert alert = new Alert(AlertType.WARNING);
												alert.setTitle("Alerte");
												alert.setHeaderText("Rendez-vous déjà confirmé");
												alert.setContentText("Ce rendez-vous est  déjà confirmé par votre docteur  vous ne pouvez plus l'annuler");
												alert.showAndWait();
												return;
											}
											LOGGER.info("assignement canceled");
										});
										setGraphic(annuler);
										setText(null);
										
									}
								}
							};
							return cell;
						}
				
			};
			actionCol.setCellFactory(cellFactory);
		}
		if(Main.getUserConnected().getProfil().equals(IConstants.Profils.MEDECIN)) {
			Callback<TableColumn<VRendezVous, String>, TableCell<VRendezVous, String>> cellFactory =
					new Callback<TableColumn<VRendezVous, String>, TableCell<VRendezVous, String>>(){

						@Override
						public TableCell<VRendezVous, String> call(TableColumn<VRendezVous, String> param) {
							final TableCell<VRendezVous, String> cell = new TableCell<VRendezVous, String>(){
								final Button confirm = new Button("Confirmer");
								@Override
								public void updateItem(String item, boolean empty) {
									super.updateItem(item, empty);
									if(empty) {
										setGraphic(null);
										setText(null);
									}else {
										String btnStyle= "-fx-background-color:#00A2D3;\r\n" + 
												"-fx-background-radius: 5em; \r\n" + 
												"-fx-text-fill: white; \r\n" + 
												"-fx-cursor: hand;";
										confirm.setStyle(btnStyle);
										confirm.setOnMouseClicked(event ->{
											VRendezVous p = getTableView().getItems().get(getIndex());
											rendezVousService.confirmRendezVous(p);
											try {
												EnvoiMailUtil.envoiMail(p.getPatientUser().getMail(), "Rendez-vous confirmé", "Info Santé", "Votre rendez-vous du "+p.getDate() +" à "+
												p.getHeure()+"a été confirmer par le docteur "+p.getDocteur()+".\n Info santé vous souhaite un bon rétablissement. ");
											} catch (MessagingException e) {
												LOGGER.severe(e.getMessage());
											}
											LOGGER.info("assignement confirmed");
											// ajouter notification
										});
										setGraphic(confirm);
										setText(null);
										
									}
								}
							};
							return cell;
						}
				
			};
			actionCol.setCellFactory(cellFactory);
		}
		listRendezv.setItems(data);
	}
}
