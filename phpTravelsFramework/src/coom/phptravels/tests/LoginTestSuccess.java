package coom.phptravels.tests;

import com.phptravels.commonlibrary.CommonLibrary;

public class LoginTestSuccess {
	
	public static void main(String[] args) {
		try {
			CommonLibrary.getBrowser();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
