package util;

import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class JsonImpl {
	private static Gson gson = getInstance();
	
	private static synchronized Gson getInstance() {
		if(gson != null)
			return gson;
		
		Properties props = ServerProperties.getInstance();
		if(props.getProperty("PRETTY_PRINT").equals("TRUE")) {
			return new GsonBuilder().setPrettyPrinting().create();
		} else {
			return new Gson();
		}
	}
	
	public static String toJson(Object o) {
		return getInstance().toJson(o);
	}
	
	public static <T> T fromJson(String jsonString, Class<T> classOfT) {
		return getInstance().fromJson(jsonString, classOfT);
	}
}
