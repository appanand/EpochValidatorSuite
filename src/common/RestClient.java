package common;

import java.util.HashMap;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class RestClient {

	public static CloseableHttpResponse httpGet(String url, String parameters, HashMap<String, String> headerMap) {

		CloseableHttpClient httpClient = HttpClients.createDefault();

		try {

			HttpGet httpGet = new HttpGet(url + "?" + parameters);

			if (headerMap.size() > 0) {

				for (String key : headerMap.keySet()) {

					httpGet.addHeader(key, headerMap.get(key));

				}
			}

			return httpClient.execute(httpGet);

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

}
