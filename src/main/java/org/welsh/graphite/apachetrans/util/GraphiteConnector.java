package org.welsh.graphite.apachetrans.util;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ConnectException;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.welsh.graphite.apachetrans.domain.ApacheStatus;

public class GraphiteConnector {
	
	private static Logger log = LoggerFactory.getLogger(GraphiteConnector.class);
	
	private String graphiteHost;
	private int graphitePort;
	
	public GraphiteConnector(String graphiteHost, int graphitePort) {
		this.graphiteHost = graphiteHost;
		this.graphitePort = graphitePort;
	}

	public void logToGraphite(ApacheStatus apacheStatus, String metricPath) throws Exception {
		log.debug("Sending Message To: {}:{}", graphiteHost, graphitePort);
		String msg = createGraphiteMessage(apacheStatus, metricPath);
		
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
		} catch (ConnectException e) {
			log.error(e.getLocalizedMessage());
		}
	}

	private String createGraphiteMessage(ApacheStatus apacheStatus, String metricPath) {
		long epoch = System.currentTimeMillis()/1000;
		
		StringBuilder message = new StringBuilder();
		if(apacheStatus.getTotalAccesses() != null) {
			message.append(metricPath + ".apache.totalAccesses " + apacheStatus.getTotalAccesses());
			message.append(" " + epoch + "\n");
		}
		
		if(apacheStatus.getTotalkBytes() != null) {
			message.append(metricPath + ".apache.totalkBytes " + apacheStatus.getTotalkBytes());
			message.append(" " + epoch + "\n");
		}
		
		if(apacheStatus.getUptime() != null) {
			message.append(metricPath + ".apache.uptime " + apacheStatus.getUptime());
			message.append(" " + epoch + "\n");
		}
		
		if(apacheStatus.getCpuLoad() != null) {
			message.append(metricPath + ".apache.cpuLoad " + apacheStatus.getCpuLoad());
			message.append(" " + epoch + "\n");
		}
		
		if(apacheStatus.getReqPerSec() != null) {
			message.append(metricPath + ".apache.reqPerSec " + apacheStatus.getReqPerSec());
			message.append(" " + epoch + "\n");
		}
		
		if(apacheStatus.getBytesPerSec() != null) {
			message.append(metricPath + ".apache.bytesPerSec " + apacheStatus.getBytesPerSec());
			message.append(" " + epoch + "\n");
		}
		
		if(apacheStatus.getBytesPerReq() != null) {
			message.append(metricPath + ".apache.bytesPerReq " + apacheStatus.getBytesPerReq());
			message.append(" " + epoch + "\n");
		}
		
		if(apacheStatus.getBusyWorkers() != null) {
			message.append(metricPath + ".apache.busyWorkers " + apacheStatus.getBusyWorkers());
			message.append(" " + epoch + "\n");
		}
		
		if(apacheStatus.getIdleWorkers() != null) {
			message.append(metricPath + ".apache.idleWorkers " + apacheStatus.getIdleWorkers());
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
