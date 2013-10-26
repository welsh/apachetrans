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
	
	public ConfigUtility() throws FileNotFoundException, IOException, ParseException, InvalidConfigurationException {
		appSettings = new HashMap<String, Object>();
		
		String baseDir = System.getProperty(Config.SYS_CONF_PROPERTY);
		
		if(baseDir == null) {
			baseDir = Config.DEFAULT_CONF_DIR;
			log.debug("Defaulting to default baseDir.");
		} else {
			log.debug("Using System Property " + Config.SYS_CONF_PROPERTY);
		}
		
		log.debug("baseDir: " + baseDir);
		
		if(!baseDir.endsWith("/")) {
			baseDir += "/";
		}
		
		JSONParser parser = new JSONParser();
		
		Object obj = parser.parse(new FileReader(baseDir + Config.CONF_FILE));
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
			log.error("Error Parsing Integer from '" + Config.GRAPHITE_PORT + "' From " + Config.CONF_FILE);
			log.error("Correct format is \"graphitePort\" : 2003,");
		}
		
		List<ApacheServer> apacheServers = new ArrayList<ApacheServer>();
		
		JSONArray apacheServersJson = (JSONArray) jsonObject.get(Config.APACHE_SERVERS);
		
		if(apacheServersJson != null) {
			Iterator<JSONObject> iterator = apacheServersJson.iterator();
			
			while (iterator.hasNext()) {
				JSONObject apacheServerJson = iterator.next();
				
				ApacheServer apacheServer = new ApacheServer(
						(String) apacheServerJson.get(Config.APACHE_URL), 
						(String) apacheServerJson.get(Config.METRIC_PATH));
				
				apacheServers.add(apacheServer);
			}
			
			appSettings.put(Config.APACHE_SERVERS, apacheServers);
		}
		
		validateConfiguration();
	}
	
	@SuppressWarnings("unchecked")
	private void validateConfiguration() throws InvalidConfigurationException {
		if(appSettings.get(Config.GRAPHITE_HOST) == null) {
			throw new InvalidConfigurationException("Missing '" + Config.GRAPHITE_HOST + "' From " + Config.CONF_FILE);
		}
		
		if(appSettings.get(Config.GRAPHITE_PORT) == null) {
			throw new InvalidConfigurationException("Missing '" + Config.GRAPHITE_PORT + "' From " + Config.CONF_FILE);
		}
		
		if(appSettings.get(Config.APACHE_SERVERS) == null) {
			throw new InvalidConfigurationException("Missing '" + Config.APACHE_SERVERS + "' From " + Config.CONF_FILE);
		}
		
		if(((List<ApacheServer>) appSettings.get(Config.APACHE_SERVERS)).isEmpty()) {
			throw new InvalidConfigurationException("Missing '" + Config.APACHE_SERVERS + "' From " + Config.CONF_FILE);
		}
	}

	public Map<String, Object> getAppSettings() {
		return appSettings;
	}

	public void setAppSettings(Map<String, Object> appSettings) {
		this.appSettings = appSettings;
	}
}
