package edu.esprit.smartInnov.gui.main;

import java.io.InputStream;
import java.util.List;

import edu.esprit.smartInnov.entites.Phytotherapie;
import edu.esprit.smartInnov.entites.Utilisateur;
import edu.esprit.smartInnov.services.PhytotherapieService;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class PhytotherapieController {

	@FXML
	private Ellipse phytoImg;
	@FXML
	private Hyperlink suivant;
	@FXML
	private Hyperlink prec;
	@FXML
	private Label libelle;
	@FXML
	private TextFlow detail;
	
	private int i;
	private List<Phytotherapie> phytos;
	private Phytotherapie phyto;
	private PhytotherapieService phytotherapieService;
	
	public void initComponents(Utilisateur userConnected) {
		i=0;
		phytotherapieService = new PhytotherapieService();
		phytos = phytotherapieService.getAllPhytotherapie();
		phyto = phytos.get(i);
		fillPhyto(phyto);
	}
	
	public void increment() {
		i++;
		phyto = phytos.get(i);
		fillPhyto(phyto);
		updateHypelinks();
		if(prec.isDisabled()) {
			prec.setDisable(false);
		}
	}
	
	public void decrement() {
		i--;
		phyto = phytos.get(i);
		fillPhyto(phyto);
		updateHypelinks();
		if(suivant.isDisabled()) {
			suivant.setDisable(false);
		}
	}
	
	public void fillPhyto(Phytotherapie p) {
		
			if(p.getPhoto() != null) {
				Image img = new Image(p.getPhoto());
				phytoImg.setFill(new ImagePattern(img));
			}
			else {
				InputStream noImg = Main.class.getResourceAsStream("img/no_image.jpg");
				Image noPhoto = new Image(noImg);
				phytoImg.setFill(new ImagePattern(noPhoto));
			}
		libelle.setText(p.getLibelle());
		Text t = new Text(p.getDetail());
		detail.getChildren().clear();
		detail.getChildren().add(t);
	}
	
	public void updateHypelinks() {
		if(i == phytos.size()-1) {
			suivant.setDisable(true);
		}
		if(i == 0) {
			prec.setDisable(true);
		}
	}
}
