package org.welsh.graphite.apachetrans.util;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.welsh.graphite.apachetrans.domain.ApacheStatus;
import org.welsh.graphite.apachetrans.mock.MockApacheServerFactory;

import com.sun.net.httpserver.HttpServer;

public class ApacheConnectorTest {

	private static final Logger log = LoggerFactory.getLogger(ApacheConnectorTest.class);
	
	private final static int PORT = 9876;
	
	private final String TEST_URL_SIMPLE = "http://127.0.0.1:" + PORT + "/server-status/simple?auto";
	private final String TEST_URL_EXTENDED = "http://127.0.0.1:" + PORT + "/server-status/extended?auto";
	private final String TEST_URL_NOT_FOUND = "http://127.0.0.1:" + PORT + "/404";
	private final String TEST_URL_OFFLINE = "http://0.0.0.0:10000";
	
	private static HttpServer apacheServer;

	@BeforeClass
	public static void beforeClass() throws IOException {		
		apacheServer = MockApacheServerFactory.createHttpServer(PORT);
		apacheServer.start();
	}

	@AfterClass
	public static void afterClass() throws InterruptedException {
		// Thread.sleep(1000000);
		apacheServer.stop(0);
	}

	@Test
	public void testGetUrl() {
		log.info("STARTING: testGetUrl()");
		
		ApacheConnector apacheConnector = new ApacheConnector(TEST_URL_EXTENDED);
		assertEquals(TEST_URL_EXTENDED, apacheConnector.getUrl());
		
		log.info("FINISHED: testGetUrl()");
	}
	
	@Test
	public void testGetApacheStatusSimple() {
		log.info("STARTING: testGetApacheStatusSimple()");

		ApacheConnector apacheConnector = new ApacheConnector(TEST_URL_SIMPLE);
		ApacheStatus apacheStatus = apacheConnector.getApacheStatus();
		
		assertNotNull(apacheStatus);

		log.info("FINISHED: testGetApacheStatusSimple()");
	}
	
	@Test
	public void testGetApacheStatusExtended() {
		log.info("STARTING: testGetApacheStatusExtended()");

		ApacheConnector apacheConnector = new ApacheConnector(TEST_URL_EXTENDED);
		ApacheStatus apacheStatus = apacheConnector.getApacheStatus();
		
		assertNotNull(apacheStatus);

		log.info("FINISHED: testGetApacheStatusExtended()");
	}

	@Test
	public void testGetApacheStatusPageNotFound() {
		log.info("STARTING: testGetApacheStatusPageNotFound()");

		ApacheConnector apacheConnector = new ApacheConnector(TEST_URL_NOT_FOUND);
		ApacheStatus apacheStatus = apacheConnector.getApacheStatus();
		
		assertNotNull(apacheStatus);

		log.info("FINISHED: testGetApacheStatusPageNotFound()");
	}
	
	@Test
	public void testGetApacheStatusServerOffline() {
		log.info("STARTING: testGetApacheStatusServerOffline()");

		ApacheConnector apacheConnector = new ApacheConnector(TEST_URL_OFFLINE);
		ApacheStatus apacheStatus = apacheConnector.getApacheStatus();
		
		assertNotNull(apacheStatus);

		log.info("FINISHED: testGetApacheStatusServerOffline()");
	}
}
