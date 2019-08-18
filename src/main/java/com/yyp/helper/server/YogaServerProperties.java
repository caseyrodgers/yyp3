package com.yyp.helper.server;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class YogaServerProperties {
	
	static private YogaServerProperties __instance;
	static public YogaServerProperties getInstance() {
		if(__instance == null) {
			__instance = new YogaServerProperties();
		}
		return __instance;
	}
	
	Properties props;
	

	private YogaServerProperties() {
		props = new Properties();
		
		try {
			props.load(new FileReader(new File(System.getProperty("user.home"), "yoga_your_practice.properties")));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public String getProperty(String name) {
		return props.getProperty(name);
	}
}
