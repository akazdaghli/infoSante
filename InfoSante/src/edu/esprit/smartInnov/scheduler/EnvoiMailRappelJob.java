package edu.esprit.smartInnov.scheduler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import edu.esprit.smartInnov.entites.ProSante;
import edu.esprit.smartInnov.entites.RendezVous;
import edu.esprit.smartInnov.entites.Utilisateur;
import edu.esprit.smartInnov.services.RendezVousService;
import edu.esprit.smartInnov.services.UtilisateurService;
import edu.esprit.smartInnov.utils.EnvoiMailUtil;

public class EnvoiMailRappelJob implements Job {

	private static final Logger LOGGER = Logger.getLogger(EnvoiMailRappelJob.class.getName());

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		LOGGER.info("RUNNING ENVOIE MAIL HEBDOMADAIRE RAPPEL RENDEZ VOUS JOB!! :: "+new Date());
		UtilisateurService utilisateurService = new UtilisateurService();
		RendezVousService rvService = new RendezVousService();
		List<Utilisateur> proSantes = utilisateurService.getMedecins();
		List<RendezVous> rendezvous = new ArrayList<>();
		StringBuilder mailContent = new StringBuilder(
				"InfoSanté vous rappelle que votre emploie de temps pour cette semaine est comme  suit:");
		for (Utilisateur p : proSantes) {
			rendezvous = rvService.getListRendezVousByProfSante((ProSante) p);
			if (rendezvous != null && !rendezvous.isEmpty()) {
				for (RendezVous rv : rendezvous) {
					mailContent.append("\n ");
					mailContent.append("Le " + rv.getDateRendezVs() + " : Patient : " + rv.getPatient().getNom()
							+ " " + rv.getPatient().getPrenom());
				}
				try {
					EnvoiMailUtil.envoiMail(p.getMail(), "Rappel Hebdomadaire", "Emploie de temps de la semaine de "
							+ new SimpleDateFormat("dd/MM/yyyy").format(new Date()), mailContent.toString());
				} catch (MessagingException e) {
					LOGGER.log(Level.SEVERE,"Problem sending mails");
					LOGGER.log(Level.SEVERE,e.getMessage());
				}
			}
		}
	}

}
