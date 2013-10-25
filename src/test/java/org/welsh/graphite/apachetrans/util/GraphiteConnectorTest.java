package org.welsh.graphite.apachetrans.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.welsh.graphite.apachetrans.domain.ApacheStatus;

public class GraphiteConnectorTest {
	
	private static Logger log = LoggerFactory.getLogger(GraphiteConnectorTest.class);

	private ApacheStatus apacheStatus; 
	private GraphiteConnector graphiteConnector;
	
	private final String GRAPHITE_HOST = "192.168.1.210";
	private final int    GRAPHITE_PORT = 2003;

	@Before
	public void setUp() throws Exception {
		apacheStatus = new ApacheStatus("215", "1133", "3207", ".00498909", ".0670408", "361.769", "5396.24", "1", "7");
		graphiteConnector = new GraphiteConnector(GRAPHITE_HOST, GRAPHITE_PORT);
		
		log.info("");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateGraphiteMessage() {
		log.info("STARTING: testCreateGraphiteMessage()");
		
		String graphiteMessage = graphiteConnector.createGraphiteMessage(apacheStatus, "nci.prod.app01");
		
		assertNotNull(graphiteMessage);
		
		log.info("Graphite Message: " + graphiteMessage);
		
		log.info("FINISHED: testCreateGraphiteMessage()\n");
	}

	@Test
	public void testLogToGraphite() {
		log.info("STARTING: testLogToGraphite()");
		
		String graphiteMessage = graphiteConnector.createGraphiteMessage(apacheStatus, "nci.prod.app01");

		try {
			graphiteConnector.logToGraphite(graphiteMessage);
			
			assertTrue(true);
		} catch (Exception e) {
			log.error("Error Pushing to Graphite Host [{}:{}]", graphiteConnector.getGraphiteHost(),  graphiteConnector.getGraphitePort());
			log.error("Stacktrace:", e);
			fail();
		}
		
		log.info("FINISHED: testLogToGraphite()\n");
	}
}
