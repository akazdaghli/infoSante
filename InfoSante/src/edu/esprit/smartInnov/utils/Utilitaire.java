package edu.esprit.smartInnov.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class Utilitaire {

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);
	public static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$";

	public static String hashMD5Crypt(String input) throws NoSuchAlgorithmException {
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.reset();
		m.update(input.getBytes());
		byte[] digest = m.digest();
		BigInteger bigInt = new BigInteger(1, digest);
		String hashtext = bigInt.toString(16);
		while (hashtext.length() < 32) {
			hashtext = "0" + hashtext;
		}
		return hashtext;
	}

	public static Date getDateFromLocalDate(LocalDate localDate) {
		return Date.valueOf(localDate);
	}
	
	public static Date getSqlDateFromUtilDate(java.util.Date d) {
		return new Date(d.getTime());
	}

	public static InputStream importerImage(Control imgName, boolean updateLabel) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Importer image");
		Utilitaire.setExtFiltersToImg(fileChooser);
		File img = fileChooser.showOpenDialog(imgName.getScene().getWindow());
		if (img != null) {
			if (updateLabel) {
				if (imgName instanceof Label) {
					((Label) imgName).setText(img.getName());
				}
				if (imgName instanceof TextField) {
					((TextField) imgName).setText(img.getName());
				}
			}
			try {
				BufferedImage bufferedImage = ImageIO.read(img);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(bufferedImage, "png", baos);
				return new ByteArrayInputStream(baos.toByteArray());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void setExtFiltersToImg(FileChooser fc) {
		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Toutes les photos", "*.*"),
				new FileChooser.ExtensionFilter("PNG", "*.png"),
				new FileChooser.ExtensionFilter("JPG", "*.jpg"),
				new FileChooser.ExtensionFilter("BMP", "*.bmp"),
				new FileChooser.ExtensionFilter("JPEG", "*.jpeg"));
	}

	public static boolean verifyHashedPassword(String passwordHashedBd, String passwordHashedSaisi) {
		return MessageDigest.isEqual(passwordHashedBd.getBytes(), passwordHashedSaisi.getBytes());
	}

	public static boolean isValidName(String name) {
		String pattern = "^[a-zA-Z\\s]+";
		return name.matches(pattern);
	}

	public static boolean isValidMail(String mail) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(mail);
		return matcher.find();
	}

	public static boolean isValidTel(String numTel) {
		return numTel != null && !numTel.isEmpty() && numTel.length() == 8;
	}

	public static boolean checkPassword(String pwd) {
		return pwd != null && !pwd.isEmpty() && pwd.length() >= 8;
	}

	public static boolean checkConfirmPassword(String pwd1, String pwd2) {
		return pwd1.equals(pwd2);
	}
}
