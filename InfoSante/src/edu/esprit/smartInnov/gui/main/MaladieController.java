package edu.esprit.smartInnov.gui.main;

import java.io.IOException;
import java.util.List;

import edu.esprit.smartInnov.entites.Maladie;
import edu.esprit.smartInnov.entites.Specialite;
import edu.esprit.smartInnov.services.MaladieService;
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

public class MaladieController {

	@FXML
	private TextField libelleTF;
	@FXML
	private TableView<Maladie> maladiesTable;
	@FXML
	private TableColumn<Maladie, String> libelleCol;
	@FXML
	private TableColumn<Maladie, String> updateCol;
	@FXML
	private TableColumn<Maladie, String> deleteCol;
	@FXML
	private Button addBtn;
	
	private List<Maladie> listMaladies;
	private MaladieService maladieService;
	public void  initComponents() {
		maladieService = new MaladieService();
		listMaladies = maladieService.getAll();
		fillMaladieTable(listMaladies);
		addBtn.setOnMouseClicked(event->{
			try {
				showModifierAjouterModal(event, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	public void fillMaladieTable(List<Maladie> dataList) {
		ObservableList<Maladie> data = FXCollections.observableArrayList(dataList);
		libelleCol.setCellValueFactory(new PropertyValueFactory<Maladie,String>("libelle"));
		updateCol.setCellValueFactory( new PropertyValueFactory<Maladie,String>("update"));
		deleteCol.setCellValueFactory( new PropertyValueFactory<Maladie,String>("delete"));
		Callback<TableColumn<Maladie, String>, TableCell<Maladie, String>> deleteFactory =
				new Callback<TableColumn<Maladie, String>, TableCell<Maladie, String>>(){

					@Override
					public TableCell<Maladie, String> call(TableColumn<Maladie, String> param) {
						final TableCell<Maladie, String> cell = new TableCell<Maladie, String>(){
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
										Maladie p = getTableView().getItems().get(getIndex());
									});
									setGraphic(delete);
									setText(null);
									
								}
							}
						};
						return cell;
					}
			
		};
		Callback<TableColumn<Maladie, String>, TableCell<Maladie, String>> updateFactory =
				new Callback<TableColumn<Maladie, String>, TableCell<Maladie, String>>(){

					@Override
					public TableCell<Maladie, String> call(TableColumn<Maladie, String> param) {
						final TableCell<Maladie, String> cell = new TableCell<Maladie, String>(){
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
										Maladie p = getTableView().getItems().get(getIndex());
										try {
											showModifierAjouterModal(event, p);
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									});
									setGraphic(update);
									setText(null);
									
								}
							}
						};
						return cell;
					}
			
		};
		deleteCol.setCellFactory(deleteFactory);
		updateCol.setCellFactory(updateFactory);
		maladiesTable.setItems(data);
	}
	
	public void filtrer() {
		Maladie m =new Maladie();
		m.setLibelle(libelleTF.getText());
		fillMaladieTable(maladieService.getMaladieByObjectSearch(m));
		
	}
	
	public void showModifierAjouterModal(MouseEvent event, Maladie rowData) throws IOException {
		Stage stage = new Stage();
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(Main.class.getResource("ajouterModifierMaladieModal.fxml"));
	    Parent root = loader.load();
	    AjouterModifierMaladieController ctrl = loader.getController();
	    stage.setScene(new Scene(root));
	    if(rowData == null) {
	    	stage.setTitle("Ajouter Maladie");
	    }else {
	    	stage.setTitle("Modifier Maladie");
	    }
	    stage.initModality(Modality.WINDOW_MODAL);
	    stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
	    stage.show();
	    ctrl.initComponents(rowData);
	}
}
