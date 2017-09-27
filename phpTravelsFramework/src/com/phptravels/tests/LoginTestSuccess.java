package com.phptravels.tests;

import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.phptravels.commonlibrary.CommonLibrary;

public class LoginTestSuccess {

	public static void main(String[] args) {
		try {

			WebDriver driver = null;

			driver = CommonLibrary.getBrowser();

			WebElement currencyUSD = driver.findElement(By.xpath("//ul[@class='nav navbar-nav navbar-right currency_btn']/ul/li[2]/a/strong"));
			Actions actions = new Actions(driver);
			actions.moveToElement(currencyUSD).perform();
			System.out.println("Hover completed");

			//WebElement currencyINR = driver.findElement(By.xpath("//ul[@class='nav navbar-nav navbar-right currency_btn']/ul/li[2]/ul/li[8]/a"));
			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement currencyINR = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[@class='nav navbar-nav navbar-right currency_btn']/ul/li[2]/ul/li[8]/a")));
			currencyINR.click();
			System.out.println("currency INR clicked");
			System.out.println(new Date());

			Thread.sleep(60000);

			/*WebElement myAccount = driver.findElement(By.xpath("//li[@id='li_myaccount']/a"));
			
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", myAccount);
			File src= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(src, new File("C:/Users/hp1/Desktop/error.jpeg"));
			System.out.println("My account clicked");*/
			
			WebElement login  = driver.findElement(By.linkText("Login"));
			login.click();




		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
