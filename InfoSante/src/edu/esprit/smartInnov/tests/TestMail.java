package edu.esprit.smartInnov.tests;

import javax.mail.MessagingException;

import edu.esprit.smartInnov.utils.EnvoiMailUtil;

public class TestMail {

	public static void main(String[] args) {
		try {
			EnvoiMailUtil.envoiMail("akazdoghli@adaming.fr", "Confirmation inscription", "Info Santé", "Veuillez nous fournir vos documents pour vérifier votre profession");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
