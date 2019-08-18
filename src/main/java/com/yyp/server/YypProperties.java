package com.yyp.server;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class YypProperties extends Properties {

	private static YypProperties __instance;
	public static YypProperties getInstance() throws Exception {
		if(__instance == null) {
			__instance = new YypProperties();
		}
		return __instance;
	}
	
	public void readProperties() throws Exception {
		try {
			/**
			 * allow override of system properties
			 * 
			 */
			String propsToUse = System.getenv("YYP_PROPERTIES");
			File pfile = null;
			if (propsToUse != null && propsToUse.length() > 0) {
				pfile = new File(propsToUse);
			} else {
				pfile = new File(System.getProperty("user.home"),
						"metube.properties");
			}
			if (!pfile.exists()) {
				throw new Exception("Property file '" + pfile
						+ "' does not exist");
			}
			clear();
			load(new FileReader(pfile));
		} catch (Exception e) {
			throw e;
		}
	}
	
	private YypProperties() throws Exception {
		readProperties();
	}

	public String getContentDir() {
		return __instance.getProperty("content.dir");
	}

	public String getContentVideoDir() {
		return getContentDir() + "/videos";
	}

}
