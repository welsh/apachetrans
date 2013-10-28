package org.welsh.graphite.apachetrans.constants;

public class Config {
	/**
	 * Config File Values
	 */
	public static final String EXECUTION_TIME = "executionTime";
	public static final String APACHE_SERVERS = "apacheServers";
	public static final String METRIC_PATH = "metricPath";
	public static final String APACHE_URL = "apacheUrl";
	
	public static final String GRAPHITE_HOST = "graphiteHost";
	public static final String GRAPHITE_PORT = "graphitePort";
	
	public static final String TOTAL_ACCESSES = "totalAccesses";
	public static final String TOTAL_KBYTES = "totalkBytes";
	public static final String UPTIME = "uptime";
	public static final String CPU_LOAD = "cpuLoad";
	public static final String REQ_PER_SEC = "reqPerSec";
	public static final String BYTES_PER_SEC = "bytesPerSec";
	public static final String BYTES_PER_REQ = "bytesPerReq";
	public static final String BUSY_WORKERS = "busyWorkers";
	public static final String IDLE_WORKERS = "idleWorkers";
	
	/**
	 * System Properties 
	 */
	public static final String SYS_CONF_DIR_PROPERTY = "apachetrans.conf.dir";
	public static final String SYS_CONF_FILE_PROPERTY = "apachetrans.conf.file";
	
	
	/**
	 * Defaults
	 */
	public static final String DEFAULT_CONF_DIR = "/etc/apachetrans";
	public static final String DEFAULT_CONF_FILE = "application.conf";
	public static final Long   DEFAULT_EXECUTION_TIME = 5L;
}
             