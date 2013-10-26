package org.welsh.graphite.apachetrans.constants;

public class Config {
	public static final String EXECUTION_TIME = "executionTime";
	public static final String APACHE_SERVERS = "apacheServers";
	public static final String METRIC_PATH = "metricPath";
	public static final String APACHE_URL = "apacheUrl";
	
	public static final String GRAPHITE_HOST = "graphiteHost";
	public static final String GRAPHITE_PORT = "graphitePort";
	
	public static final String SYS_CONF_PROPERTY = "apachetrans.conf.dir";
	public static final String SYS_LOG_PROPERTY = "apachetrans.log.dir";
	
	public static final String DEFAULT_LOG_DIR = "/var/log/apachetrans";
	public static final String DEFAULT_CONF_DIR = "/etc/apachetrans";
	public static final Long   DEFAULT_EXECUTION_TIME = 5L;
	
	public static final String CONF_FILE = "application.conf";
}
             