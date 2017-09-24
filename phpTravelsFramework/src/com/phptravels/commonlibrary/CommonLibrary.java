package com.phptravels.commonlibrary;

import java.io.File;
import java.io.FileInputStream;
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
	 * getBrowser - Fetches browername, driver path and URL. 
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
}
