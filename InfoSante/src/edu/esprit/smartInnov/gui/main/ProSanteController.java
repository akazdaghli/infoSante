package edu.esprit.smartInnov.gui.main;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.esprit.smartInnov.entites.Specialite;
import edu.esprit.smartInnov.services.SpecialiteService;
import edu.esprit.smartInnov.services.UtilisateurService;
import edu.esprit.smartInnov.utils.IConstants;
import edu.esprit.smartInnov.vues.VMedicament;
import edu.esprit.smartInnov.vues.VProSante;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ProSanteController {

	private static final Logger LOGGER = Logger.getLogger(ProSanteController.class.getName());
	
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
	private TableColumn<VProSante, String> rvCol;
	@FXML
	private TextField filterNom;
	@FXML
	private TextField filterPrenom;
	@FXML
	private ComboBox<String> filterSpecialite;
	@FXML
	private TextField filterAdresse;
	@FXML
	private Button filterButton;
	@FXML
	private Button showModal;
	private UtilisateurService utilisateurService;
	SpecialiteService ss;
	
	public void initComponents() {
		utilisateurService = new UtilisateurService();
		List<VProSante> proSantes = utilisateurService.getAllVProSante();
		ss = new SpecialiteService();
		List<Specialite> specs = ss.getAllSpecialites();
		filterSpecialite.getItems().clear();
		filterSpecialite.getItems().add("");
		for(Specialite s : specs) {
			filterSpecialite.getItems().add(s.getLibelle());
		}
		
		fillTableView(proSantes);
		listProSante.setRowFactory(ev -> {
			TableRow<VProSante> row = new TableRow<VProSante>();
			row.setOnMouseClicked(event -> {
		        if (! row.isEmpty() ) {
		        	VProSante rowData = row.getItem();
		        	try {
						showDetailsModal(event, rowData);
					} catch (IOException e) {
						e.printStackTrace();
					}
		        }
		    });
			return row ;
		});
	}
	
	public void filter() {
		VProSante v = new VProSante();
		v.setAdresse(filterAdresse.getText());
		v.setNom(filterNom.getText());
		v.setPrenom(filterPrenom.getText());
		v.setLibelle(filterSpecialite.getValue());
		fillTableView(utilisateurService.getListMedecinsFiltred(v));
	}
	
	public void showDetailsModal(MouseEvent event,VProSante rowData) throws IOException {
		    Stage stage = new Stage();
		    FXMLLoader loader = new FXMLLoader();
		    loader.setLocation(Main.class.getResource("proSanteDetails.fxml"));
		    Parent root = loader.load();
		    ProSanteDetailsController ctrl = loader.getController();
		    stage.setScene(new Scene(root));
		    stage.setTitle("Détails");
		    stage.initModality(Modality.WINDOW_MODAL);
		    stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
		    stage.show();
		    ctrl.initComponents(rowData);
	}
	
	public void fillTableView(List<VProSante> dataList) {
		ObservableList<VProSante> data = FXCollections.observableArrayList(dataList);
		nomCol.setCellValueFactory( new PropertyValueFactory<VProSante,String>("nom"));
		prenomCol.setCellValueFactory( new PropertyValueFactory<VProSante,String>("prenom"));
		numTelCol.setCellValueFactory( new PropertyValueFactory<VProSante,String>("numTel"));
		specialiteCol.setCellValueFactory( new PropertyValueFactory<VProSante,String>("libelle"));
		adresseCol.setCellValueFactory( new PropertyValueFactory<VProSante,String>("adresse"));
		rvCol.setCellValueFactory( new PropertyValueFactory<VProSante,String>("rendezvous"));
		if(Main.getUserConnected() != null && Main.getUserConnected().getProfil().equals(IConstants.Profils.PATIENT)) {
			Callback<TableColumn<VProSante, String>, TableCell<VProSante, String>> cellFactory =
					new Callback<TableColumn<VProSante, String>, TableCell<VProSante, String>>(){

						@Override
						public TableCell<VProSante, String> call(TableColumn<VProSante, String> param) {
							final TableCell<VProSante, String> cell = new TableCell<VProSante, String>(){
								final Button maladie = new Button("Rendez-vous");
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
										maladie.setStyle(btnStyle);
										maladie.setOnMouseClicked(event ->{
											VProSante m = getTableView().getItems().get(getIndex());
											LOGGER.info("Rendez vous avec dr. "+m.getNom());
											try {
												showPriseRendezvousView(event, m);
											} catch (IOException e) {
												LOGGER.severe("error rendering prise rendez vous modal");
											}
											
										});
										setGraphic(maladie);
										setText(null);
										
									}
								}
							};
							return cell;
						}
				
			};
			rvCol.setCellFactory(cellFactory);
		}
		listProSante.setItems(data);
	}
	
	public void showPriseRendezvousView(MouseEvent event, VProSante rowData) throws IOException {
		   Stage stage = new Stage();
		    FXMLLoader loader = new FXMLLoader();
		    loader.setLocation(Main.class.getResource("priseRendezvousView.fxml"));
		    Parent root = loader.load();
		    PriseRendezVousController ctrl = loader.getController();
		    stage.setScene(new Scene(root));
		    stage.setTitle("Prise rendez-vous avec Dr. "+rowData.getNom());
		    stage.initModality(Modality.WINDOW_MODAL);
		    stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
		    stage.show();
		    ctrl.initComponents(rowData);
	}
}
