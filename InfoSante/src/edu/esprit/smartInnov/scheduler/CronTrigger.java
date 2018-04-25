package edu.esprit.smartInnov.scheduler;

import javax.annotation.PostConstruct;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class CronTrigger {

	public static void run() throws Exception{
		JobKey envoiMailRplKey = new JobKey("envoiMailRplKey", "group1");
		JobDetail sendMailRappel = JobBuilder.newJob(EnvoiMailRappelJob.class).withIdentity(envoiMailRplKey).build();
		// test trigger with cron fired every minute
//		Trigger triggerSendMailRpl = TriggerBuilder.newTrigger().withIdentity("triggerSendMailRpl","group1").withSchedule(
//				CronScheduleBuilder.cronSchedule("0 0/1 * * * ?")).build();
		// trigger with cron fired on monday ay 8am
		Trigger triggerSendMailRpl = TriggerBuilder.newTrigger().withIdentity("triggerSendMailRpl","group1").withSchedule(
				CronScheduleBuilder.cronSchedule("0 00 08 ? * MON")).build();
		Scheduler sch = new StdSchedulerFactory().getScheduler();
		sch.start();
		sch.scheduleJob(sendMailRappel,triggerSendMailRpl);
	}
}
