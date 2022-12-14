package tests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Random;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import common.ExcelOperations;
import common.RestClient;
import common.Testbase;

public class EpochValidatorTest extends Testbase {

	String url = "";
	int actualResponseCode;
	String responseString = "";
	String expectedMessage = "";
	HashMap<String, String> headerMap = new HashMap<>();

	@BeforeClass
	public void setURI() {
		url = prop.getProperty("uri");
		headerMap = new HashMap<>();
		headerMap.put("accept", "text/html,application/xhtml+xml");
		headerMap.put("accept-encoding", "gzip, deflate, br");
	}

	@Test(priority = 1)
	public void verifyEpochConversionWithValidInput() {

		String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
		Random r = new Random();
		CloseableHttpResponse response = null;

		System.out.println("=== start test " + nameofCurrMethod + "===");

		int randomEpoch = r.nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);

		String filter = "unixtimestamp=" + randomEpoch;

		System.out.println("=== random epoch date time - " + randomEpoch);

		try {
			response = RestClient.httpGet(domain + url, filter, headerMap);
			actualResponseCode = response.getStatusLine().getStatusCode();
			responseString = EntityUtils.toString(response.getEntity());

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(responseString);
		Assert.assertEquals(actualResponseCode, RESPONSE_CODE_200);

		JSONObject objJson = new JSONObject(responseString);
		String dateTime = objJson.getString("Datetime");
		System.out.println("Date time received - " + dateTime);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		try {
			dateFormat.parse(dateTime.trim());
			Assert.assertTrue(true,
					"date time format is matching to expected format yyyy-MM-dd HH:mm:ss - " + dateTime);

		} catch (ParseException e) {
			Assert.assertTrue(false,
					"date time format is not matching to expected format yyyy-MM-dd HH:mm:ss - " + dateTime);
		}

	}

	@Test(priority = 2)
	public void verifyEpochConversionWithInvalidInputExceedingInt32Range() {

		String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();

		Random r = new Random();
		CloseableHttpResponse response = null;

		System.out.println("=== start test " + nameofCurrMethod + "===");

		long randomEpoch = r.nextLong();

		String filter = "unixtimestamp=" + randomEpoch;

		System.out.println("=== random epoch date time - " + randomEpoch);

		try {
			response = RestClient.httpGet(domain + url, filter, headerMap);
			actualResponseCode = response.getStatusLine().getStatusCode();
			responseString = EntityUtils.toString(response.getEntity());

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(responseString);
		Assert.assertEquals(actualResponseCode, RESPONSE_CODE_400);

		expectedMessage = ExcelOperations.readDataFromExcelForTestcase("epochValidator", nameofCurrMethod,
				"ExpectedMessage");

		JSONObject objJson = new JSONObject(responseString);
		String error = objJson.getString("Error");

		Assert.assertEquals(error, expectedMessage);

	}

	@Test(priority = 3)
	public void verifyEpochConversionWithInvalidInput() {

		String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();

		CloseableHttpResponse response = null;

		System.out.println("=== start test " + nameofCurrMethod + "===");

		String requestData = "EpochString";

		String filter = "unixtimestamp=" + requestData;

		try {
			response = RestClient.httpGet(domain + url, filter, headerMap);
			actualResponseCode = response.getStatusLine().getStatusCode();
			responseString = EntityUtils.toString(response.getEntity());

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(responseString);
		Assert.assertEquals(actualResponseCode, RESPONSE_CODE_400);

		expectedMessage = ExcelOperations.readDataFromExcelForTestcase("epochValidator", nameofCurrMethod,
				"ExpectedMessage");

		JSONObject objJson = new JSONObject(responseString);
		String error = objJson.getString("Error");

		Assert.assertEquals(error, expectedMessage);

	}

	@Test(priority = 4)
	public void verifyEpochConversionWithMaxBoundaryValue() {

		String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();

		CloseableHttpResponse response = null;

		System.out.println("=== start test " + nameofCurrMethod + "===");

		String requestData = "" + Integer.MAX_VALUE; // to validate max possible epoch value

		String filter = "unixtimestamp=" + requestData;

		try {
			response = RestClient.httpGet(domain + url, filter, headerMap);
			actualResponseCode = response.getStatusLine().getStatusCode();
			responseString = EntityUtils.toString(response.getEntity());

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(responseString);
		Assert.assertEquals(actualResponseCode, RESPONSE_CODE_200);

		JSONObject objJson = new JSONObject(responseString);
		String dateTime = objJson.getString("Datetime");
		System.out.println("Date time received - " + dateTime);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		try {
			dateFormat.parse(dateTime.trim());
			Assert.assertTrue(true,
					"date time format is matching to expected format yyyy-MM-dd HH:mm:ss - " + dateTime);

		} catch (ParseException e) {
			Assert.assertTrue(false,
					"date time format is not matching to expected format yyyy-MM-dd HH:mm:ss - " + dateTime);
		}

	}

}
