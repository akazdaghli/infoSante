package edu.esprit.smartInnov.gui.main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;

import edu.esprit.smartInnov.entites.Local;
import edu.esprit.smartInnov.entites.Patient;
import edu.esprit.smartInnov.entites.ProSante;
import edu.esprit.smartInnov.entites.Specialite;
import edu.esprit.smartInnov.entites.Utilisateur;
import edu.esprit.smartInnov.services.LocalService;
import edu.esprit.smartInnov.services.SpecialiteService;
import edu.esprit.smartInnov.services.UtilisateurService;
import edu.esprit.smartInnov.utils.EnvoiMailUtil;
import edu.esprit.smartInnov.utils.IConstants;
import edu.esprit.smartInnov.utils.Utilitaire;
import eu.hansolo.enzo.notification.Notification.Notifier;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class InscriptionController {

	private UtilisateurService utilisateurService;
	private Button inscrire;
	@FXML
	private Button login;
	@FXML
	private TextField loginTextField;
	@FXML
	private TextField pwdTextField;
	@FXML
	private TextField nomField;
	@FXML
	private TextField prenomField;
	@FXML
	private TextField adresseField;
	@FXML
	private TextField mailField;
	@FXML
	private TextField numTelField;
	@FXML
	private TextField loginField;
	@FXML
	private TextField pwdField;
	@FXML
	private TextField ConfirmPwdField;
	@FXML
	private RadioButton isPro;
	@FXML
	private RadioButton isParticulier;
	@FXML
	private ComboBox<String> specialites;
	@FXML
	private TextField adresseLocal;
	@FXML
	private TextField imageUpload;
	@FXML
	private Label specialiteLabel;
	@FXML
	private Label adresseLocalLabel;
	private FileInputStream fis;
	private InputStream photo;

	private Alert alert;
	SpecialiteService ss;
	ProSante userPro;

	public void initComponents() {
		utilisateurService = new UtilisateurService();
		final ToggleGroup group = new ToggleGroup();
		isPro.setToggleGroup(group);
		isParticulier.setToggleGroup(group);
		isParticulier.setSelected(true);
		changeVisibility();
		ss = new SpecialiteService();
		List<Specialite> specs = ss.getAllSpecialites();
		specialites.getItems().clear();
		for (Specialite s : specs) {
			specialites.getItems().add(s.getLibelle());
		}
		// imageUpload.setOnMouseClicked(event->{
		// FileChooser fc = new FileChooser();
		// FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG
		// files(*.jpg)","*.JPG");
		// FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG
		// files(*.png)","*.PNG");
		// fc.getExtensionFilters().addAll(ext1,ext2);
		// File file =
		// fc.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
		//
		// BufferedImage bf;
		// try {
		// bf = ImageIO.read(file);
		// Image image = SwingFXUtils.toFXImage(bf, null);
		// fis = new FileInputStream(file);
		// }catch(Exception e) {
		//
		// }
		// });
	}

	public void importImage() {
		photo = Utilitaire.importerImage(imageUpload, true);
	}

	public void changeVisibility() {
		if (isPro.isSelected()) {
			specialiteLabel.setVisible(true);
			specialiteLabel.setManaged(true);
			adresseLocalLabel.setVisible(true);
			adresseLocalLabel.setManaged(true);
			specialites.setVisible(true);
			specialites.setManaged(true);
			adresseLocal.setVisible(true);
			adresseLocal.setManaged(true);
		} else {
			specialiteLabel.setVisible(false);
			specialiteLabel.setManaged(false);
			adresseLocalLabel.setVisible(false);
			adresseLocalLabel.setManaged(false);
			specialites.setVisible(false);
			adresseLocal.setVisible(false);
			adresseLocal.setManaged(false);
			specialites.setManaged(false);
		}
	}

	public void ajouterUtilisateur() {
		Utilisateur user = new Patient();
		user.setDateCreation(new Date(Calendar.getInstance().getTimeInMillis()));
		if (photo != null) {
			user.setPhoto(photo);
		}
		if (Utilitaire.isValidName(nomField.getText())) {
			user.setNom(nomField.getText());
		} else {
			Notifier.INSTANCE.notifyError("Erreur",
					"Le nom entré est incorrect! le nom ne peut pas contenir ni des chiffres ni des caractéres spéciaux.");
			// alert = new Alert(AlertType.ERROR);
			// alert.setTitle("Erreur");
			// alert.setHeaderText("Erreur lors de la validation du nom");
			// alert.setContentText("Le nom entré est incorrect! le nom ne peut pas contenir
			// ni des chiffres ni des caractéres spéciaux.");
			// alert.showAndWait();
			return;
		}
		if (Utilitaire.isValidName(prenomField.getText())) {
			user.setPrenom(prenomField.getText());
		} else {
			Notifier.INSTANCE.notifyError("Erreur",
					"Le prénom entré est incorrect! le nom ne peut pas contenir ni des chiffres ni des caractéres spéciaux.");
			// alert = new Alert(AlertType.ERROR);
			// alert.setTitle("Erreur");
			// alert.setHeaderText("Erreur lors de la validation du prénom");
			// alert.setContentText("Le prénom entré est incorrect! le prénom ne peut pas
			// contenir ni des chiffres ni des caractéres spéciaux.");
			// alert.showAndWait();
			return;
		}
		user.setAdresse(adresseField.getText());
		if (Utilitaire.isValidMail(mailField.getText())) {
			if (!checkUserMail(mailField.getText())) {
				user.setMail(mailField.getText());
			} else {
				Notifier.INSTANCE.notifyError("Erreur",
						"L'adresse mail entrée est utilisée par un autre utilisateur! ");
				// alert = new Alert(AlertType.ERROR);
				// alert.setTitle("Erreur");
				// alert.setHeaderText("Erreur lors de la validation du mail");
				// alert.setContentText("L'adresse mail entrée est utilisée par unautre
				// utilisateur! ");
				// alert.showAndWait();
				return;
			}
		} else {
			Notifier.INSTANCE.notifyError("Erreur", "L'adresse mail entrée est incorrect! ");
			// alert = new Alert(AlertType.ERROR);
			// alert.setTitle("Erreur");
			// alert.setHeaderText("Erreur lors de la validation du mail");
			// alert.setContentText("L'adresse mail entrée est incorrect! ");
			// alert.showAndWait();
			return;
		}
		if (Utilitaire.isValidTel(numTelField.getText())) {
			user.setNumTel(numTelField.getText());
		} else {
			Notifier.INSTANCE.notifyError("Erreur", "Le numéro de teléphone entré est incorrect! ");
			// alert = new Alert(AlertType.ERROR);
			// alert.setTitle("Erreur");
			// alert.setHeaderText("Erreur lors de lavalidation du numéro de teléphone");
			// alert.setContentText("Le numéro de teléphone entré est incorrect! ");
			// alert.showAndWait();
			return;
		}
		if (loginField.getText() != null && !loginField.getText().isEmpty() && !checkUserLogin(loginField.getText())) {
			user.setLogin(loginField.getText());
		} else {
			Notifier.INSTANCE.notifyError("Erreur",
					"Le login entré est utilisé parun autre utilisateur! veuillez en choisir un autre.");
			// alert = new Alert(AlertType.ERROR);
			// alert.setTitle("Erreur");
			// alert.setHeaderText("Erreur lors de lavalidation du login");
			// alert.setContentText("Le login entré est utilisé parun autre utilisateur!
			// veuillez en choisir un autre.");
			// alert.showAndWait();
			return;
		}
		if (Utilitaire.checkPassword(pwdField.getText())
				&& Utilitaire.checkConfirmPassword(pwdField.getText(), ConfirmPwdField.getText())) {
			user.setPwd(pwdField.getText());
		} else {
			Notifier.INSTANCE.notifyError("Erreur",
					"Les mots de passe saisis ne sont pas identiques, veuillez vérifier votre mot de passe");
			// alert = new Alert(AlertType.ERROR);
			// alert.setTitle("Erreur");
			// alert.setHeaderText("Erreur lors de la validation du mot de passe");
			// alert.setContentText("Les mots de passe saisis ne sont pas identiques,
			// veuillez vérifier votre mot de passe");
			// alert.showAndWait();
			return;
		}
		// if(isPro.isSelected() && specialites.getValue()!= null &&
		// !specialites.getValue().isEmpty()) {
		// ProSante userPro = new ProSante(user);
		// userPro.setSpecialite(ss.getSpecialiteByLibelle(specialites.getValue()));
		// }
		//
		// if(isPro.isSelected() && adresseLocalLabel.getText() != null &&
		// !adresseLocalLabel.getText().isEmpty()) {
		// LocalService ls = new LocalService();
		// Local l = new Local();
		// ((ProSante) user).setLocal(ls.ajouter(l));
		// }

		if (isPro.isSelected()) {
			userPro = new ProSante(user);
			if (specialites.getValue() != null && !specialites.getValue().isEmpty()) {
				userPro.setSpecialite(ss.getSpecialiteByLibelle(specialites.getValue()));
			}
			if (adresseLocal.getText() != null && !adresseLocal.getText().isEmpty()) {
				LocalService ls = new LocalService();
				Local l = new Local();
				l.setAdresse(adresseLocal.getText());
				switch (ss.getSpecialiteByLibelle(specialites.getValue()).getType()) {
				case IConstants.TypeSecialites.KINESIE:
					l.setType(IConstants.TypeLocals.CENTRE);
					break;
				case IConstants.TypeSecialites.MEDECINE:
					l.setType(IConstants.TypeLocals.CABINET);
					break;
				case IConstants.TypeSecialites.RADIOLOGIE:
					l.setType(IConstants.TypeLocals.LABORATOIRE);
					break;
				}
				userPro.setLocal(ls.ajouter(l));
			}
		}

		utilisateurService = new UtilisateurService();
		if (isPro.isSelected()) {
			userPro.setFlagActif(false);
			utilisateurService.ajouter(userPro);
			Notifier.INSTANCE.notifySuccess("Succès",
					"Votre inscription est prise en compte par notre système, un mail vous sera envoyé à l'adresse "
							+ mailField.getText() + " pour vérifier votre profession et activer votre inscription.");
			// alert = new Alert(AlertType.INFORMATION);
			// alert.setTitle("Succès");
			// alert.setHeaderText("Inscription effectuée");
			// alert.setContentText("Votre inscription est prise en compte par notre
			// système, un mail vous sera envoyé à l'adresse "+
			// mailField.getText() + " pour vérifier votre profession et activer votre
			// inscription.");
			// alert.showAndWait();

			try {
				EnvoiMailUtil.envoiMail(mailField.getText(), "Confirmation Inscription",
						"Bonjour " + userPro.getNom() + " " + userPro.getPrenom() + ".",
						"Veuillez nous fournir vos documents pour vérifier votre profession.");
			} catch (MessagingException e) {

			}
			;
			clearAll();
		} else {
			user.setFlagActif(true);
			utilisateurService.ajouter(user);
			Notifier.INSTANCE.notifySuccess("Succès",
					"Votre inscription est prise en compte par notre système, bienvenue sur infoSanté");
			// alert = new Alert(AlertType.INFORMATION);
			// alert.setTitle("Succès");
			// alert.setHeaderText("Inscription effectuée");
			// alert.setContentText("Votre inscription est prise en compte par notre
			// système, bienvenue sur infoSanté");
			// alert.showAndWait();
			clearAll();
		}

	}

	public void loginUsingKeyboard(KeyEvent ev) throws NoSuchAlgorithmException {
		if (ev.getCode().equals(KeyCode.ENTER)) {
			login();
		}
	}

	public void login() throws NoSuchAlgorithmException {
		utilisateurService = new UtilisateurService();
		Utilisateur user = utilisateurService.getUserByLogin(loginTextField.getText());
		if (user != null) {
			if (Utilitaire.verifyHashedPassword(user.getPwd(), Utilitaire.hashMD5Crypt(pwdTextField.getText()))
					&& user.getFlagActif()) {
				// alert = new Alert(AlertType.INFORMATION);
				// alert.setTitle("Success");
				// alert.setHeaderText("Login success");
				// alert.setContentText("Bienvenue, " + user.getNom() +" "+ user.getPrenom());
				// alert.showAndWait();
				try {
					navigateToAcceuilPage(user);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				Notifier.INSTANCE.notifyError("Erreur", "Veuillez vérifier vos coordonnées");
				// alert = new Alert(AlertType.ERROR);
				// alert.setTitle("Error");
				// alert.setHeaderText("Login sucerrors");
				// alert.setContentText("Veuillez vérifier vos coordonnées");
				// alert.showAndWait();
			}
		} else {
			Notifier.INSTANCE.notifyError("Erreur",
					"Vous n'etes pas encore inscris, veuillez vous inscrire pour pouvoir se connecter");
			// alert = new Alert(AlertType.ERROR);
			// alert.setTitle("Error");
			// alert.setHeaderText("Login error");
			// alert.setContentText("Vous n'etes pas encore inscris, veuillez vous inscrire
			// pour pouvoir se connecter");
			// alert.showAndWait();
		}
	}

	public void showPwOublieModal() throws IOException {
		 Stage stage = new Stage();
		    FXMLLoader loader = new FXMLLoader();
		    loader.setLocation(Main.class.getResource("pwdOublieModal.fxml"));
		    Parent root = loader.load();
		    PwdOublieController ctrl = loader.getController();
		    stage.setScene(new Scene(root));
		    stage.setTitle("Mot de passe oublié");
		    stage.initModality(Modality.WINDOW_MODAL);
		    stage.initOwner((specialiteLabel.getScene().getWindow())) ;
		    stage.show();
	}

	public void clearAll() {
		nomField.clear();
		prenomField.clear();
		adresseField.clear();
		mailField.clear();
		numTelField.clear();
		loginField.clear();
		pwdField.clear();
		ConfirmPwdField.clear();
		adresseLocal.clear();
		loginTextField.clear();
		pwdTextField.clear();
		imageUpload.clear();
		// initComponents();
	}

	public boolean checkUserLogin(String login) {
		return utilisateurService.isUtilisateurExistByLogin(login);
	}

	public boolean checkUserMail(String mail) {
		return utilisateurService.isUtilisateurExistByMail(mail);

	}

	private void navigateToAcceuilPage(Utilisateur userConnected) throws IOException {
		// FXMLLoader loader = new FXMLLoader(Main.class.getResource("acceuilV2.fxml"));
		// Parent root = loader.load();
		// AcceuilController acceuilController = loader.getController();
		// acceuilController.setUserConnected(userConnected);
		// acceuilController.initComponents();
		// loginTextField.getScene().setRoot(root);
		Main.showAcceuilView(userConnected);
	}

	public TextField getNomField() {
		return nomField;
	}

	public void setNomField(TextField nomField) {
		this.nomField = nomField;
	}

	public Button getInscrire() {
		return inscrire;
	}

	public void setInscrire(Button inscrire) {
		this.inscrire = inscrire;
	}

	public TextField getPrenomField() {
		return prenomField;
	}

	public void setPrenomField(TextField prenomField) {
		this.prenomField = prenomField;
	}

	public TextField getAdresseField() {
		return adresseField;
	}

	public void setAdresseField(TextField adresseField) {
		this.adresseField = adresseField;
	}

	public TextField getMailField() {
		return mailField;
	}

	public void setMailField(TextField mailField) {
		this.mailField = mailField;
	}

	public TextField getNumTelField() {
		return numTelField;
	}

	public void setNumTelField(TextField numTelField) {
		this.numTelField = numTelField;
	}

	public TextField getLoginField() {
		return loginField;
	}

	public void setLoginField(TextField loginField) {
		this.loginField = loginField;
	}

	public TextField getPwdField() {
		return pwdField;
	}

	public void setPwdField(TextField pwdField) {
		this.pwdField = pwdField;
	}

	public TextField getConfirmPwdField() {
		return ConfirmPwdField;
	}

	public void setConfirmPwdField(TextField confirmPwdField) {
		ConfirmPwdField = confirmPwdField;
	}

	public RadioButton getIsPro() {
		return isPro;
	}

	public void setIsPro(RadioButton isPro) {
		this.isPro = isPro;
	}

	public RadioButton getIsParticulier() {
		return isParticulier;
	}

	public void setIsParticulier(RadioButton isParticulier) {
		this.isParticulier = isParticulier;
	}

	public ComboBox<String> getSpecialites() {
		return specialites;
	}

	public void setSpecialites(ComboBox<String> specialites) {
		this.specialites = specialites;
	}
}
