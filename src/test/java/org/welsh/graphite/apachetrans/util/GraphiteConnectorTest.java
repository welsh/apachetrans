package org.welsh.graphite.apachetrans.util;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static HttpServer graphiteServer;
	
	@BeforeClass
	public static void beforeClass() throws IOException {
		apacheStatus = new ApacheStatus("215", "1133", "3207", ".00498909", ".0670408", "361.769", "5396.24", "1", "7");
		
		graphiteServer = MockGraphiteServerFactory.createHttpServer(GRAPHITE_PORT_ONLINE);
		graphiteServer.start();
	}

	@AfterClass
	public static void afterClass() throws InterruptedException {
		graphiteServer.stop(0);
	}

	@Test
	public void testLogToGraphiteOnline() {
		log.info("STARTING: testLogToGraphiteOnline()");
		
		GraphiteConnector graphiteConnector = new GraphiteConnector(GRAPHITE_HOST, GRAPHITE_PORT_ONLINE);

		try {
			graphiteConnector.logToGraphite(apacheStatus, GRAPHITE_METRIC);
			
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
			graphiteConnector.logToGraphite(apacheStatus, GRAPHITE_METRIC);
			
			assertTrue(true);
		} catch (Exception e) {
			log.error("Error Pushing to Graphite Host [{}:{}]", graphiteConnector.getGraphiteHost(),  graphiteConnector.getGraphitePort());
			log.error("Stacktrace:", e);
			fail();
		}
		
		log.info("FINISHED: testLogToGraphiteOffline()");
	}
}
