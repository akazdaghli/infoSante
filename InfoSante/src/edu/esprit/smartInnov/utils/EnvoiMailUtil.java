package edu.esprit.smartInnov.utils;


import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


/**
 * The Class EnvoiMailUtil.
 *
 * @author Chahir AMMARI
 */

public final class EnvoiMailUtil {

	private static final String MAIL_TRANSPORT_PROTOCOL = "smtp";

	private static final String MAIL_HOST = "smtp.gmail.com";

	private static final String MAIL_SMTP_STARTTLS_ENABLE = "true";

	private static final String MAIL_SMTP_AUTH = "true";

	private static final String MAIL_DEBUG = "true";

	private static final String USERNAME = "kazdaghli46@gmail.com";

	private static final String PASSWORD = "AymenKazdaghli965";
	public static final void envoiMail(String destinataire, String titreMail, String titreContentMail, String bodyContentMail)
			throws MessagingException {

		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", MAIL_TRANSPORT_PROTOCOL);
		props.setProperty("mail.host", MAIL_HOST);
		props.put("mail.smtp.starttls.enable", MAIL_SMTP_STARTTLS_ENABLE);
		props.put("mail.smtp.auth", MAIL_SMTP_AUTH);
		props.put("mail.debug", MAIL_DEBUG);

		Session mailSession = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		});


		MimeMultipart multipart = new MimeMultipart("related");

		BodyPart messageBodyPart = new MimeBodyPart();

		String templateMail1 = "<p>"+titreContentMail+"</p><p>"+bodyContentMail;

		messageBodyPart.setContent(templateMail1, "text/html; charset=utf-8");
		multipart.addBodyPart(messageBodyPart);

		MimeMessage message = new MimeMessage(mailSession);
		message.setContent(multipart);
		message.setSubject(titreMail);

		message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinataire));

		Transport transport = mailSession.getTransport();
		transport.connect();
		transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
		transport.close();

	}

}

