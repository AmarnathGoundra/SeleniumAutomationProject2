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
import USER_SPACE.ObjectRepository.AllAppsTabsScreen_LUI;
import USER_SPACE.ObjectRepository.ContactsScreen_LUI; 

/* 
* 
* @Author: <Name of Test Script Creator> 
* @Description: <Please mention the scope of this test script> 
* @General Guidelines: Every Test Script must begin from Launching 
* URL of login screen and must end with browser closed 
*/ 

public class CAAS_CreateAccount extends TestBase {

@Test	
public void createMyTest() { 

	SFDCAutomationFW sfdc = null; 
	AccountsScreen_LUI accountsScreen = null; 
	ContactsScreen_LUI contactsScreen = null;
	AllAppsTabsScreen_LUI allTAppsObjectsScreen = null;
	String CAAS_CreateAccount = "CAAS_CreateAccount";

	String TCName = "CAAS_CreateAccount"; 
	if (toolName.equalsIgnoreCase("Selenium"))
	{ 
		System.out.println("----------------->Inside Test Case");
		sfdc = new SFDCAutomationFW(remoteDriver, TCName);
		System.out.println("----------->Back to Test case");
		accountsScreen = new AccountsScreen_LUI(remoteDriver); 
		contactsScreen = new ContactsScreen_LUI(remoteDriver);
		allTAppsObjectsScreen = new AllAppsTabsScreen_LUI(remoteDriver);
	}
	else if (toolName.equalsIgnoreCase("Appium"))
	{
		sfdc = new SFDCAutomationFW(appiumDriver, TCName);
		accountsScreen = new AccountsScreen_LUI(appiumDriver); 
		contactsScreen = new ContactsScreen_LUI(remoteDriver);
		allTAppsObjectsScreen = new AllAppsTabsScreen_LUI(appiumDriver);
	}
	
	DB DB = new DB();
	BC BC = new BC(remoteDriver);
	DataSetup DataSetup = new DataSetup();

	System.out.println("-----------Begin of TestScript-------------");

	try { 
			 
		DB.Connect(DataSetup.TestData);
			
		//Reading the test data from external test data sheet
		String ACCOUNT_NAME = DB.ReadXLData("CreateAccount", "ACCOUNT_NAME", "TESTCASENAME", TCName);
		String RATING = DB.ReadXLData("CreateAccount", "RATING", "TESTCASENAME", TCName);
		String PHONE = DB.ReadXLData("CreateAccount", "PHONE", "TESTCASENAME", TCName);
		String ACCOUNT_NUMBER = DB.ReadXLData("CreateAccount", "ACCOUNT_NUMBER", "TESTCASENAME", TCName);
		String ACCOUNT_SITE = DB.ReadXLData("CreateAccount", "ACCOUNT_SITE", "TESTCASENAME", TCName);
		String TYPE = DB.ReadXLData("CreateAccount", "TYPE", "TESTCASENAME", TCName);
		String INDUSTRY = DB.ReadXLData("CreateAccount", "INDUSTRY", "TESTCASENAME", TCName);
		String SLA = DB.ReadXLData("CreateAccount", "SLA", "TESTCASENAME", TCName);
		String ACTIVE = DB.ReadXLData("CreateAccount", "ACTIVE", "TESTCASENAME", TCName);
		String OWNERSHIP = DB.ReadXLData("CreateAccount", "OWNERSHIP", "TESTCASENAME", TCName);
		String UPSELL_OPPORTUNITY = DB.ReadXLData("CreateAccount", "UPSELL_OPPORTUNITY", "TESTCASENAME", TCName);
		String CUSTOMER_PRIORITY = DB.ReadXLData("CreateAccount", "CUSTOMER_PRIORITY", "TESTCASENAME", TCName);
				
				
		
		//DB.Connect(DataSetup.Logininfo);
		ReadDataSheet rds = new ReadDataSheet();
		String OWNER =  rds.getValue("LoginInfo", SFDCLoginUserName, "Name");
				
		ACCOUNT_NAME = ACCOUNT_NAME + BC.GetCurrentDateTimeStamp();
		Thread.sleep(2000L);
		
		// Login to SFDC 
		sfdc.LoginToSFDC(SFDCLoginUserName); //This variable is inherited from TestBase
				
		
		Thread.sleep(3000L);
		sfdc.SelectApplication_LUI("Sales");
		Thread.sleep(6000L);
		
		allTAppsObjectsScreen.AccountsTab().Click();
		Thread.sleep(4000L);
		
		accountsScreen.NewButton().Click();
		Thread.sleep(4000L);
		accountsScreen.SaveButton().Click();
		Thread.sleep(4000L);
		
		accountsScreen.AccountNameField().VerifyFieldErrorMsgOnEditPage("Complete this field.");
		sfdc.VerifyPageLevelErrorMessage_LUI("Review the following fields");
		Thread.sleep(2000L);
		
		//Enter the data 
		accountsScreen.AccountNameField().Type(ACCOUNT_NAME);
		accountsScreen.AccountNumberField().Type(ACCOUNT_NUMBER);
		accountsScreen.AccountSiteField().Type(ACCOUNT_SITE);
		accountsScreen.PhoneField().Type(PHONE);
		Thread.sleep(1000);
		
		accountsScreen.RatingField().SelectPL(RATING);
		accountsScreen.TypeField().SelectPL(TYPE);
		accountsScreen.IndustryField().SelectPL(INDUSTRY);
		accountsScreen.OwnershipField().SelectPL(OWNERSHIP);
		accountsScreen.SLAField().SelectPL(SLA);		
		accountsScreen.UpsellOpportunityField().SelectPL(UPSELL_OPPORTUNITY);		
		accountsScreen.ActiveField().SelectPL(ACTIVE);		
		accountsScreen.CustomerPriorityField().SelectPL(CUSTOMER_PRIORITY);	
		Thread.sleep(1000L);
		
		accountsScreen.SaveButton().Click();
		Thread.sleep(1000L);
		sfdc.remoteDriver.findElement(By.xpath("//div[@class='forceVisualMessageQueue' ]/div/div/div/div/div/span[contains(text(), 'Account')]"));
		Thread.sleep(1000L);
		accountsScreen.AddLogToCustomReport("Validated, Successfully Account Created", "Pass");
		Thread.sleep(4000L);
		
		String msgSuccess = "Account \""+ACCOUNT_NAME+"\" was created.";
		System.out.println(msgSuccess);
		
		Thread.sleep(10000L);
		
		//Verify Default Values and Prepopulated Values
		
		DB.Connect(DataSetup.TestData);		
		
		//Update excel with full name
		DB.UpdateXLCell("CreateAccount", ACCOUNT_NAME, "ACCOUNT_NAME_SAVED", "TESTCASENAME", TCName);
		
		
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
