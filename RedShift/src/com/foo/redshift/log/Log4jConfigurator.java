package com.foo.redshift.log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.NDC;
import org.apache.log4j.PropertyConfigurator;

import com.foo.redshift.core.CoreStateManager;

/**
 * dumb log4j configuration helper class.
 * 
 * @author ter
 * 
 */
public class Log4jConfigurator {
    public static void configureLog4j() throws IOException {
	NDC.push("ter");
	Properties props = new Properties();
	@SuppressWarnings("rawtypes")
	Class<? extends Class> coreClass = CoreStateManager.class.getClass();
	InputStream propStream = coreClass
		.getResourceAsStream("/META-INF/log4j.properties");
	props.load(propStream);
	PropertyConfigurator.configure(props);
	propStream.close();
	StdOutErrLog.tieSystemOutAndErrToLog();
    }

}
