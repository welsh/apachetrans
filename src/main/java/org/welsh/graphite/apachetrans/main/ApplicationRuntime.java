package org.welsh.graphite.apachetrans.main;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.jdbcjobstore.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.welsh.graphite.apachetrans.constants.Config;
import org.welsh.graphite.apachetrans.jobs.ServerJob;
import org.welsh.graphite.apachetrans.util.ConfigUtility;
import org.welsh.graphite.apachetrans.util.InvalidConfigurationException;

public class ApplicationRuntime {

	private static final Logger log = LoggerFactory.getLogger(ApplicationRuntime.class);
	
	public static void main(String[] args) throws SchedulerException, FileNotFoundException, IOException, ParseException, InvalidConfigurationException {
		log.info("Starting apachetrans");
		
		ApplicationRuntime appRuntime = new ApplicationRuntime();
		
		ConfigUtility configUtility = new ConfigUtility();
		Map<String, Object> config = configUtility.getAppSettings();
		log.info("Config: " + config);
		
		appRuntime.startJob(config);
	}
	
	public ApplicationRuntime() {
		
	}
	
	public void startJob(Map<String, Object> context) throws SchedulerException {
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Scheduler scheduler = schedulerFactory.getScheduler();
		
		// define the job and tie it to our HelloJob class
	    JobDetail job = newJob(ServerJob.class)
	    	.withIdentity("apachetransJob", "apachetransGroup")
	    	.build();
	    
	    job.getJobDataMap().putAll(context);
	    Integer executionTime = ((Integer) context.get(Config.EXECUTION_TIME)) * 60;
	    
	    log.info("Execution Time: [{}] Seconds", executionTime);

    	Trigger trigger = newTrigger()
    		.withIdentity("apachetransTrigger", "apachetransGroup")
    		.startNow()
    		.withSchedule(simpleSchedule()
    			.withIntervalInSeconds(executionTime)
    		    .repeatForever())            
    		 .build();

	    // Tell quartz to schedule the job using our trigger
	    scheduler.scheduleJob(job, trigger);
	    
	    scheduler.start();
	}
}
