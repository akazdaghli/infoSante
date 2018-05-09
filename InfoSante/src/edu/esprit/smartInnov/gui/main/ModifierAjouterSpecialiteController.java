package edu.esprit.smartInnov.gui.main;

import edu.esprit.smartInnov.entites.Specialite;
import edu.esprit.smartInnov.services.SpecialiteService;
import eu.hansolo.enzo.notification.Notification.Notifier;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ModifierAjouterSpecialiteController {

	@FXML
	private TextField libelleLbl;
	@FXML
	private TextField typeLbl;
	
	private boolean add;
	private SpecialiteService specialiteService;
	private Specialite specialite;
	public void initComponents(Specialite s, boolean add) {
		specialiteService = new SpecialiteService();
		this.add = add;
		if(!add && s != null) {
			libelleLbl.setText(s.getLibelle());
			typeLbl.setText(s.getType());
			specialite = s;
		}
	}
	
	public void valider() {
		Specialite sp = new Specialite();
		if(libelleLbl.getText() != null && !libelleLbl.getText().isEmpty()) {
			sp.setLibelle(libelleLbl.getText());
		}else {
			Notifier.INSTANCE.notifyError("Erreur", "Veuillez saisir un libellé");
			return;
		}
		if(typeLbl.getText() != null && !typeLbl.getText().isEmpty()) {
			sp.setType(typeLbl.getText());
		}else {
			Notifier.INSTANCE.notifyError("Erreur", "Veuillez saisir un type");
			return;
		}
		if(add) {
			specialiteService.ajouter(sp);
			Notifier.INSTANCE.notifySuccess("Succès", "Spécialité ajoutée");
		}else {
			sp.setId(specialite.getId());
			specialiteService.update(sp);
			Notifier.INSTANCE.notifySuccess("Succès", "Spécialité modifiée");
		}
		typeLbl.getScene().getWindow().hide();
	}
}
