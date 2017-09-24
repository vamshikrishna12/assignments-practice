package com.phptravels.homepagelibrary;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.phptravels.commonlibrary.CommonLibrary;

public class HomePageLibrary {
	
	public void currencyChange(String currency, WebDriver driver) throws Exception {
		String v_currency = currency;
		
		WebElement ele_currency = CommonLibrary.propertiesParser("phptravels_home_currency", driver);
		
		Select oSelect = new Select(ele_currency);
		
		oSelect.selectByVisibleText(v_currency);
		
		Thread.sleep(5000);
	}
	
	public void myAccountLoginClick(WebDriver driver) throws Exception {
		
		WebElement myAccount = CommonLibrary.propertiesParser("phptravels_home_MyAccount", driver);
		
		myAccount.click();
		
		Thread.sleep(5000);
		
		WebElement login = CommonLibrary.propertiesParser("phptravels_home_MyAccount_Login", driver);
		
		login.click();
		
		Thread.sleep(5000);
		
	}
	
	public void Login(WebDriver driver)throws Exception {
		
		WebElement username = CommonLibrary.propertiesParser("phptravels_Login_username", driver);
		
		username.sendKeys("vkjregisteruseless@gmail.com");
	
		Thread.sleep(5000);
		
		WebElement pwd = CommonLibrary.propertiesParser("phptravels_Login_pwd", driver);
		
		pwd.sendKeys("welcome1");
	
		Thread.sleep(5000);
		
		WebElement loginBtn = CommonLibrary.propertiesParser("phptravels_Login_btn", driver);
		
		loginBtn.click();
		
		Thread.sleep(5000);			
		
	}
	
}
