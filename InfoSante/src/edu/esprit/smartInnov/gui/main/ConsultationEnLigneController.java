package edu.esprit.smartInnov.gui.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.controlsfx.control.CheckListView;

import edu.esprit.smartInnov.entites.Maladie;
import edu.esprit.smartInnov.entites.Symptome;
import edu.esprit.smartInnov.services.MaladieService;
import edu.esprit.smartInnov.services.SymptomeService;
import edu.esprit.smartInnov.vues.VMedicament;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConsultationEnLigneController {

	@FXML
	private CheckListView<String> symptomesCheckList;
	@FXML
	private PieChart pourcentageChart;
	@FXML
	private Label testLabel;
	private List<Symptome> symptomes;
	private List<Symptome> symptomesChecked;
	private SymptomeService symptomeService;
	private MaladieService maladieService;
	private Map<Maladie, Double> mapPourcentage;

	public void initComponents() {
		symptomesChecked = new ArrayList<>();
		symptomeService = new SymptomeService();
		maladieService = new MaladieService();
		symptomes = symptomeService.getListSymptomes();
		symptomesCheckList.getItems().clear();
		for (Symptome s : symptomes) {
			symptomesCheckList.getItems().add(s.getDesignation());
		}
		symptomesCheckList.getCheckModel().getCheckedIndices().addListener(new ListChangeListener<Integer>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Integer> c) {
				while (c.next()) {
					if (c.wasAdded()) {
						for (int i : c.getAddedSubList()) {
							symptomesChecked.add(
									symptomeService.getSymptomeByDesignation(symptomesCheckList.getItems().get(i)));
						}
					}
					if (c.wasRemoved()) {
						for (int i : c.getRemoved()) {
							symptomesChecked.remove(
									symptomeService.getSymptomeByDesignation(symptomesCheckList.getItems().get(i)));
						}
					}
				}
			}
		});
	}

	public void validerListSymptomes() {
		mapPourcentage = new HashMap<>();
		mapPourcentage = maladieService.calculerPourcentage(symptomesChecked);

		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
		for (Maladie m : mapPourcentage.keySet()) {
			pieChartData.add(new PieChart.Data(m.getLibelle(), mapPourcentage.get(m).doubleValue()));
		}

		pourcentageChart.setTitle("Statistiques maladies");
		pourcentageChart.setClockwise(true);
		pourcentageChart.setLabelLineLength(50);
		pourcentageChart.setLabelsVisible(true);
		pourcentageChart.setStartAngle(180);
		pourcentageChart.setData(pieChartData);
		
		  for(final PieChart.Data data : pourcentageChart.getData()){
		         
	          data.getNode().addEventHandler(
	                  javafx.scene.input.MouseEvent.MOUSE_CLICKED,
	                  new EventHandler<javafx.scene.input.MouseEvent>() {
	                     
	                      @Override
	                      public void handle(javafx.scene.input.MouseEvent mouseEvent) {
	 
	                          String name = data.getName();
	                          double value = data.getPieValue();
	                          Maladie selectedMaladie = maladieService.getMaladieByLibelle(name);
	                          
	                          //Afficher modal contenant la liste des medicaments et des phytotherapies du maladie selectionnee
	                      }
	                  });
	         
	         
	      }
	}
	
	public void showModalListMedicamentPhytotherapie(MouseEvent event,Maladie m) throws IOException {
	    Stage stage = new Stage();
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(Main.class.getResource("listMedicamentPhytotherapieModal.fxml"));
	    Parent root = loader.load();
	    ListMedicamentPhytotherapieModalController ctrl = loader.getController();
	    stage.setScene(new Scene(root));
	    stage.setTitle("Liste médicaments & phytothérapies");
	    stage.initModality(Modality.WINDOW_MODAL);
	    stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
	    stage.show();
	    ctrl.initComponents(m);
	}
}
