package edu.esprit.smartInnov.gui.main;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.License;

import edu.esprit.smartInnov.vues.VMedicament;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class NoticeModalController {

	private static final Logger LOGGER = Logger.getLogger(NoticeModalController.class.getName());
	@FXML
	private Label noticeLabel;
	
	@FXML
	private TextFlow noticeTF;
	
	@FXML
	private Button exportButton;
	
	private VMedicament vMed;
	public void initComponents(VMedicament med) {
		vMed = med;
		Text notice = new Text(med.getNotice());
		noticeTF.getChildren().clear();
		noticeTF.getChildren().add(notice);
//		noticeLabel.setText(med.getNotice());
		
	}
	
	public void exportNoticeToPdf() throws Exception {
		LOGGER.log(Level.INFO, "Exporting notice to pdf ...");
//		File template = new File(getClass().getResource("img/notice.doc").toURI());
//		Document doc = new Document(template.getPath());
//		DocumentBuilder db = new DocumentBuilder(doc);
//		if(vMed != null) {
//			if(vMed.getLibelle() != null && !vMed.getLibelle().isEmpty()) {
//				doc.getRange().replace("@libelle", vMed.getLibelle(), true, false);
//			}
//		}
//		String userDir = System.getProperty("user.dir");
//
//		String fileName = "Notice : " + vMed.getLibelle()+".pdf";
//		doc.save(userDir+File.separator+fileName, com.aspose.words.SaveFormat.PDF);

	}
}
