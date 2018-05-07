package edu.esprit.smartInnov.gui.main;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import edu.esprit.smartInnov.entites.Laboratoire;
import edu.esprit.smartInnov.entites.Utilisateur;
import edu.esprit.smartInnov.services.LaboratoireService;
import edu.esprit.smartInnov.services.ProduitService;
import edu.esprit.smartInnov.utils.IConstants;
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

public class ProduitController {

	private static final Logger LOGGER = Logger.getLogger(ProduitController.class.getName());
	private List<VProduit> produits;
	private ProduitService produitService;
	private LaboratoireService laboratoireService;
	private Utilisateur userConnected;
	Stage stage;
	private List<Laboratoire> labos;
	@FXML
	private TableView<VProduit> produitTable;
	@FXML
	private TableColumn<VProduit, String> libelleCol;
	@FXML
	private TableColumn<VProduit, String> categorieCol;
	@FXML
	private TableColumn<VProduit, String> laboCol;
	
	@FXML
	private TableColumn<VProduit, String> actionCol;
	@FXML
	private TextField libelleFilter;
	@FXML
	private ComboBox<String> categorieFilter;
	@FXML
	private ComboBox<String> laboFilter;
	@FXML
	private Button addBtn;
	public void initComponents(Utilisateur userConnected) {
		this.userConnected = userConnected;
		produitService = new ProduitService();
		laboratoireService = new LaboratoireService();
		labos = laboratoireService.findAll();
		produits = produitService.getAllVProduit();
		categorieFilter.getItems().clear();
		categorieFilter.getItems().add("");
		categorieFilter.getItems().addAll(IConstants.categorieProduit);
		laboFilter.getItems().clear();
		laboFilter.getItems().add("");
		for(Laboratoire l : labos) {
			laboFilter.getItems().add(l.getLibelle());
		}
		if(userConnected.getProfil().equals(IConstants.Profils.ADMINISTRATUER)) {
			addBtn.setVisible(true);
			addBtn.setManaged(true);
		}
		if(userConnected.getProfil().equals(IConstants.Profils.MEDECIN) || userConnected.getProfil().equals(IConstants.Profils.PATIENT)) {
			addBtn.setVisible(false);
			addBtn.setManaged(false);
		}
		fillTaleView(produits);
		produitTable.setRowFactory(ev -> {
			TableRow<VProduit> row = new TableRow<VProduit>();
			row.setOnMouseClicked(event -> {
		        if (! row.isEmpty() ) {
		        	VProduit rowData = row.getItem();
		        	try {
						showPhotoModal(event, rowData);
					} catch (IOException e) {
						e.printStackTrace();
					}
		        }
		    });
			return row ;
		});
	}
	
	public void filtrer() {
		VProduit vProduit = new VProduit();
		vProduit.setCategorie(categorieFilter.getValue());
		vProduit.setLabo(laboFilter.getValue());
		vProduit.setLibelle(libelleFilter.getText());
		
		fillTaleView(produitService.getListVProduitFiltred(vProduit));
	}
	
	public void showPhotoModal(MouseEvent event,VProduit rowData) throws IOException {
	    Stage stage = new Stage();
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(Main.class.getResource("produitPhoto.fxml"));
	    Parent root = loader.load();
	    ProduitPhotoController ctrl = loader.getController();
	    stage.setScene(new Scene(root));
	    stage.setTitle("Détails Produit: "+ rowData.getLibelle());
	    stage.initModality(Modality.WINDOW_MODAL);
	    stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
	    stage.show();
	    ctrl.initComponents(rowData);
}
	
	public void fillTaleView(List<VProduit> dataList) {
		ObservableList<VProduit> data = FXCollections.observableArrayList(dataList);
		libelleCol.setCellValueFactory(new PropertyValueFactory<VProduit,String>("libelle"));
		categorieCol.setCellValueFactory( new PropertyValueFactory<VProduit,String>("categorie"));
		laboCol.setCellValueFactory( new PropertyValueFactory<VProduit,String>("labo"));
		actionCol.setCellValueFactory(new PropertyValueFactory<VProduit,String>("action"));
		if(userConnected.getProfil().equals(IConstants.Profils.ADMINISTRATUER)) {
			
			Callback<TableColumn<VProduit, String>, TableCell<VProduit, String>> cellFactory =
					new Callback<TableColumn<VProduit, String>, TableCell<VProduit, String>>(){

						@Override
						public TableCell<VProduit, String> call(TableColumn<VProduit, String> param) {
							final TableCell<VProduit, String> cell = new TableCell<VProduit, String>(){
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
											VProduit p = getTableView().getItems().get(getIndex());
											showConfirmDeleteDialog(p);
										});
										setGraphic(delete);
										setText(null);
										
									}
								}
							};
							return cell;
						}
				
			};
			actionCol.setCellFactory(cellFactory);
		}
		if(userConnected.getProfil().equals(IConstants.Profils.PATIENT)) {
			Callback<TableColumn<VProduit, String>, TableCell<VProduit, String>> cellFactory =
					new Callback<TableColumn<VProduit, String>, TableCell<VProduit, String>>(){

						@Override
						public TableCell<VProduit, String> call(TableColumn<VProduit, String> param) {
							final TableCell<VProduit, String> cell = new TableCell<VProduit, String>(){
								final Button noter = new Button("Noter");
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
										noter.setStyle(btnStyle);
										noter.setOnMouseClicked(event ->{
											VProduit p = getTableView().getItems().get(getIndex());
											LOGGER.info("noting "+p.getLibelle()+"...");
											try {
												showNoterProduitModal(event, p);
											} catch (IOException e) {
												LOGGER.severe(e.getMessage());
											}
										});
										setGraphic(noter);
										setText(null);
										
									}
								}
							};
							return cell;
						}
				
			};
			actionCol.setCellFactory(cellFactory);
		}
		produitTable.setItems(data);
	}
	
	public void showConfirmDeleteDialog(VProduit p) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Vous avez choisi de supprimer le produit "+p.getLibelle());
		alert.setContentText("Voule-vous envoyer le mail quand même?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		   produitService.supprimerProduit(p.getId());
		   initComponents(userConnected);
		} else {
			alert.close();
		}
	}
	
	public void showAjouterProduitModal(MouseEvent event) throws IOException {
	    stage = new Stage();
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(Main.class.getResource("ajouterProduitModal.fxml"));
	    Parent root = loader.load();
	    AjouterProduitController ctrl = loader.getController();
	    stage.setScene(new Scene(root));
	    stage.setTitle("Ajouter Produit");
	    stage.initModality(Modality.WINDOW_MODAL);
	    stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
	    ctrl.initComponents();
	    stage.show();
	}
	
	public void showNoterProduitModal(MouseEvent event, VProduit p) throws IOException {
	    stage = new Stage();
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(Main.class.getResource("noterProduitModal.fxml"));
	    Parent root = loader.load();
	    NoterProduitController ctrl = loader.getController();
	    stage.setScene(new Scene(root));
	    stage.setTitle("Noter Produit");
	    stage.initModality(Modality.WINDOW_MODAL);
	    stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
	    ctrl.initComponents(p);
	    stage.show();
	}
	
	public Stage getAjouterProduitModal() {
		return stage;
	}
	
}
