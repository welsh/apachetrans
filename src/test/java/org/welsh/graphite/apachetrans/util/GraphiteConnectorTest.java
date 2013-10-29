package org.welsh.graphite.apachetrans.util;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.welsh.graphite.apachetrans.constants.Metric;
import org.welsh.graphite.apachetrans.domain.ApacheServer;
import org.welsh.graphite.apachetrans.domain.ApacheStatus;
import org.welsh.graphite.apachetrans.mock.MockGraphiteServerFactory;

import com.sun.net.httpserver.HttpServer;

public class GraphiteConnectorTest {
	
	private static Logger log = LoggerFactory.getLogger(GraphiteConnectorTest.class);

	private final static String GRAPHITE_HOST = "127.0.0.1";
	private final static String GRAPHITE_METRIC = "nci.prod.app01";
	private final static int GRAPHITE_PORT_ONLINE = 2003;
	private final static int GRAPHITE_PORT_OFFLINE = 10000;
	
	private static ApacheStatus apacheStatus; 
	private static ApacheServer apacheServer;
	private static HttpServer graphiteServer;
	
	@BeforeClass
	public static void beforeClass() throws IOException {
		apacheStatus = new ApacheStatus("215", "1133", "3207", ".00498909", ".0670408", "361.769", "5396.24", "1", "7");
		apacheServer = new ApacheServer(GRAPHITE_HOST, GRAPHITE_METRIC);
		
		graphiteServer = MockGraphiteServerFactory.createHttpServer(GRAPHITE_PORT_ONLINE);
		graphiteServer.start();
	}

	@AfterClass
	public static void afterClass() throws InterruptedException {
		graphiteServer.stop(0);
	}
	
	@Test
	public void testCreateGraphiteMessageAllValues() {
		log.info("STARTING: testCreateGraphiteMessageAllValues()");
		
		GraphiteConnector graphiteConnector = new GraphiteConnector(GRAPHITE_HOST, GRAPHITE_PORT_ONLINE);
		apacheServer.setAllReportingValues(true);
		
		String message = graphiteConnector.createGraphiteMessage(apacheStatus, apacheServer);
		
		assertNotNull(message);
		
		assertTrue(message.contains(Metric.APACHE_TOTAL_ACCESSES));
		assertTrue(message.contains(Metric.APACHE_TOTAL_KBYTES));
		assertTrue(message.contains(Metric.APACHE_UPTIME));
		assertTrue(message.contains(Metric.APACHE_CPU_LOAD));
		assertTrue(message.contains(Metric.APACHE_REQ_PER_SEC));
		assertTrue(message.contains(Metric.APACHE_BYTES_PER_SEC));
		assertTrue(message.contains(Metric.APACHE_BYTES_PER_REQ));
		assertTrue(message.contains(Metric.APACHE_BUSY_WORKERS));
		assertTrue(message.contains(Metric.APACHE_IDLE_WORKERS));

		log.info("FINISHED: testCreateGraphiteMessageAllValues()");
	}
	
	@Test
	public void testCreateGraphiteMessageNoValues() {
		log.info("STARTING: testCreateGraphiteMessageNoValues()");
		
		GraphiteConnector graphiteConnector = new GraphiteConnector(GRAPHITE_HOST, GRAPHITE_PORT_ONLINE);
		apacheServer.setAllReportingValues(false);

		String message = graphiteConnector.createGraphiteMessage(apacheStatus, apacheServer);
		
		assertNotNull(message);
		
		assertFalse(message.contains(Metric.APACHE_TOTAL_ACCESSES));
		assertFalse(message.contains(Metric.APACHE_TOTAL_KBYTES));
		assertFalse(message.contains(Metric.APACHE_UPTIME));
		assertFalse(message.contains(Metric.APACHE_CPU_LOAD));
		assertFalse(message.contains(Metric.APACHE_REQ_PER_SEC));
		assertFalse(message.contains(Metric.APACHE_BYTES_PER_SEC));
		assertFalse(message.contains(Metric.APACHE_BYTES_PER_REQ));
		assertFalse(message.contains(Metric.APACHE_BUSY_WORKERS));
		assertFalse(message.contains(Metric.APACHE_IDLE_WORKERS));
		
		log.info("FINISHED: testCreateGraphiteMessageNoValues()");
	}
	
	@Test
	public void testCreateGraphiteMessageSomeValues() {
		log.info("STARTING: testCreateGraphiteMessageSomeValues()");
		
		GraphiteConnector graphiteConnector = new GraphiteConnector(GRAPHITE_HOST, GRAPHITE_PORT_ONLINE);
		apacheServer.setReportBusyWorkers(true);
		apacheServer.setReportIdleWorkers(true);
		apacheServer.setReportBytesPerSec(true);
		apacheServer.setReportReqPerSec(true);
		apacheServer.setReportCpuLoad(true);

		String message = graphiteConnector.createGraphiteMessage(apacheStatus, apacheServer);
		
		assertNotNull(message);
		
		assertFalse(message.contains(Metric.APACHE_TOTAL_ACCESSES));
		assertFalse(message.contains(Metric.APACHE_TOTAL_KBYTES));
		assertFalse(message.contains(Metric.APACHE_UPTIME));
		assertFalse(message.contains(Metric.APACHE_BYTES_PER_REQ));
		
		assertTrue(message.contains(Metric.APACHE_CPU_LOAD));
		assertTrue(message.contains(Metric.APACHE_BYTES_PER_SEC));
		assertTrue(message.contains(Metric.APACHE_REQ_PER_SEC));
		assertTrue(message.contains(Metric.APACHE_BUSY_WORKERS));
		assertTrue(message.contains(Metric.APACHE_IDLE_WORKERS));
		
		log.info("FINISHED: testCreateGraphiteMessageSomeValues()");
	}
	
	@Test
	public void testLogToGraphiteOnline() {
		log.info("STARTING: testLogToGraphiteOnline()");
		
		GraphiteConnector graphiteConnector = new GraphiteConnector(GRAPHITE_HOST, GRAPHITE_PORT_ONLINE);
		apacheServer.setAllReportingValues(true);

		try {
			graphiteConnector.logToGraphite(apacheStatus, apacheServer);
			
			assertTrue(true);
		} catch (Exception e) {
			log.error("Error Pushing to Graphite Host [{}:{}]", graphiteConnector.getGraphiteHost(),  graphiteConnector.getGraphitePort());
			log.error("Stacktrace:", e);
			fail();
		}
		
		log.info("FINISHED: testLogToGraphiteOnline()");
	}
	
	@Test
	public void testLogToGraphiteOffline() {
		log.info("STARTING: testLogToGraphiteOffline()");
		
		GraphiteConnector graphiteConnector = new GraphiteConnector(GRAPHITE_HOST, GRAPHITE_PORT_OFFLINE);

		try {
			graphiteConnector.logToGraphite(apacheStatus, apacheServer);
			
			assertTrue(true);
		} catch (Exception e) {
			log.error("Error Pushing to Graphite Host [{}:{}]", graphiteConnector.getGraphiteHost(),  graphiteConnector.getGraphitePort());
			log.error("Stacktrace:", e);
			fail();
		}
		
		log.info("FINISHED: testLogToGraphiteOffline()");
	}
}
