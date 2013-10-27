package org.welsh.graphite.apachetrans.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.welsh.graphite.apachetrans.constants.ServerStatus;
import org.welsh.graphite.apachetrans.domain.ApacheStatus;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ApacheConnector {
	
	private static Logger log = LoggerFactory.getLogger(ApacheConnector.class);
	private String url;
	
	public ApacheConnector(String url) {
		this.url = url;
	}
	
	public ApacheStatus getApacheStatus() {
		log.debug("Getting Status for Page: " + url);
		
		ApacheStatus apacheStatus = new ApacheStatus();

		Map<String, String> parsed = parsePage();
		
		apacheStatus.setTotalAccesses(parsed.get(ServerStatus.TOTAL_ACCESSES));
		apacheStatus.setTotalkBytes(parsed.get(ServerStatus.TOTAL_KBYTES));
		
		apacheStatus.setCpuLoad(parsed.get(ServerStatus.CPU_LOAD));
		apacheStatus.setUptime(parsed.get(ServerStatus.UPTIME));
		
		apacheStatus.setReqPerSec(parsed.get(ServerStatus.REQ_PER_SEC));
		apacheStatus.setBytesPerSec(parsed.get(ServerStatus.BYTES_PER_SEC));
		apacheStatus.setBytesPerReq(parsed.get(ServerStatus.BYTES_PER_REQ));
		
		apacheStatus.setBusyWorkers(parsed.get(ServerStatus.BUSY_WORKERS));
		apacheStatus.setIdleWorkers(parsed.get(ServerStatus.IDLE_WORKERS));
		
		log.info(apacheStatus.toString());
		
		return apacheStatus;
	}
	
	private Map<String, String> parsePage()  {
		Map<String, String> results = new HashMap<String, String>();
		
		Client client = Client.create();
		WebResource webResource = client.resource(url);
		
		try {
			ClientResponse clientResponse = webResource.get(ClientResponse.class);
			String response = clientResponse.getEntity(String.class);
			
			if(clientResponse.getStatus() == 200) {		
				log.debug("Response: \n" + response);
				
				String[] responseLines = response.split("\n");
				
				for (String line : responseLines) {
					String[] keyValue = line.split(":");
					
					if(keyValue.length == 2) {
						results.put(keyValue[0].trim(), keyValue[1].trim());
					}
				}
			} else {
				log.error("None 200 Response Code Recevied. Received [{}]", clientResponse.getStatus());
				log.error("Response: \n" + response);
			}
		} catch (ClientHandlerException e) {
			log.error(e.getLocalizedMessage());
		}
		
		return results;
	}

	public String getUrl() {
		return url;
	}
}
