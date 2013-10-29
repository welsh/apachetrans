package org.welsh.graphite.apachetrans.util;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ConnectException;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.welsh.graphite.apachetrans.constants.Metric;
import org.welsh.graphite.apachetrans.domain.ApacheServer;
import org.welsh.graphite.apachetrans.domain.ApacheStatus;

public class GraphiteConnector {
	
	private static Logger log = LoggerFactory.getLogger(GraphiteConnector.class);
	
	private String graphiteHost;
	private int graphitePort;
	
	public GraphiteConnector(String graphiteHost, int graphitePort) {
		this.graphiteHost = graphiteHost;
		this.graphitePort = graphitePort;
	}

	public void logToGraphite(ApacheStatus apacheStatus, ApacheServer apacheServer) throws Exception {
		log.debug("Sending Message To: {}:{}", graphiteHost, graphitePort);
		String msg = createGraphiteMessage(apacheStatus, apacheServer);
		
		log.info("Sending Data Points: " + apacheServer.datapointsToSend());
		
		if(!msg.isEmpty()) {
			try {
				Socket socket = new Socket(graphiteHost, graphitePort); 
				
				try {
					Writer writer = new OutputStreamWriter(socket.getOutputStream());
					writer.write(msg);
					writer.flush();
					writer.close();
				} finally {
					socket.close();
				}
				
				log.info("Message Sent.");
			} catch (ConnectException e) {
				log.error(e.getLocalizedMessage());
			}
		} else {
			log.warn("Message Empty. Not Sending.");
		}
	}

	public String createGraphiteMessage(ApacheStatus apacheStatus, ApacheServer apacheServer) {
		long epoch = System.currentTimeMillis()/1000;
		String metricPath = apacheServer.getMetricPath();
		
		StringBuilder message = new StringBuilder();
		if(apacheStatus.getTotalAccesses() != null && apacheServer.isReportTotalAccesses()) {
			message.append(metricPath + Metric.APACHE_TOTAL_ACCESSES + " " + apacheStatus.getTotalAccesses());
			message.append(" " + epoch + "\n");
		}
		
		if(apacheStatus.getTotalkBytes() != null && apacheServer.isReportTotalkBytes()) {
			message.append(metricPath + Metric.APACHE_TOTAL_KBYTES + " " + apacheStatus.getTotalkBytes());
			message.append(" " + epoch + "\n");
		}
		
		if(apacheStatus.getUptime() != null && apacheServer.isReportUptime()) {
			message.append(metricPath + Metric.APACHE_UPTIME + " " + apacheStatus.getUptime());
			message.append(" " + epoch + "\n");
		}
		
		if(apacheStatus.getCpuLoad() != null && apacheServer.isReportCpuLoad()) {
			message.append(metricPath + Metric.APACHE_CPU_LOAD + " " + apacheStatus.getCpuLoad());
			message.append(" " + epoch + "\n");
		}
		
		if(apacheStatus.getReqPerSec() != null && apacheServer.isReportReqPerSec()) {
			message.append(metricPath + Metric.APACHE_REQ_PER_SEC + " " + apacheStatus.getReqPerSec());
			message.append(" " + epoch + "\n");
		}
		
		if(apacheStatus.getBytesPerSec() != null && apacheServer.isReportBytesPerSec()) {
			message.append(metricPath + Metric.APACHE_BYTES_PER_SEC + " " + apacheStatus.getBytesPerSec());
			message.append(" " + epoch + "\n");
		}
		
		if(apacheStatus.getBytesPerReq() != null && apacheServer.isReportBytesPerReq()) {
			message.append(metricPath + Metric.APACHE_BYTES_PER_REQ + " " + apacheStatus.getBytesPerReq());
			message.append(" " + epoch + "\n");
		}
		
		if(apacheStatus.getBusyWorkers() != null && apacheServer.isReportBusyWorkers()) {
			message.append(metricPath + Metric.APACHE_BUSY_WORKERS + " " + apacheStatus.getBusyWorkers());
			message.append(" " + epoch + "\n");
		}
		
		if(apacheStatus.getIdleWorkers() != null && apacheServer.isReportIdleWorkers()) {
			message.append(metricPath + Metric.APACHE_IDLE_WORKERS + " " + apacheStatus.getIdleWorkers());
			message.append(" " + epoch + "\n");
		}
		
		log.debug("Message: " + message.toString());

		return message.toString();
	}

	public String getGraphiteHost() {
		return graphiteHost;
	}

	public int getGraphitePort() {
		return graphitePort;
	}
}
