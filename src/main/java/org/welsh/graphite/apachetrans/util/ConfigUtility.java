package org.welsh.graphite.apachetrans.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.welsh.graphite.apachetrans.constants.Config;
import org.welsh.graphite.apachetrans.domain.ApacheServer;

public class ConfigUtility {
	
	private static Logger log = LoggerFactory.getLogger(ConfigUtility.class);

	private Map<String, Object> appSettings; 
	private String confFile;
	
	public ConfigUtility() {
		confFile = determineFolderPath() + determineFileName();
	}
	
	public ConfigUtility(String confFile) {
		this.confFile = confFile;
	}
	
	@SuppressWarnings("unchecked")
	public void loadConfiguration() throws InvalidConfigurationException, FileNotFoundException, IOException, ParseException {
		appSettings = new HashMap<String, Object>();
		
		log.debug("Conf File: " + confFile);
		
		JSONParser parser = new JSONParser();
		
		Object obj = parser.parse(new FileReader(confFile));
		JSONObject jsonObject = (JSONObject) obj;
		
		Long executionTime = (Long) jsonObject.get(Config.EXECUTION_TIME);
		if(executionTime == null) {
			log.debug("Execution time not found. Defaulting to [{}]", Config.EXECUTION_TIME);
			executionTime = Config.DEFAULT_EXECUTION_TIME;
		}

		appSettings.put(Config.EXECUTION_TIME, executionTime.intValue());
		appSettings.put(Config.GRAPHITE_HOST, (String) jsonObject.get(Config.GRAPHITE_HOST));
		
		try {
			Long graphitePort = (Long) jsonObject.get(Config.GRAPHITE_PORT);
			if(graphitePort != null) {
				appSettings.put(Config.GRAPHITE_PORT, graphitePort.intValue());
			}
		} catch (Exception e ) {
			log.error("Error Parsing Integer from '" + Config.GRAPHITE_PORT + "' From " + confFile);
			log.error("Correct format is \"graphitePort\" : 2003,");
		}
		
		List<ApacheServer> apacheServers = new ArrayList<ApacheServer>();
		
		JSONArray apacheServersJson = (JSONArray) jsonObject.get(Config.APACHE_SERVERS);
		
		if(apacheServersJson != null) {
			Iterator<JSONObject> iterator = apacheServersJson.iterator();
			
			while (iterator.hasNext()) {
				apacheServers.add(parseApacheServer(iterator.next()));
			}
			
			appSettings.put(Config.APACHE_SERVERS, apacheServers);
		}
		
		validateConfiguration(confFile);
	}
	
	public String determineFolderPath() {
		String baseDir = System.getProperty(Config.SYS_CONF_DIR_PROPERTY);
		
		if(baseDir == null || baseDir.trim().isEmpty()) {
			baseDir = Config.DEFAULT_CONF_DIR;
			log.debug("Defaulting to default baseDir.");
		} else {
			log.debug("Using System Property " + Config.SYS_CONF_DIR_PROPERTY);
		}

		if(!baseDir.endsWith("/")) {
			baseDir += "/";
		}
		
		return baseDir;
	}
	
	public String determineFileName() {
		String fileName = System.getProperty(Config.SYS_CONF_FILE_PROPERTY);
		
		if(fileName == null || fileName.trim().isEmpty()) {
			fileName = Config.DEFAULT_CONF_FILE;
			log.debug("Defaulting to default confFile.");
		} else {
			log.debug("Using System Property " + Config.SYS_CONF_FILE_PROPERTY);
		}
		
		return fileName;
	}
	
	private ApacheServer parseApacheServer(JSONObject apacheServerJson) {
		ApacheServer apacheServer = new ApacheServer(
				(String) apacheServerJson.get(Config.APACHE_URL), 
				(String) apacheServerJson.get(Config.METRIC_PATH));
		
		// By Default, we pass all values as true.
		// So they all get set to true by default
		apacheServer.setAllReportingValues(true);
		
		Object tmp = apacheServerJson.get(Config.TOTAL_ACCESSES);
		if(tmp != null) {
			apacheServer.setReportTotalAccesses((Boolean) tmp);
		}
		
		tmp = apacheServerJson.get(Config.TOTAL_KBYTES);
		if(tmp != null) {
			apacheServer.setReportTotalkBytes((Boolean) tmp);
		}
		
		tmp = apacheServerJson.get(Config.CPU_LOAD);
		if(tmp != null) {
			apacheServer.setReportCpuLoad((Boolean) tmp);
		}
		
		tmp = apacheServerJson.get(Config.UPTIME);
		if(tmp != null) {
			apacheServer.setReportUptime((Boolean) tmp);
		}
		
		tmp = apacheServerJson.get(Config.REQ_PER_SEC);
		if(tmp != null) {
			apacheServer.setReportReqPerSec((Boolean) tmp);
		}
		
		tmp = apacheServerJson.get(Config.BYTES_PER_SEC);
		if(tmp != null) {
			apacheServer.setReportBytesPerSec((Boolean) tmp);
		}
		
		tmp = apacheServerJson.get(Config.BYTES_PER_REQ);
		if(tmp != null) {
			apacheServer.setReportBytesPerReq((Boolean) tmp);
		}
		
		tmp = apacheServerJson.get(Config.BUSY_WORKERS);
		if(tmp != null) {
			apacheServer.setReportBusyWorkers((Boolean) tmp);
		}
		
		tmp = apacheServerJson.get(Config.IDLE_WORKERS);
		if(tmp != null) {
			apacheServer.setReportIdleWorkers((Boolean) tmp);
		}
		
		return apacheServer;
	}
	
	@SuppressWarnings("unchecked")
	private void validateConfiguration(String confFile) throws InvalidConfigurationException {
		if(appSettings.get(Config.GRAPHITE_HOST) == null) {
			throw new InvalidConfigurationException("Missing '" + Config.GRAPHITE_HOST + "' From " + confFile);
		}
		
		if(appSettings.get(Config.GRAPHITE_PORT) == null) {
			throw new InvalidConfigurationException("Missing '" + Config.GRAPHITE_PORT + "' From " + confFile);
		}
		
		if(appSettings.get(Config.APACHE_SERVERS) == null) {
			throw new InvalidConfigurationException("Missing '" + Config.APACHE_SERVERS + "' From " + confFile);
		}
		
		if(((List<ApacheServer>) appSettings.get(Config.APACHE_SERVERS)).isEmpty()) {
			throw new InvalidConfigurationException("Missing '" + Config.APACHE_SERVERS + "' From " + confFile);
		}
	}

	public Map<String, Object> getAppSettings() {
		return appSettings;
	}

	public void setAppSettings(Map<String, Object> appSettings) {
		this.appSettings = appSettings;
	}

	public String getConfFile() {
		return confFile;
	}

	public void setConfFile(String confFile) {
		this.confFile = confFile;
	}
}
