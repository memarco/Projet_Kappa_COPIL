package server;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public abstract class ServerProperties {
	private static final String propPath = "server.properties";
	
	private static Properties bean = getInstance();
	
	public static Properties getInstance() {
		if(bean != null)
			return bean;
		
		try {
			//Loading properties
			FileInputStream fin = new FileInputStream(propPath);
			Properties prop = new Properties();
			prop.load(fin);
			fin.close();
			
			return prop;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
