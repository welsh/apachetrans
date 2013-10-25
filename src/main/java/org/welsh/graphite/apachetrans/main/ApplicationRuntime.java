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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.welsh.graphite.apachetrans.constants.Config;
import org.welsh.graphite.apachetrans.jobs.ServerJob;

public class ApplicationRuntime {

	private static final Logger log = LoggerFactory.getLogger(ApplicationRuntime.class);
	
	public static void main(String[] args) throws SchedulerException, FileNotFoundException, IOException, ParseException {
		log.info("Starting apachetrans");
		
		ApplicationRuntime appRuntime = new ApplicationRuntime();
		
		Map<String, Object> config = appRuntime.readConfig();
		log.info("Config: " + config);
		
		appRuntime.startJob(config);
	}
	
	public ApplicationRuntime() {
		
	}
	
	public Map<String, Object> readConfig() throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader("/etc/apachetrans/application.conf"));
		 
		JSONObject jsonObject = (JSONObject) obj;
		
		Map<String, Object> context = new HashMap<String, Object>();
		
		context.put(Config.EXECUTION_TIME, ((Long) jsonObject.get(Config.EXECUTION_TIME)).intValue());
		context.put(Config.METRIC_PATH, (String) jsonObject.get(Config.METRIC_PATH));
		context.put(Config.APACHE_URL, (String) jsonObject.get(Config.APACHE_URL));
		context.put(Config.GRAPHITE_HOST, (String) jsonObject.get(Config.GRAPHITE_HOST));
		context.put(Config.GRAPHITE_PORT, ((Long) jsonObject.get(Config.GRAPHITE_PORT)).intValue());

		return context;
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
