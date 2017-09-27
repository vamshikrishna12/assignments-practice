package com.jira.test.automation;

import java.io.FileInputStream;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;





public class JiraTestUpload {

	static Map<String, List<String>> v_tstCases = new HashMap<String, List<String>>();


	public static void main(String[] args) throws Exception {

		String v_loggedUser = System.getProperty("user.name");
		String v_tcFileName = "Jira_Upload_Testcases.xlsx";
		String v_tcFile = "C:\\Users\\"+v_loggedUser+"\\Desktop\\"+v_tcFileName;
		int v_sheetCount = 0;
		String v_sheetName = "";
		List<String> v_tstShtVls = new ArrayList<String>();

		System.out.println(v_tcFile);

		//Read the excel through file input stream
		FileInputStream fis = new FileInputStream(v_tcFile);
		Workbook wb = WorkbookFactory.create(fis);
		Sheet v_ticketSheet = null;


		//Get the number of sheets in excel
		v_sheetCount = wb.getNumberOfSheets();


		for(int i = 0; i < v_sheetCount; i++){
			v_sheetName = wb.getSheetName(i);
			v_ticketSheet = wb.getSheet(v_sheetName);		//For each sheet index get the sheet name
			v_tstShtVls = getCellValues(v_ticketSheet);
		}

		System.setProperty("webdriver.gecko.driver", "A:/Softwares/Drivers/Firefox/geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(180,TimeUnit.SECONDS);

		driver.get("http://jira.mynmg.com");
		jiraAutomationScript(driver, v_sheetName, v_tstShtVls);

	}

	public static List<String> getCellValues(Sheet v_ticketSheet) {

		Cell v_tcCell= null;
		Cell v_expResCell = null;
		String v_tcName = "";
		String v_expResult = "";
		List<String> v_valSetOne = new ArrayList<String>();

		//For each sheet loop through the rows and get cell values of 
		//column B - Test case Name and D - Expected Result
		for(int j = 2; j < v_ticketSheet.getPhysicalNumberOfRows(); j++){

			//Get the test case name
			v_tcCell = v_ticketSheet.getRow(j).getCell(1);
			v_tcName = v_tcCell.getStringCellValue();

			//Get the expected result 
			v_expResCell = v_ticketSheet.getRow(j).getCell(3);
			v_expResult = v_expResCell.getStringCellValue();


			//Load the test case name and exp result into hashmap
			v_valSetOne.add(v_tcName);
			v_valSetOne.add(v_expResult);
		}
		return v_valSetOne;
	}
	
	
	public static void jiraAutomationScript(WebDriver driver, String sheetName, List<String> v_tests) throws Exception {

		WebElement v_userName = driver.findElement(By.id("login-form-username"));
		v_userName.sendKeys("invj12");
		Thread.sleep(5000);

		WebElement v_pwd = driver.findElement(By.id("login-form-password"));
		v_pwd.sendKeys("Welcome4");
		Thread.sleep(5000);

		WebElement v_logButton = driver.findElement(By.id("login"));
		v_logButton.click();

		WebDriverWait wait = new WebDriverWait(driver, 180);
		WebElement v_testsLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("zephyr_je.topnav.tests")));
		boolean v_testEnabled = v_testsLink.isEnabled();
		

		
		WebElement v_testsCreateLink = driver.findElement(By.id("zephyr-je.topnav.tests.create_lnk"));
		if(v_testsCreateLink.isDisplayed()){
			v_testsCreateLink.click();
		}

	}

}
