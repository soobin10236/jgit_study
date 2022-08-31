package common;

import java.io.InputStream;
import java.util.Properties;

public class util {

	public Properties readProperties(String propFileName) {
		Properties prop = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		
		try {
			if(inputStream != null) {
				prop.load(inputStream);
				return prop;
			} else {
				throw new Exception("Error!!");
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
