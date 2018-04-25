package edu.esprit.smartInnov.gui.main;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import edu.esprit.smartInnov.entites.Maladie;
import edu.esprit.smartInnov.vues.VMedicament;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

public class ListMaladieByMedicamentModalController {

	@FXML
	private TableView<Maladie> maladies;
	@FXML
	private TableColumn<Maladie, String> libelleCol;
	public void initComponents(List<Maladie> data) {
		fillTableView(data);
	}
	
	public void fillTableView(List<Maladie> data) {
		ObservableList<Maladie> observableData = FXCollections.observableArrayList(data);
		libelleCol.setCellValueFactory( new PropertyValueFactory<Maladie,String>("libelle"));
		maladies.setItems(observableData);
	}
}
