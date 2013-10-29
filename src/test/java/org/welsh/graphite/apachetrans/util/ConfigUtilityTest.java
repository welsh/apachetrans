package org.welsh.graphite.apachetrans.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.welsh.graphite.apachetrans.constants.Config;

public class ConfigUtilityTest {
	
	private static Logger log = LoggerFactory.getLogger(ConfigUtilityTest.class);
	private final String TEST_DIR = "conf-files/";
	private final String TEST_CONF_1 = "application-1.conf";

	@Before
	public void setUp() throws Exception {	
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testDefaultConstructor() {
		log.info("STARTING: testDefaultConstructor()");
		
		try {
			System.setProperty(Config.SYS_CONF_DIR_PROPERTY, "");
			System.setProperty(Config.SYS_CONF_FILE_PROPERTY, "");

			ConfigUtility configUtility = new ConfigUtility();
			
			assertNotNull(configUtility);			
			assertEquals(Config.DEFAULT_CONF_DIR + Config.DEFAULT_CONF_FILE, configUtility.getConfFile());

		} catch (Exception e) {
			log.error("Test Failed.", e);
			fail(e.getLocalizedMessage());
		}
		
		log.info("FINISHED: testDefaultConstructor()");
	}
	
	@Test
	public void testConstructor() {
		log.info("STARTING: testConstructor()");
		
		try {
			ConfigUtility configUtility = new ConfigUtility(TEST_DIR + TEST_CONF_1);
			
			assertNotNull(configUtility);			
			assertEquals(TEST_DIR + TEST_CONF_1, configUtility.getConfFile());

		} catch (Exception e) {
			log.error("Test Failed.", e);
			fail(e.getLocalizedMessage());
		}
		
		log.info("FINISHED: testConstructor()");
	}
	
	@Test
	public void testConstructorSystemPropertys() {
		log.info("STARTING: testConstructorSystemPropertys()");
		
		try {
			System.setProperty(Config.SYS_CONF_DIR_PROPERTY, TEST_DIR);
			System.setProperty(Config.SYS_CONF_FILE_PROPERTY, TEST_CONF_1);
			
			ConfigUtility configUtility = new ConfigUtility();
			
			assertNotNull(configUtility);
			assertEquals(TEST_DIR + TEST_CONF_1, configUtility.getConfFile());
			
		} catch (Exception e) {
			log.error("Test Failed.", e);
			fail(e.getLocalizedMessage());
		}
		
		log.info("FINISHED: testConstructorSystemPropertys()");
	}
}
