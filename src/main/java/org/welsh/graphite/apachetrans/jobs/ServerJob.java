package org.welsh.graphite.apachetrans.jobs;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.welsh.graphite.apachetrans.constants.Config;
import org.welsh.graphite.apachetrans.domain.ApacheStatus;
import org.welsh.graphite.apachetrans.util.ApacheConnector;
import org.welsh.graphite.apachetrans.util.GraphiteConnector;

public class ServerJob implements Job {
	
	private static final Logger log = LoggerFactory.getLogger(ServerJob.class);

	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		log.info("Starting Execution");
		
		try {
			JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
			String metricPath = (String) jobDataMap.get(Config.METRIC_PATH);
			String apacheUrl = (String) jobDataMap.get(Config.APACHE_URL);
			
			String graphiteHost = (String) jobDataMap.get(Config.GRAPHITE_HOST);
			Integer graphitePort = (Integer) jobDataMap.get(Config.GRAPHITE_PORT);
			
			log.info("Getting Apache Info From: " + apacheUrl);
			ApacheConnector apacheConnector = new ApacheConnector(apacheUrl);
			ApacheStatus apacheStatus = apacheConnector.getApacheStatus();
			
			log.info("Sending To: " + graphiteHost + ":" + graphitePort + "/" + metricPath);
			GraphiteConnector graphiteConnector = new GraphiteConnector(graphiteHost, graphitePort);
			String message = graphiteConnector.createGraphiteMessage(apacheStatus, metricPath);
			graphiteConnector.logToGraphite(message);
			
		} catch (Exception e) {
			log.error("Job Execution Failed. Stacktrace to follow.", e);
		}
		
		log.info("Finished Execution");
	}

}
