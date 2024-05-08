package com.mop.qa.test.bvt;


import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.mop.qa.Utilities.ReadDataSheet;
import com.mop.qa.testbase.TestBase; 
import SOURCE_CODE.SFDC.DB; 
import USER_SPACE.BusinessComponent.BC; 
import SOURCE_CODE.SFDC.SFDCAutomationFW; 
import USER_SPACE.TestPrerequisite.DataSetup;
import USER_SPACE.ObjectRepository.AccountsScreen_LUI;
import USER_SPACE.ObjectRepository.AllAppPage_LUI;
import USER_SPACE.ObjectRepository.AllAppsTabsScreen_LUI;
import USER_SPACE.ObjectRepository.ContactsScreen_LUI;

/* 
* 
* @Author: <Name of Test Script Creator> 
* @Description: <Please mention the scope of this test script> 
* @General Guidelines: Every Test Script must begin from Launching 
* URL of login screen and must end with browser closed 
*/ 

public class CAAS_CreateContact extends TestBase { 

@Test	
public void createMyTest() { 

	SFDCAutomationFW sfdc = null; 
	ContactsScreen_LUI contactsScreen = null; 
	AllAppPage_LUI allAppScreen = null;
	AllAppsTabsScreen_LUI allTAppsObjectsScreen = null;
	String CAAS_CreateContact = "CAAS_CreateContact";

	String TCName = "CAAS_CreateContact"; 
	if (toolName.equalsIgnoreCase("Selenium"))
	{ 
		System.out.println("----------------->Inside Test Case");
		sfdc = new SFDCAutomationFW(remoteDriver, TCName);
		System.out.println("----------->Back to Test case");
		contactsScreen = new ContactsScreen_LUI(remoteDriver); 
		allTAppsObjectsScreen = new AllAppsTabsScreen_LUI(remoteDriver);
		allAppScreen = new AllAppPage_LUI(remoteDriver);
		
	}
	else if (toolName.equalsIgnoreCase("Appium"))
	{
		sfdc = new SFDCAutomationFW(appiumDriver, TCName);
		contactsScreen = new ContactsScreen_LUI(appiumDriver); 
		allTAppsObjectsScreen = new AllAppsTabsScreen_LUI(appiumDriver);
	}
	
	DB DB = new DB();
	BC BC = new BC(remoteDriver);
	DataSetup DataSetup = new DataSetup();

	System.out.println("-----------Begin of TestScript-------------");

	try { 
			 
		DB.Connect(DataSetup.TestData);
			
		//Reading the test data from external test data sheet
		String ACCOUNTNAME = DB.ReadXLData("CreateAccount", "ACCOUNT_NAME_SAVED", "TESTCASENAME", "CAAS_CreateAccount");
		String PHONE = DB.ReadXLData("CreateContact", "PHONE", "TESTCASENAME", TCName);
		String SALUTATION = DB.ReadXLData("CreateContact", "SALUTATION", "TESTCASENAME", TCName);
		String FIRST_NAME = DB.ReadXLData("CreateContact", "FIRST_NAME", "TESTCASENAME", TCName);
		String LAST_NAME = DB.ReadXLData("CreateContact", "LAST_NAME", "TESTCASENAME", TCName);
		String MOBILE = DB.ReadXLData("CreateContact", "MOBILE", "TESTCASENAME", TCName);
		String TITLE = DB.ReadXLData("CreateContact", "TITLE", "TESTCASENAME", TCName);
		String BIRTH_DAY = DB.ReadXLData("CreateLead", "BIRTH_DAY", "TESTCASENAME", TCName);
		String STREET = DB.ReadXLData("CreateContact", "STREET", "TESTCASENAME", TCName);
		String CITY = DB.ReadXLData("CreateContact", "CITY", "TESTCASENAME", TCName);
		String STATE = DB.ReadXLData("CreateContact", "STATE", "TESTCASENAME", TCName);
		String ZIP = DB.ReadXLData("CreateContact", "ZIP", "TESTCASENAME", TCName);
		String COUNTRY = DB.ReadXLData("CreateContact", "COUNTRY", "TESTCASENAME", TCName);
				
		
		//DB.Connect(DataSetup.Logininfo);
		//ReadDataSheet rds = new ReadDataSheet();
		//String OWNER =  rds.getValue("LoginInfo", SFDCLoginUserName, "Name");
				
		String FIRSTNAME = FIRST_NAME+BC.GetCurrentDateTimeStamp();
		Thread.sleep(1000L);
		String LASTNAME = LAST_NAME+BC.GetCurrentDateTimeStamp();
		Thread.sleep(1000L);
		String mandatory_error = "Complete this field";
		
		// Login to SFDC 
		sfdc.LoginToSFDC(SFDCLoginUserName); //This variable is inherited from TestBase
		
		Thread.sleep(5000L);
		
		//Selecting Sales app from app launcher
		sfdc.SelectApplication_LUI("Sales");
		Thread.sleep(3000L);
		
		allTAppsObjectsScreen.ContactsTab().Click();
		
		contactsScreen.NewButton().Click();
		Thread.sleep(4000L);
		contactsScreen.SaveButton().Click();
		Thread.sleep(3000L);
		
		contactsScreen.LastNameField().VerifyFieldErrorMsgOnEditPage(mandatory_error);
		sfdc.VerifyPageLevelErrorMessage_LUI("Review the following fields");
							
		contactsScreen.SalutationField().SelectPL(SALUTATION);
		contactsScreen.FirstNameField().Type(FIRSTNAME);
		contactsScreen.LastNameField().Type(LASTNAME);
		contactsScreen.TitleField().Type(TITLE);
		contactsScreen.PhoneField().Type(PHONE);
		contactsScreen.MobileField().Type(MOBILE);
		contactsScreen.MailingStreetField().Type(STREET);
		contactsScreen.MailingCityField().Type(CITY);
		contactsScreen.MailingZipPostalCodeField().Type(ZIP);
		contactsScreen.MailingStateProvinceField().Type(STATE);
		contactsScreen.BirthdateField().SelectFromDateLookup("2020","October","20");
		contactsScreen.AccountNameField().SelectFromLookup(ACCOUNTNAME);
		Thread.sleep(2000);
				
		contactsScreen.SaveButton().Click();
		Thread.sleep(1000L);
		//sfdc.remoteDriver.findElement(By.xpath("//div[@class='forceVisualMessageQueue' ]/div/div/div/div/div/span[contains(text(), 'Contact')]"));
		Thread.sleep(1000L);
		contactsScreen.AddLogToCustomReport("Validated, Successfully Related Contact Created", "Pass");
		Thread.sleep(4000L);
		
		String NAME = FIRSTNAME+" "+LASTNAME;
		String msgSuccess = "Contact \""+NAME+"\" was created.";
		System.out.println(msgSuccess);
				
		DB.Connect(DataSetup.TestData);		
		
		//Update excel with full name
		DB.UpdateXLCell("CreateContact", NAME, "CONTACT_NAME", "TESTCASENAME", TCName);
		System.out.println("Newly Created contact is: "+NAME);
		
		sfdc.LogOff();
		remoteDriver.close();
	} 
	catch (Exception e) { 
		e.printStackTrace(); 
		System.out.println("Exception(Exception) in main"); 
	}
	finally { 
		System.out.println("-----------End of TestScript-------------"); 
	} 
	} 
} 
