package org.welsh.graphite.apachetrans.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.welsh.graphite.apachetrans.domain.ApacheStatus;

public class ApacheConnectorTest {
	
	private static Logger log = LoggerFactory.getLogger(ApacheConnectorTest.class);
	
	private final String TEST_URL = "http://192.168.1.210/server-status?auto";
	private ApacheConnector apacheConnector;

	@Before
	public void setUp() throws Exception {
		apacheConnector = new ApacheConnector(TEST_URL);
		
		log.info("");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetApacheStatus() {
		assertEquals(TEST_URL, apacheConnector.getUrl());
	}

	@Test
	public void testGetUrl() {
		log.info("STARTING: testGetUrl()");
		
		ApacheStatus apacheStatus = apacheConnector.getApacheStatus();	
		assertNotNull(apacheStatus);
		
		log.info("FINISHED: testGetUrl()");
	}

}
