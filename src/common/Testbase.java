package common;

import java.io.FileInputStream;
import java.util.Properties;

import org.testng.annotations.BeforeSuite;

public class Testbase {

	String configFile = System.getProperty("user.dir") + "//resources//config.properties";
	public static String domain = "";
	public Properties prop;

	public static int RESPONSE_CODE_200 = 200;
	public static int RESPONSE_CODE_400 = 400;
	public static int RESPONSE_CODE_500 = 500;

	@BeforeSuite
	public void setup() {

		try {
			FileInputStream fis = new FileInputStream(configFile);
			prop = new Properties();
			prop.load(fis);
			domain = prop.get("domain").toString();

		} catch (Exception e) {
			System.out.println("error reading config file");
		}

	}

}
