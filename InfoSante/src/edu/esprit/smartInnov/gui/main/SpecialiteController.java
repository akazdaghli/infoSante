package edu.esprit.smartInnov.gui.main;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import edu.esprit.smartInnov.entites.Specialite;
import edu.esprit.smartInnov.services.SpecialiteService;
import eu.hansolo.enzo.notification.Notification;
import eu.hansolo.enzo.notification.Notification.Notifier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SpecialiteController {

	private static final Logger LOGGER = Logger.getLogger(SpecialiteController.class.getName());
	@FXML
	private TableView<Specialite> specialiteTable;
	@FXML
	private TableColumn<Specialite, String> libelleCol;
	@FXML
	private TableColumn<Specialite, String> typeCol;
	@FXML
	private TableColumn<Specialite, String> deleteCol;
	@FXML
	private TableColumn<Specialite, String> updateCol;
	@FXML
	private TextField libelleField;
	@FXML
	private TextField typeField;
	@FXML
	private Button addBtn;
	@FXML
	private AnchorPane pane;
	private List<Specialite> specialites;
	private SpecialiteService specialiteService;
	public void initComponents() {
		specialiteService = new SpecialiteService();
		specialites = specialiteService.getAllSpecialites();
		fillSpecialiteTable(specialites);
		addBtn.setOnMouseClicked(event ->{
			try {
				showModifierAjouterModal(event, true, null);
			} catch (IOException e) {
				LOGGER.severe(e.getMessage());
			}
		});
	}
	
	public void fillSpecialiteTable(List<Specialite> dataList) {
		ObservableList<Specialite> data = FXCollections.observableArrayList(dataList);
		libelleCol.setCellValueFactory( new PropertyValueFactory<Specialite,String>("libelle"));
		typeCol.setCellValueFactory( new PropertyValueFactory<Specialite,String>("type"));
		deleteCol.setCellValueFactory( new PropertyValueFactory<Specialite,String>("delete"));
		updateCol.setCellValueFactory( new PropertyValueFactory<Specialite,String>("update"));
		Callback<TableColumn<Specialite, String>, TableCell<Specialite, String>> cellFactory =
				new Callback<TableColumn<Specialite, String>, TableCell<Specialite, String>>(){

					@Override
					public TableCell<Specialite, String> call(TableColumn<Specialite, String> param) {
						final TableCell<Specialite, String> cell = new TableCell<Specialite, String>(){
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
										Specialite s = getTableView().getItems().get(getIndex());
										specialiteService.delete(s);
										getTableView().getItems().remove(getIndex());
										LOGGER.info("deleting specialite");
										Notifier.INSTANCE.notifySuccess("Succès", "Spécialité supprimé");
									});
									setGraphic(delete);
									setText(null);
									
								}
							}
						};
						return cell;
					}
			
		};
		deleteCol.setCellFactory(cellFactory);
		Callback<TableColumn<Specialite, String>, TableCell<Specialite, String>> cellFactory2 =
				new Callback<TableColumn<Specialite, String>, TableCell<Specialite, String>>(){

					@Override
					public TableCell<Specialite, String> call(TableColumn<Specialite, String> param) {
						final TableCell<Specialite, String> cell = new TableCell<Specialite, String>(){
							final Button update = new Button("Modifier");
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
									update.setStyle(btnStyle);
									update.setOnMouseClicked(event ->{
										Specialite s = getTableView().getItems().get(getIndex());
										try {
											showModifierAjouterModal(event, false, s);
										} catch (IOException e) {
											LOGGER.severe(e.getMessage());
										}
										LOGGER.info("updating specialite");
									});
									setGraphic(update);
									setText(null);
									
								}
							}
						};
						return cell;
					}
			
		};
		updateCol.setCellFactory(cellFactory2);
		specialiteTable.setItems(data);
	}
	
	public void showModifierAjouterModal(MouseEvent event,boolean add, Specialite rowData) throws IOException {
		Stage stage = new Stage();
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(Main.class.getResource("ModifierAjouterSpecialiteModal.fxml"));
	    Parent root = loader.load();
	    ModifierAjouterSpecialiteController ctrl = loader.getController();
	    stage.setScene(new Scene(root));
	    if(add) {
	    	stage.setTitle("Ajouter spéialité");
	    }else {
	    	stage.setTitle("Modifier spéialité");
	    }
	    stage.initModality(Modality.WINDOW_MODAL);
	    stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
	    stage.show();
	    ctrl.initComponents(rowData, add);
	}
	
	public void filtrer() {
		Specialite s = new Specialite();
		if(libelleField != null && !libelleField.getText().isEmpty()) {
			s.setLibelle(libelleField.getText());
		}
		if(typeField != null && !typeField.getText().isEmpty()) {
			s.setType(typeField.getText());
		}
		fillSpecialiteTable(specialiteService.getSpecialitesByObjectSearch(s));
		
	}
	
}
