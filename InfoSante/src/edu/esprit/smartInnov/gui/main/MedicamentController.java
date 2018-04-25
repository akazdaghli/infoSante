package edu.esprit.smartInnov.gui.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.esprit.smartInnov.entites.Laboratoire;
import edu.esprit.smartInnov.entites.Maladie;
import edu.esprit.smartInnov.entites.Utilisateur;
import edu.esprit.smartInnov.services.LaboratoireService;
import edu.esprit.smartInnov.services.MaladieService;
import edu.esprit.smartInnov.services.MedicamentService;
import edu.esprit.smartInnov.utils.IConstants;
import edu.esprit.smartInnov.vues.VMedicament;
import edu.esprit.smartInnov.vues.VProduit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MedicamentController {

	@FXML
	private TableView<VMedicament> medicaments;
	@FXML
	private TableColumn<VMedicament, String> libelleCol;
	@FXML
	private TableColumn<VMedicament, String> typeCol;
	@FXML
	private TableColumn<VMedicament, String> applicationCol;
	@FXML
	private TableColumn<VMedicament, String> laboCol;
	@FXML
	private TableColumn<VMedicament, String> actionCol;
	@FXML
	private TableColumn<VMedicament, String> deleteCol;
	@FXML
	private TextField libelleFilter;
	@FXML 
	private ComboBox<String> typeFilter;
	@FXML 
	private ComboBox<String> appFilter;
	@FXML 
	private ComboBox<String> laboFilter;
	@FXML 
	private Button btnFilter;
	@FXML 
	private Button btnAdd;
	private MedicamentService medicamentService;
	private MaladieService maladieService;
	private LaboratoireService laboratoireService;
	private List<VMedicament> listMedicaments;
	List<Laboratoire> laboratoires;
	private Utilisateur userConnected;
	private static final Logger LOGGER = Logger.getLogger(MedicamentController.class.getName());
	public void initComponents(Utilisateur userConnected) {
		this.userConnected = userConnected;
		medicamentService = new MedicamentService();
		maladieService = new MaladieService();
		laboratoireService = new LaboratoireService();
		listMedicaments = new ArrayList<>();
		laboratoires=laboratoireService.findAll();
		laboFilter.getItems().clear();
		laboFilter.getItems().add("");
		for(Laboratoire l : laboratoires) {
			laboFilter.getItems().add(l.getLibelle());
		}
		typeFilter.getItems().clear();
		typeFilter.getItems().add("");
		typeFilter.getItems().addAll(IConstants.typesMedicament);
		appFilter.getItems().clear();
		appFilter.getItems().add("");
		appFilter.getItems().addAll(IConstants.applicationMedicament);
		listMedicaments = medicamentService.getAllVMedicaments();
		fillTableView(listMedicaments);
		if(userConnected.getProfil().equals(IConstants.Profils.ADMINISTRATUER)) {
			btnAdd.setVisible(true);
			btnAdd.setManaged(true);
			deleteCol.setVisible(true);
		}else {
			btnAdd.setVisible(false);
			btnAdd.setManaged(false);
			deleteCol.setVisible(false);
		}
		medicaments.setRowFactory(ev -> {
			TableRow<VMedicament> row = new TableRow<VMedicament>();
			row.setOnMouseClicked(event -> {
		        if (! row.isEmpty() ) {
		        	VMedicament rowData = row.getItem();
		        	try {
						showNoticeModal(event, rowData);
					} catch (IOException e) {
						e.printStackTrace();
					}
		        }
		    });
			return row ;
		});
	}
	
	public void filterMedicament() {
		VMedicament v = new VMedicament();
		if(libelleFilter.getText() != null) {
			v.setLibelle(libelleFilter.getText());
		}
		v.setLabo(laboFilter.getValue());
		v.setType(typeFilter.getValue());
		v.setApplication(appFilter.getValue());
		listMedicaments = medicamentService.getListVMedicamentsFiltred(v);
		if(listMedicaments != null)
			fillTableView(listMedicaments);
	}
	
	public void fillTableView(List<VMedicament> dataList) {
		ObservableList<VMedicament> data = FXCollections.observableArrayList(dataList);
		libelleCol.setCellValueFactory( new PropertyValueFactory<VMedicament,String>("libelle"));
		typeCol.setCellValueFactory( new PropertyValueFactory<VMedicament,String>("type"));
		applicationCol.setCellValueFactory( new PropertyValueFactory<VMedicament,String>("application"));
		laboCol.setCellValueFactory( new PropertyValueFactory<VMedicament,String>("labo"));
		actionCol.setCellValueFactory(new PropertyValueFactory<VMedicament,String>("action"));
		deleteCol.setCellValueFactory(new PropertyValueFactory<VMedicament,String>("delete"));
		
		Callback<TableColumn<VMedicament, String>, TableCell<VMedicament, String>> cellFactory =
				new Callback<TableColumn<VMedicament, String>, TableCell<VMedicament, String>>(){

					@Override
					public TableCell<VMedicament, String> call(TableColumn<VMedicament, String> param) {
						final TableCell<VMedicament, String> cell = new TableCell<VMedicament, String>(){
							final Button maladie = new Button("Maladies");
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
										VMedicament m = getTableView().getItems().get(getIndex());
										try {
											showModalListMaladies(event,maladieService.getListMaladiesByVMedicament(m),m);
										} catch (IOException e) {
											LOGGER.log(Level.SEVERE, "Error occured while rendering list of maladies");
											e.printStackTrace();
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
		if(userConnected.getProfil().equals(IConstants.Profils.ADMINISTRATUER)) {
		Callback<TableColumn<VMedicament, String>, TableCell<VMedicament, String>> cellFactoryDelete =
				new Callback<TableColumn<VMedicament, String>, TableCell<VMedicament, String>>(){

					@Override
					public TableCell<VMedicament, String> call(TableColumn<VMedicament, String> param) {
						final TableCell<VMedicament, String> cell = new TableCell<VMedicament, String>(){
							final Button delete = new Button("Supprimer");
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
									delete.setStyle(btnStyle);
									delete.setOnMouseClicked(event ->{
										VMedicament m = getTableView().getItems().get(getIndex());
										showConfirmDeleteDialog(m);
									});
									setGraphic(delete);
									setText(null);
									
								}
							}
						};
						return cell;
					}
			
		};
		deleteCol.setCellFactory(cellFactoryDelete);
	}
		actionCol.setCellFactory(cellFactory);
		
		medicaments.setItems(data);
	}
	
	public void showConfirmDeleteDialog(VMedicament p) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Vous avez choisi de supprimer le médicament "+ p.getLibelle());
		alert.setContentText("Voule-vous continuer?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			medicamentService.deleteMedicament(p.getId());
			initComponents(userConnected);
		} else {
			alert.close();
		}
	}
	
	public void showModalAjout(MouseEvent event) throws IOException {
		 	Stage stage = new Stage();
		    FXMLLoader loader = new FXMLLoader();
		    loader.setLocation(Main.class.getResource("ajouterMedicamentModal.fxml"));
		    
		   
		    Parent root = loader.load();
		    AjouterMedicamentController ctrl = loader.getController();
		    stage.setScene(new Scene(root));
		    stage.setTitle("Ajouter Médicament");
		    stage.initModality(Modality.WINDOW_MODAL);
		    stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
		    stage.show();
		    ctrl.initComponents();
	}
	
	public void showModalListMaladies(MouseEvent event,List<Maladie> data, VMedicament m) throws IOException {
	    Stage stage = new Stage();
//	    Parent root = FXMLLoader.load(
//	        Main.class.getResource("proSanteDetails.fxml"));
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(Main.class.getResource("listMaladieByMedicamentModal.fxml"));
	    
	   
	    Parent root = loader.load();
	    ListMaladieByMedicamentModalController ctrl = loader.getController();
	    stage.setScene(new Scene(root));
	    stage.setTitle("Liste maladies du médicament: "+ m.getLibelle());
	    stage.initModality(Modality.WINDOW_MODAL);
	    stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
	    stage.show();
	    ctrl.initComponents(data);
	}
	
	public void showNoticeModal(MouseEvent event,VMedicament rowData) throws IOException {
	    Stage stage = new Stage();
//	    Parent root = FXMLLoader.load(
//	        Main.class.getResource("proSanteDetails.fxml"));
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(Main.class.getResource("noticeModal.fxml"));
	    
	   
	    Parent root = loader.load();
	    NoticeModalController ctrl = loader.getController();
	    stage.setScene(new Scene(root));
	    stage.setTitle("Notice: "+ rowData.getLibelle());
	    stage.initModality(Modality.WINDOW_MODAL);
	    stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
	    stage.show();
	    ctrl.initComponents(rowData);
}
}
