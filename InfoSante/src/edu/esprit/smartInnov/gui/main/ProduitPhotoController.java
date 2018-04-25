package edu.esprit.smartInnov.gui.main;

import java.io.InputStream;

import edu.esprit.smartInnov.vues.VProduit;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ProduitPhotoController {

	@FXML
	private ImageView imgView;
	@FXML
	private TextArea modeEmploi;
	public void initComponents(VProduit v) {
		if(v.getImage() != null) {
			InputStream photo = v.getImage();
			Image img = new Image(photo);
			imgView.setImage(img);
		}
		if(v.getModeEmploi() != null) {
			modeEmploi.setText(v.getModeEmploi());
		}
	}
}
