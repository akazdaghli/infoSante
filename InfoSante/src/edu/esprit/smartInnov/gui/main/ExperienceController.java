package edu.esprit.smartInnov.gui.main;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import edu.esprit.smartInnov.entites.Experience;
import edu.esprit.smartInnov.entites.Utilisateur;
import edu.esprit.smartInnov.services.ExperienceService;
import edu.esprit.smartInnov.vues.VExperience;
import edu.esprit.smartInnov.vues.VMedicament;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class ExperienceController {

	private List<Experience> userExperiences;
	private List<Experience> allExperiences;
	private List<VExperience> allVExperience;
	private List<VExperience> userVExperience;
	private Utilisateur user;
	private static final Logger LOGGER = Logger.getLogger(ExperienceController.class.getName());
	
	@FXML
	private TableView<VExperience> allExperiencesTable;
	@FXML
	private TableColumn<VExperience, String> userCol;
	@FXML
	private TableColumn<VExperience, String> titreCol;
	@FXML
	private TableColumn<VExperience, String> dateCol;
	@FXML
	private TableColumn<VExperience, String> nbrCommCol;
	@FXML
	private TableColumn<VExperience, String> nbrVuesCol;
	@FXML
	private RadioButton toutesExp;
	@FXML
	private RadioButton userExp;
	
	private ExperienceService experienceService;
	public void initComponents(Utilisateur userConnected) {
		user = userConnected;
		experienceService = new ExperienceService();
		ToggleGroup group = new ToggleGroup();
		toutesExp.setToggleGroup(group);
		userExp.setToggleGroup(group);
		toutesExp.setSelected(true);
		allVExperience = experienceService.getAllVExperiences();
		fillExperienceTable(allVExperience);
		allExperiencesTable.setRowFactory(ev -> {
			TableRow<VExperience> row = new TableRow<VExperience>();
			row.setOnMouseClicked(event -> {
		        if (! row.isEmpty() ) {
		        	VExperience rowData = row.getItem();
		        	LOGGER.info("Expérience "+ rowData.getTitre() +" pressed...");
		        	experienceService.incrementExperienceVues(rowData);
		        	try {
						showExperienceDetailView(rowData);
					} catch (IOException e) {
						LOGGER.severe("Cannot navigate to Experience details view");
					}
		        }
		    });
			return row ;
		});
	}
	
	public void fillExperienceTable(List<VExperience> dataList) {
		ObservableList<VExperience> data = FXCollections.observableArrayList(dataList);
		userCol.setCellValueFactory( new PropertyValueFactory<VExperience,String>("user"));
		titreCol.setCellValueFactory( new PropertyValueFactory<VExperience,String>("titre"));
		dateCol.setCellValueFactory( new PropertyValueFactory<VExperience,String>("date"));
		nbrCommCol.setCellValueFactory( new PropertyValueFactory<VExperience,String>("nbrComm"));
		nbrVuesCol.setCellValueFactory(new PropertyValueFactory<VExperience,String>("nbrVues"));
		allExperiencesTable.setItems(data);
	}
	
	public void showExperienceDetailView(VExperience v) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("experienceDetailView.fxml"));
		AnchorPane view = (AnchorPane) loader.load();
		ExperienceDetailController ctrl = loader.getController();
		ctrl.initComponents(v);
		Main.getMainLayout().setCenter(view);																		
	}
	
	public void showAjouterExperienceView() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("ajouterExperience.fxml"));
		AnchorPane view = (AnchorPane) loader.load();
		AjouterExperienceController ctrl = loader.getController();
		ctrl.initComponents(user);
		Main.getMainLayout().setCenter(view);
	}
	
	public void changeData() {
		if(toutesExp.isSelected()) {
			allVExperience = experienceService.getAllVExperiences();
			fillExperienceTable(allVExperience);
		}else {
			userVExperience = experienceService.getVExperienceByUser(user);
			fillExperienceTable(userVExperience);
		}
	}
	
}
