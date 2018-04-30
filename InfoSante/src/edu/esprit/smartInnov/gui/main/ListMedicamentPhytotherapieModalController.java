package edu.esprit.smartInnov.gui.main;

import java.io.IOException;
import java.util.List;

import edu.esprit.smartInnov.entites.Maladie;
import edu.esprit.smartInnov.entites.Medicament;
import edu.esprit.smartInnov.entites.Phytotherapie;
import edu.esprit.smartInnov.services.MedicamentService;
import edu.esprit.smartInnov.services.PhytotherapieService;
import edu.esprit.smartInnov.vues.VMedicament;
import edu.esprit.smartInnov.vues.VProSante;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ListMedicamentPhytotherapieModalController {

	@FXML
	private TableView<VMedicament> medsTable;
	@FXML
	private TableView<Phytotherapie> phytosTable;
	@FXML
	private TableColumn<VMedicament, String> libelleCol;
	@FXML
	private TableColumn<VMedicament, String> typeCol;
	@FXML
	private TableColumn<VMedicament, String> applicationCol;
	@FXML
	private TableColumn<VMedicament, String> laboCol;
	@FXML
	private TableColumn<Phytotherapie, String> phytoLibelleCol;
	@FXML
	private TableColumn<Phytotherapie, String> phytoDetailCol;
	private MedicamentService medicamentService;
	private PhytotherapieService phytotherapieService;
	private List<VMedicament> meds;
	private List<Phytotherapie> phytos;
	public void initComponents(Maladie m) {
		medicamentService = new MedicamentService();
		phytotherapieService = new PhytotherapieService();
		meds = medicamentService.getListVMedicamentByMaladie(m);
		phytos = phytotherapieService.getListPhytotherapieByMaladie(m);
		fillMedicamentTable(meds);
		fillPhytotherapieTable(phytos);
		setEventHandlerToMedsTable();
	}
	
	public void fillMedicamentTable(List<VMedicament> dataList) {
		ObservableList<VMedicament> data = FXCollections.observableArrayList(dataList);
		libelleCol.setCellValueFactory( new PropertyValueFactory<VMedicament,String>("libelle"));
		typeCol.setCellValueFactory( new PropertyValueFactory<VMedicament,String>("type"));
		applicationCol.setCellValueFactory( new PropertyValueFactory<VMedicament,String>("application"));
		laboCol.setCellValueFactory( new PropertyValueFactory<VMedicament,String>("labo"));
		medsTable.setItems(data);
	}
	
	public void fillPhytotherapieTable(List<Phytotherapie> dataList) {
		ObservableList<Phytotherapie> data = FXCollections.observableArrayList(dataList);
		phytoLibelleCol.setCellValueFactory( new PropertyValueFactory<Phytotherapie,String>("libelle"));
		phytoDetailCol.setCellValueFactory( new PropertyValueFactory<Phytotherapie,String>("detail"));
		phytosTable.setItems(data);
	}
	
	public void setEventHandlerToMedsTable() {
		medsTable.setRowFactory(ev -> {
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
	
	public void showNoticeModal(MouseEvent event,VMedicament rowData) throws IOException {
	    Stage stage = new Stage();
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
