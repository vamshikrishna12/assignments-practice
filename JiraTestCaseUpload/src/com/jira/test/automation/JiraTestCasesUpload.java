package com.jira.test.automation;

import org.testng.annotations.Test;

import com.jira.test.automation.log.Log;

import org.testng.annotations.BeforeTest;

import static org.testng.Assert.assertEquals;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;

public class JiraTestCasesUpload {

	WebDriver driver = null;

	@Test
	public void jiraAutomationScript(WebDriver driver, String sheetName, List<String> v_tests)  throws Exception{

		System.setProperty("webdriver.gecko.driver", "A:/Softwares/Drivers/Firefox/geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);

		driver.get("http://jira.mynmg.com");


		WebElement v_userName = driver.findElement(By.id("login-form-username"));
		v_userName.sendKeys("invj12");
		Thread.sleep(2000);

		WebElement v_pwd = driver.findElement(By.id("login-form-password"));
		v_pwd.sendKeys("Welcome4");
		Thread.sleep(2000);

		WebElement v_logButton = driver.findElement(By.id("login"));
		v_logButton.click();

		WebDriverWait wait = new WebDriverWait(driver, 180);
		WebElement v_testsLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("zephyr_je.topnav.tests")));
		boolean v_testEnabled = v_testsLink.isEnabled();
		assertEquals(v_testEnabled, true);
		v_testsLink.click();
		Thread.sleep(5000);

		WebElement v_testsCreateLink = driver.findElement(By.id("zephyr-je.topnav.tests.create_lnk"));
		if(v_testsCreateLink.isDisplayed()){
			v_testsCreateLink.click();
		}
		WebElement v_createTestHomeTab = driver.findElement(By.id("aui-uid-0"));
		assertEquals(v_createTestHomeTab.isEnabled(), true);

		WebElement v_dueDate = driver.findElement(By.id("duedate"));
		v_dueDate.sendKeys("27/NOV/17");
		Thread.sleep(2000);

		WebElement v_summary = driver.findElement(By.id("summary"));
		v_summary.sendKeys(v_tests.get(0));
		Thread.sleep(2000);

		WebElement v_description = driver.findElement(By.id("description"));
		v_description.sendKeys(v_tests.get(1));
		Thread.sleep(2000);

		WebElement v_assignToMe = driver.findElement(By.id("assign-to-me-trigger"));
		v_assignToMe.click();
		Thread.sleep(2000);

		WebElement v_versionsTab = driver.findElement(By.id("aui-uid-2"));
		v_versionsTab.click();
		Thread.sleep(2000);

		WebElement v_fixVersions = driver.findElement(By.id("fixVersions-textarea"));
		v_fixVersions.sendKeys("OCT 2017 POS DEV");
		v_fixVersions.sendKeys(Keys.ENTER);	
		Thread.sleep(2000);

		WebElement v_createButton = driver.findElement(By.id("issue-create-submit"));
		v_createButton.submit();
		Thread.sleep(60000);

		WebElement v_testNo = driver.findElement(By.id("key-val"));
		System.out.println(v_testNo.getText());

		WebElement v_MoreActions = driver.findElement(By.id("opsbar-zephyr-je-manage-plan-top_more"));
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();" , v_MoreActions);
		//v_MoreActions.click();
		Thread.sleep(5000);

		WebElement v_Link = driver.findElement(By.id("zfj-link-issue"));
		v_Link.click();
		Thread.sleep(5000);

		WebElement v_issue = driver.findElement(By.id("jira-issue-keys-textarea"));
		v_issue.sendKeys(sheetName);
		Thread.sleep(5000);
		v_issue.sendKeys(Keys.ENTER);
		Thread.sleep(5000);

		WebElement v_linkBtn = driver.findElement(By.xpath("//form[@id='link-jira-issue']/div[2]/div/input"));
		v_linkBtn.click();
		Thread.sleep(10000);

		WebElement v_MoreActions_2 = driver.findElement(By.id("opsbar-zephyr-je-manage-plan-top_more"));
		JavascriptExecutor js_2 = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();" , v_MoreActions);
		//v_MoreActions.click();
		Thread.sleep(5000);

		WebElement v_addToCycle = driver.findElement(By.id("viewissue-add-cycle"));
		v_addToCycle.click();
		Thread.sleep(2000);

		WebElement v_versionSelect = driver.findElement(By.xpath("//select[@id='project_version']/optgroup[@label='Unreleased Versions']/option[4]"));
		v_versionSelect.click();
		Thread.sleep(2000);

		WebElement v_testCycle = driver.findElement(By.id("cycle_names"));
		Select v_tstCycleSelect = new Select(v_testCycle);
		v_tstCycleSelect.selectByVisibleText("POS OCT 2017 SIT Test cycle");
		Thread.sleep(2000);

		WebElement v_addBtn = driver.findElement(By.linkText("Add"));
		v_addBtn.click();
		Thread.sleep(4000);		

	}


	@BeforeTest
	public void beforeTest() throws Exception {
		String v_loggedUser = System.getProperty("user.name");
		String v_tcFileName = "Jira_Upload_Testcases.xlsx";
		String v_tcFile = "C:\\Users\\"+v_loggedUser+"\\Desktop\\"+v_tcFileName;
		int v_sheetCount = 0;
		String v_sheetName = "";
		List<String> v_tstShtVls = new ArrayList<String>();

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
			if(v_tstShtVls.size() != 0){
				jiraAutomationScript(driver, v_sheetName, v_tstShtVls);
			}
		}
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
			if(v_tcName.isEmpty()){
				System.out.println("First test name is found empty in sheet : "+v_ticketSheet);
				break;
			}

			//Get the expected result 
			v_expResCell = v_ticketSheet.getRow(j).getCell(3);
			v_expResult = v_expResCell.getStringCellValue();


			//Load the test case name and exp result into hashmap
			v_valSetOne.add(v_tcName);
			v_valSetOne.add(v_expResult);
		}
		return v_valSetOne;
	}


	@AfterTest
	public void afterTest() {
	}

}
