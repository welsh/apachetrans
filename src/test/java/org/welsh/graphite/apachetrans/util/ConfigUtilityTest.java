package org.welsh.graphite.apachetrans.util;

import static org.junit.Assert.assertNotNull;

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
	public void testConfigUtility() {
		log.info("STARTING: testConfigUtility()");
		
		try {
			System.setProperty(Config.SYS_CONF_DIR_PROPERTY, TEST_DIR);
			System.setProperty(Config.SYS_CONF_FILE_PROPERTY, TEST_CONF_1);
			ConfigUtility configUtility = new ConfigUtility();
			configUtility.loadConfiguration();
			assertNotNull(configUtility);
			
			log.info(configUtility.getAppSettings().toString());
		} catch (Exception e) {
			log.error("Test Failed.", e);
		}
		
		log.info("FINISHED: testConfigUtility()");
	}

}
