package org.welsh.graphite.apachetrans.jobs;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.welsh.graphite.apachetrans.constants.Config;
import org.welsh.graphite.apachetrans.domain.ApacheServer;
import org.welsh.graphite.apachetrans.domain.ApacheStatus;
import org.welsh.graphite.apachetrans.util.ApacheConnector;
import org.welsh.graphite.apachetrans.util.GraphiteConnector;

public class ServerJob implements Job {
	
	private static final Logger log = LoggerFactory.getLogger(ServerJob.class);

	@SuppressWarnings("unchecked")
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		log.info("Starting Execution");
		
		try {
			JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
			List<ApacheServer> apacheServers = (List<ApacheServer>) jobDataMap.get(Config.APACHE_SERVERS);
			
			String graphiteHost = (String) jobDataMap.get(Config.GRAPHITE_HOST);
			Integer graphitePort = (Integer) jobDataMap.get(Config.GRAPHITE_PORT);
			
			for(ApacheServer apacheServer : apacheServers) {
				String apacheUrl = apacheServer.getApacheUrl();
				
				log.info("Getting Apache Info From: " + apacheUrl);
				ApacheConnector apacheConnector = new ApacheConnector(apacheUrl);
				ApacheStatus apacheStatus = apacheConnector.getApacheStatus();
				
				log.info("Sending To: " + graphiteHost + ":" + graphitePort + "/" + apacheServer.getMetricPath());
				GraphiteConnector graphiteConnector = new GraphiteConnector(graphiteHost, graphitePort);
				graphiteConnector.logToGraphite(apacheStatus, apacheServer);
			}
			
		} catch (Exception e) {
			log.error("Job Execution Failed. Stacktrace to follow.", e);
		}
		
		log.info("Finished Execution");
	}

}
