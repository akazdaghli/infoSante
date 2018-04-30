package edu.esprit.smartInnov.gui.main;

import java.io.InputStream;

import edu.esprit.smartInnov.services.AvisService;
import edu.esprit.smartInnov.vues.VProduit;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ProduitPhotoController {

	@FXML
	private ImageView imgView;
	@FXML
	private TextFlow modeEmploi;
	@FXML
	private Label avis;
	private AvisService avisService;
	public void initComponents(VProduit v) {
		avisService = new AvisService();
		if(v.getImage() != null) {
			InputStream photo = v.getImage();
			Image img = new Image(photo);
			imgView.setImage(img);
		}
		if(v.getModeEmploi() != null) {
			Text t = new Text(v.getModeEmploi());
			modeEmploi.getChildren().clear();
			modeEmploi.getChildren().add(t);
		}
		int note = avisService.getAvgRateByVProduit(v);
		if(note != -1 && note != 0) {
			avis.setText(note+"");
		}else {
			avis.setText("Pas de note");
		}
	}
}
