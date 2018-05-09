package edu.esprit.smartInnov.gui.main;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import edu.esprit.smartInnov.entites.Symptome;
import edu.esprit.smartInnov.entites.Symptome;
import edu.esprit.smartInnov.services.SymptomeService;
import eu.hansolo.enzo.notification.Notification.Notifier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SymptomeController {

	private static final Logger LOGGER = Logger.getLogger(SymptomeController.class.getName());
	
	@FXML
	private TableView<Symptome> symptomeTable;
	@FXML
	private TableColumn<Symptome, String> libelleCol;
	@FXML
	private TableColumn<Symptome, String> deleteCol;
	@FXML
	private TableColumn<Symptome, String> updateCol;
	@FXML
	private TextField libelleField;
	@FXML
	private Button addBtn;
	
	private SymptomeService symptomeService;
	private List<Symptome> symptomes;
	public void initComponents() {
		symptomeService = new SymptomeService();
		symptomes = symptomeService.getListSymptomes();
		fillSymptomeTable(symptomes);
		addBtn.setOnMouseClicked(event->{
			try {
				showModifierAjouterModal(event, true, null);
			} catch (IOException e) {
				LOGGER.severe("error rendering modal");
			}
		});
	}
	
	public void fillSymptomeTable(List<Symptome> dataList) {
		ObservableList<Symptome> data = FXCollections.observableArrayList(dataList);
		libelleCol.setCellValueFactory( new PropertyValueFactory<Symptome,String>("designation"));
		deleteCol.setCellValueFactory( new PropertyValueFactory<Symptome,String>("delete"));
		updateCol.setCellValueFactory( new PropertyValueFactory<Symptome,String>("update"));
		Callback<TableColumn<Symptome, String>, TableCell<Symptome, String>> cellFactory =
				new Callback<TableColumn<Symptome, String>, TableCell<Symptome, String>>(){

					@Override
					public TableCell<Symptome, String> call(TableColumn<Symptome, String> param) {
						final TableCell<Symptome, String> cell = new TableCell<Symptome, String>(){
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
										Symptome s = getTableView().getItems().get(getIndex());
										symptomeService.supprimer(s);
										getTableView().getItems().remove(getIndex());
										LOGGER.info("deleting Symptome");
										Notifier.INSTANCE.notifySuccess("Succès", "Symptome supprimée");
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
		Callback<TableColumn<Symptome, String>, TableCell<Symptome, String>> cellFactory2 =
				new Callback<TableColumn<Symptome, String>, TableCell<Symptome, String>>(){

					@Override
					public TableCell<Symptome, String> call(TableColumn<Symptome, String> param) {
						final TableCell<Symptome, String> cell = new TableCell<Symptome, String>(){
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
										Symptome s = getTableView().getItems().get(getIndex());
										try {
											showModifierAjouterModal(event, false, s);
										} catch (IOException e) {
											LOGGER.severe(e.getMessage());
										}
										LOGGER.info("updating Symptome");
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
		symptomeTable.setItems(data);
	}
	
	public void filtrer() {
		symptomes = symptomeService.getSymptomesByDesignation(libelleField.getText());
		fillSymptomeTable(symptomes);
		LOGGER.info("filtering...");
	}
	
	public void showModifierAjouterModal(MouseEvent event, boolean add, Symptome rowData) throws IOException{
		Stage stage = new Stage();
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(Main.class.getResource("ModifierAjouterModal.fxml"));
	    Parent root = loader.load();
	    ModifierAjouterSymptomeController ctrl = loader.getController();
	    stage.setScene(new Scene(root));
	    if(add) {
	    	stage.setTitle("Ajouter symptome");
	    }else {
	    	stage.setTitle("Modifier symptome");
	    }
	    stage.initModality(Modality.WINDOW_MODAL);
	    stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
	    stage.show();
	    ctrl.initComponents(rowData, add);
	}
}
