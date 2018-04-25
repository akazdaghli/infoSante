package edu.esprit.smartInnov.gui.main;

import java.io.IOException;
import java.util.List;

import edu.esprit.smartInnov.entites.Specialite;
import edu.esprit.smartInnov.services.SpecialiteService;
import edu.esprit.smartInnov.services.UtilisateurService;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProSanteController {

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
//		    Parent root = FXMLLoader.load(
//		        Main.class.getResource("proSanteDetails.fxml"));
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
		listProSante.setItems(data);
	}
}
