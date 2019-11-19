package com.chmei.nzbcommon.util;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class ConfigHelper {
	public static final Properties prop = new Properties();

	static {
		try {
			InputStream is = ConfigHelper.class.getResourceAsStream("/config/system.properties");
			BufferedReader bf = new BufferedReader(new InputStreamReader(is)); 
			prop.load(bf);
			is.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static String getValue(String key) {
		return prop.getProperty(key);
	}

	/*public static void main(String[] args) {
		String pageSize = ConfigHelper.getValue("smsContent");
		System.out.println(pageSize);
	}*/

}
