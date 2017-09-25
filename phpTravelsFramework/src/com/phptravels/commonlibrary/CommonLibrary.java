package com.phptravels.commonlibrary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class CommonLibrary {

	private static String v_rootDirectory = System.getProperty("user.dir");
	private static Logger Log = Logger.getLogger(CommonLibrary.class.getName());
	private static WebDriver driver = null;
	private static String v_envXmlPath = v_rootDirectory + "\\Environment.xml";
	private static Properties  propertyFile = new Properties();
	private static Map<String, String> v_testMap = new HashMap<String, String>();

	/****************************************************************************************
	 * loadEnvironmentXml - Reads the XML tags that are passed to method and
	 * returns tag values	 * 
	 * @param tagName1
	 * @param tagName2
	 * @throws Exception
	 * @return tagValue
	 ****************************************************************************************/
	public static String loadEnvironmentXml(String tagName1, String tagName2) throws Exception {
		String v_value = "";

		File v_environmentxml = new File(v_envXmlPath);

		DocumentBuilderFactory xmlBuilderFactory = DocumentBuilderFactory.newInstance();

		DocumentBuilder xmlBuilder = xmlBuilderFactory.newDocumentBuilder();

		Document enviornmentXml = xmlBuilder.parse(v_environmentxml);

		enviornmentXml.getDocumentElement().normalize();

		NodeList nList_browser = enviornmentXml.getElementsByTagName(tagName1);

		for (int i = 0; i < nList_browser.getLength(); i++) {

			Node nNode = nList_browser.item(i);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;

				v_value = eElement.getElementsByTagName(tagName2).item(0).getTextContent();

				return v_value;
			}
		}
		return v_value;
	}
	

	/****************************************************************************************
	 * getBrowser - Fetches browsername, driver path and URL. 
	 *              Loads the URL in browser
	 * @throws Exception
	 *****************************************************************************************/
	public static WebDriver getBrowser() throws Exception {
		String v_browserName, v_driverPath, v_url = "";
		DOMConfigurator.configure("log4j.xml");

		// Fetch the browser,driver path and url from environmentXML
		v_browserName = CommonLibrary.loadEnvironmentXml("browser", "name");

		v_driverPath = CommonLibrary.loadEnvironmentXml("browser", "driver");

		v_url = CommonLibrary.loadEnvironmentXml("webpage_URL", "url");

		switch (v_browserName) {
		case "firefox":
			System.setProperty("webdriver.gecko.driver", v_driverPath);

			driver = new FirefoxDriver();
			Log.info("Launching browser : " + v_browserName);

			driver.get(v_url);
			Log.info("Launching URL : " + v_url);
			break;

		case "chrome":
			System.setProperty("webdriver.chrome.driver", v_driverPath);

			driver = new ChromeDriver();
			Log.info("Launching browser : " + v_browserName);
			driver.manage().window().maximize();

			driver.get(v_url);
			Log.info("Launching URL : " + v_url);
			break;

		case "ie":
			System.setProperty("webdriver.ie.driver", v_driverPath);

			driver = new InternetExplorerDriver();
			driver.manage().window().maximize();
			Log.info("Launching browser : " + v_browserName);

			driver.get(v_url);
			Log.info("Launching URL : " + v_url);
			break;

		default:
			System.setProperty("webdriver.gecko.driver", v_driverPath);

			driver = new FirefoxDriver();
			Log.info("Launching default browser : " + v_browserName);

			driver.get(v_url);
			Log.info("Launching deafult URL : " + v_url);
			break;
		}
		
		return driver;

	}

	
	/*****************************************************************************************
	 * propertiesParser - Reads properties from OR file and retruns the webelement
	 * @param elementName
	 * @param driver
	 * @return element
	 * @throws Exception
	 ******************************************************************************************/
	public static WebElement propertiesParser(String elementName, WebDriver driver) throws Exception {
		String v_OrFileName = CommonLibrary.loadEnvironmentXml("objectRepository", "name");
		String v_ORFilePath = CommonLibrary.loadEnvironmentXml("objectRepository", "filePath");
		String v_objectORFile = v_ORFilePath + "\\" + v_OrFileName;
		WebElement element = null;
		
		FileInputStream stream  = new FileInputStream(v_objectORFile);
		propertyFile.load(stream);
		String elementProperty = propertyFile.getProperty(elementName);		
		String locatorType = elementProperty.split(":")[0];
		String locatorValue = elementProperty.split(":")[1];
		
		switch (locatorType) {
		case "id":
			element = driver.findElement(By.id(locatorValue));
			break;

		case "name":
			element = driver.findElement(By.name(locatorValue));
			break;

		case "cssselector":
			element = driver.findElement(By.cssSelector(locatorValue));
			break;

		case "linktext":
			element = driver.findElement(By.linkText(locatorValue));
			break;

		case "partiallinktext":			
			element = driver.findElement(By.partialLinkText(locatorValue));
			break;

		case "tagname":
			element = driver.findElement(By.tagName(locatorValue));
			break;

		case "xpath":
			element = driver.findElement(By.xpath(locatorValue));
			break;
		}
		return element;
	}
	
	/*****************************************************************************************
	 * loadCsvData - Loads all the test data from the specified CSV file into hashmap
	 * @return void
	 * @throws Exception
	 ******************************************************************************************/
	public static void loadCsvData() throws Exception{
		String v_fileName = CommonLibrary.loadEnvironmentXml("testDataFile", "name");
		String v_filePath = CommonLibrary.loadEnvironmentXml("testDataFile", "filePath");
		String v_testDataFile = v_filePath + v_fileName;
		BufferedReader reader = new BufferedReader(new FileReader(v_testDataFile));
		String line = "";
		while((line = reader.readLine()) != null){
			String[] parts = line.split(",");
			v_testMap.put(parts[0], parts[1]);		
		}
		reader.close();
	}
	
	/*****************************************************************************************
	 * getParameter - Fetches the data loaded into hashmap and returns to calling methods
	 * @param Key
	 * @param Value
	 * @return String
	 * @throws Exception
	 ******************************************************************************************/
	public static String getParameter(String key, String value){
		String datapool = v_testMap.get(key);
		String[] datasets = datapool.split(";");
		String dataset = "";
		String data[];
		for(int i=0; i < datasets.length; i++){
			if(datasets[i].contains(value)){
				dataset = datasets[i];
			}
		}
		data = dataset.split("=");
		System.out.println(data[1]);
		return data[1];
	}
	public static void main(String[] args) throws Exception {
		CommonLibrary.loadCsvData();
		CommonLibrary.getParameter("home_currency", "currency");
		
	}
}
