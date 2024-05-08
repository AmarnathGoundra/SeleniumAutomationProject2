package com.mop.qa.test.bvt;


import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.mop.qa.Utilities.ReadDataSheet;
import com.mop.qa.testbase.TestBase; 
import SOURCE_CODE.SFDC.DB; 
import USER_SPACE.BusinessComponent.BC; 
import SOURCE_CODE.SFDC.SFDCAutomationFW; 
import USER_SPACE.TestPrerequisite.DataSetup;
//import io.appium.java_client.android.nativekey.KeyEvent;
import USER_SPACE.ObjectRepository.AccountsScreen_LUI;
import USER_SPACE.ObjectRepository.AllAppPage_LUI;
import USER_SPACE.ObjectRepository.AllAppsTabsScreen_LUI;
import USER_SPACE.ObjectRepository.ContactsScreen_LUI;
import USER_SPACE.ObjectRepository.OpportunitiesScreen_LUI;
import USER_SPACE.ObjectRepository.OpportunitiesScreen_LUI_stnd;

/* 
* 
* @Author: <Name of Test Script Creator> 
* @Description: <Please mention the scope of this test script> 
* @General Guidelines: Every Test Script must begin from Launching 
* URL of login screen and must end with browser closed 
*/ 

public class CAAS_Opportunities extends TestBase { 

@Test	
public void createMyTest() { 

	SFDCAutomationFW sfdc = null; 
	OpportunitiesScreen_LUI_stnd opportunities = null; 
	AllAppPage_LUI allAppScreen = null;
	AllAppsTabsScreen_LUI allTAppsObjectsScreen = null;
	String CAAS_Opportunities = "CAAS_Opportunities";

	String TCName = "CAAS_Opportunities"; 
	if (toolName.equalsIgnoreCase("Selenium"))
	{ 
		System.out.println("----------------->Inside Test Case");
		sfdc = new SFDCAutomationFW(remoteDriver, TCName);
		System.out.println("----------->Back to Test case");
		opportunities = new OpportunitiesScreen_LUI_stnd(remoteDriver); 
		allTAppsObjectsScreen = new AllAppsTabsScreen_LUI(remoteDriver);
		allAppScreen = new AllAppPage_LUI(remoteDriver);
		
	}
	else if (toolName.equalsIgnoreCase("Appium"))
	{
		sfdc = new SFDCAutomationFW(appiumDriver, TCName);
		opportunities = new OpportunitiesScreen_LUI_stnd(appiumDriver); 
		allTAppsObjectsScreen = new AllAppsTabsScreen_LUI(appiumDriver);
	}
	
	DB DB = new DB();
	BC BC = new BC(remoteDriver);
	DataSetup DataSetup = new DataSetup();

	System.out.println("-----------Begin of TestScript-------------");

	try { 
			 
		DB.Connect(DataSetup.TestData);
			
		//Reading the test data from external test data sheet
		String ACCOUNT_NAME = DB.ReadXLData("CreateAccount", "ACCOUNT_NAME_SAVED", "TESTCASENAME", "CAAS_CreateAccount");
		String CONTACT_NAME = DB.ReadXLData("CreateAccount", "CONTACT_NAME", "TESTCASENAME", "CAAS_CreateContact");
		String OPPORTUNITY_NAME = DB.ReadXLData("CreateOpportunity", "OPPORTUNITY_NAME", "TESTCASENAME", TCName);
		String AMOUNT = DB.ReadXLData("CreateOpportunity", "AMOUNT", "TESTCASENAME", TCName);
//		String CLOSE_DATE = DB.ReadXLData("CreateOpportunity", "CLOSE_DATE", "TESTCASENAME", TCName);
		String STAGE = DB.ReadXLData("CreateOpportunity", "STAGE", "TESTCASENAME", TCName);
		String PROBABILITY = DB.ReadXLData("CreateOpportunity", "PROBABILITY", "TESTCASENAME", TCName);
		String LEAD_SOURCE = DB.ReadXLData("CreateOpportunity", "LEAD_SOURCE", "TESTCASENAME", TCName);	
		
//		//DB.Connect(DataSetup.Logininfo);
//		//ReadDataSheet rds = new ReadDataSheet();
//		//String OWNER =  rds.getValue("LoginInfo", SFDCLoginUserName, "Name");
				
		OPPORTUNITY_NAME = ACCOUNT_NAME+"-"+OPPORTUNITY_NAME;
		Thread.sleep(1000L);
		
		// Login to SFDC 
		sfdc.LoginToSFDC(SFDCLoginUserName); //This variable is inherited from TestBase
		Thread.sleep(3000L);
		
		//Selecting Sales app from app launcher
		sfdc.SelectApplication_LUI("Sales");
		Thread.sleep(3000L);
		
		allTAppsObjectsScreen.OpportunitiesTab().Click();
		Thread.sleep(3000L);
		
		opportunities.NewButton().Click();
		Thread.sleep(4000L);
		opportunities.SaveButton().Click();
		Thread.sleep(3000L);
		
		sfdc.VerifyPageLevelErrorMessage_LUI("Review the following fields");
		opportunities.OpportunityNameField().VerifyFieldErrorMsgOnEditPage("Complete this field.");
							
		opportunities.OpportunityNameField().Type(OPPORTUNITY_NAME);
		opportunities.AmountField().Type(AMOUNT);
		opportunities.CloseDateField().SelectFromDateLookup("2024", "March", "20");
		opportunities.StageField().SelectPL_Contains(STAGE);
		opportunities.LeadSourceField().SelectPL(LEAD_SOURCE);
		opportunities.ProbabilityField().Type(PROBABILITY);
		opportunities.AccountNameField().SelectFromLookup(ACCOUNT_NAME);
		opportunities.SaveButton().Click();	
		Thread.sleep(2000L);
		opportunities.AddLogToCustomReport("Validated, Successfully Opportunity Created", "Pass");
		Thread.sleep(5000L);
		
		//Products

		sfdc.remoteDriver.findElement(By.xpath("//article[@aria-label='Products']/descendant::div[@class='actionsContainer']/descendant::lightning-icon")).click();
		sfdc.remoteDriver.findElement(By.xpath("//div[text()='Add Products']/ancestor::div[@role='menu']")).click();
		opportunities.AddLogToCustomReport("Clicked on the button (Add Products).", "Pass");
		Thread.sleep(4000L);
		
		sfdc.remoteDriver.findElement(By.xpath("//div[@role='dialog']/descendant::a")).click();
		Thread.sleep(2000L);
		sfdc.remoteDriver.findElement(By.xpath("//div[@class='select-options']/descendant::a[text()='Amazon Great Sale Discoount']")).click();
		Thread.sleep(2000L);
		sfdc.remoteDriver.findElement(By.xpath("//div[@role='dialog']/descendant::button[@type='button']/span[text()='Save']")).click();
		//opportunities.SaveButton().Click();
		Thread.sleep(4000L);
		sfdc.remoteDriver.findElement(By.xpath("//h2[text()='Add Products']/ancestor::div[@role='dialog']/descendant::table[@role='grid']/thead/descendant::div[contains(@class, 'checkbox-container')]")).click();
		sfdc.remoteDriver.findElement(By.xpath("//div[@role='dialog']/descendant::button[@type='button']/span[text()='Next']")).click();
		Thread.sleep(2000L);
		
		WebElement quantity=sfdc.remoteDriver.findElement(By.xpath("(//div[@class=\"modal-container slds-modal__container\"]//span[@class=\"triggerContainer\"])[2]"));
		quantity.click();
		sfdc.remoteDriver.findElement(By.xpath("//span[text()='Quantity']/ancestor::div[@class='container']/descendant::input")).sendKeys("5000");
		sfdc.remoteDriver.findElement(By.xpath("//div[@role='dialog']/descendant::button[@type='button']/span[text()='Save']")).click();
		Thread.sleep(2000L);
		opportunities.AddLogToCustomReport("Validated, Successfully Products Created", "Pass");
		Thread.sleep(4000L);
		
		//Contacts Roles
		
		opportunities.ScrollToElement(sfdc.remoteDriver.findElement(By.xpath("//span[text()='Notes & Attachments']")));
		Thread.sleep(2000L);
		sfdc.remoteDriver.findElement(By.xpath("//article[@aria-label='Contact Roles']/descendant::div[@class='actionsContainer']/descendant::lightning-icon")).click();
		sfdc.remoteDriver.findElement(By.xpath("//div[@role='menu']/descendant::div[text()='Add Contact Roles']/ancestor::li")).click();
		opportunities.AddLogToCustomReport("Clicked on the button (Add Contact Roles).", "Pass");
		Thread.sleep(4000L);
				
		sfdc.remoteDriver.findElement(By.xpath("//div[@class='headerRegion']/descendant::input")).click();
		Thread.sleep(2000L);
		sfdc.remoteDriver.findElement(By.xpath("//div[@class='headerRegion']/descendant::input")).sendKeys(CONTACT_NAME);
		Thread.sleep(2000L);
		sfdc.remoteDriver.findElement(By.xpath("//div[@class='headerRegion']/descendant::input")).click();
		Thread.sleep(2000L);
		sfdc.remoteDriver.findElement(By.xpath("//div[@role='listbox']/descendant::li[1]")).click();
		Thread.sleep(2000L);
		sfdc.remoteDriver.findElement(By.xpath("//h2[text()='Add Contact Roles']/ancestor::div[@role='dialog']/descendant::button[@type='button']/span[text()='Next']")).click();
		//opportunities.SaveButton().Click();
		Thread.sleep(4000L);
		sfdc.remoteDriver.findElement(By.xpath("//h2[text()='Add Contact Roles']/ancestor::div[@role='dialog']/descendant::button[@type='button']/span[text()='Save']")).click();
		Thread.sleep(2000L);
		opportunities.AddLogToCustomReport("Validated, Successfully Contact Roles Created", "Pass");
		Thread.sleep(4000L);

//		//New Task
//		opportunities.ScrollToElement(sfdc.remoteDriver.findElement(By.xpath("//p[text()='Account Name']")));
		opportunities.ReloadWebPage();
		Thread.sleep(6000L);
		
		sfdc.remoteDriver.findElement(By.xpath("//button[@title='New Task']")).click();
		Thread.sleep(2000L);
		sfdc.remoteDriver.findElement(By.xpath("//label[text()='Subject']/ancestor::lightning-grouped-combobox/descendant::input")).sendKeys("DemoTask");
		Thread.sleep(2000L);
		sfdc.remoteDriver.findElement(By.xpath("//h2[text()='New Task']/ancestor::div[@role='dialog']/div[@class='slds-docked-composer__body']/div/div[2]/div/div[2]/div[2]")).click();
		Thread.sleep(2000L);
		opportunities.AddLogToCustomReport("Validated, Successfully New Task Created", "Pass");
		Thread.sleep(4000L);

//		//New Event
		sfdc.remoteDriver.findElement(By.xpath("//button[@title='New Event']")).click();
		Thread.sleep(2000L);
		sfdc.remoteDriver.findElement(By.xpath("//label[text()='Subject']/ancestor::lightning-grouped-combobox/descendant::input")).sendKeys("DemoEvent");
		Thread.sleep(2000L);
		sfdc.remoteDriver.findElement(By.xpath("//h2[text()='New Event']/ancestor::div[@role='dialog']/div[@class='slds-docked-composer__body']/div/div[2]/div/div[2]/div[2]")).click();
		Thread.sleep(2000L);
		opportunities.AddLogToCustomReport("Validated, Successfully New Event Created", "Pass");
		Thread.sleep(4000L);
		
		//Upload
		opportunities.ScrollToElement(sfdc.remoteDriver.findElement(By.xpath("//a[text()='Related']")));
		Thread.sleep(4000L);
		sfdc.remoteDriver.findElement(By.xpath("//span[text()='Upload Files']")).click();
		
		Robot rb = new Robot();
		rb.delay(4000);
		//file path in clipboard
		StringSelection ss = new StringSelection("C:\\Users\\2327595\\Downloads\\01 Microsoft Dynamics CRM Overview v1.0.pptx");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);//copy the path
		rb.keyPress(KeyEvent.VK_CONTROL);//for pasting the path
		rb.keyPress(KeyEvent.VK_V);
		rb.delay(2000);
		rb.keyRelease(KeyEvent.VK_CONTROL);
		rb.keyRelease(KeyEvent.VK_V);
		rb.delay(2000);
		rb.keyPress(KeyEvent.VK_ENTER);//for clicking enter button
		rb.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(5000);
		
		sfdc.remoteDriver.findElement(By.xpath("//span[text()='Done']")).click();
		Thread.sleep(2000L);
		opportunities.AddLogToCustomReport("Validated, Successfully File Uploaded", "Pass");
		Thread.sleep(4000L);
//		opportunities.ScrollToElement(sfdc.remoteDriver.findElement(By.xpath("//p[text()='Account Name']")));
		opportunities.ReloadWebPage();
		Thread.sleep(6000L);
		
		// Stage Won
		sfdc.ClickONSubTab("Details");
		Thread.sleep(4000L);
		opportunities.ScrollToElement(sfdc.remoteDriver.findElement(By.xpath("//a[text()='Related']")));
		Thread.sleep(4000L);
		sfdc.remoteDriver.findElement(By.xpath("//button[@title='Edit Stage']/span[1]")).click();
//		opportunities.ScrollToElement(sfdc.remoteDriver.findElement(By.xpath("//label[text()='Amount']")));
		Thread.sleep(2000L);
		sfdc.remoteDriver.findElement(By.xpath("//button[contains(@aria-label, 'Stage')]")).click();
		opportunities.ScrollToElement(sfdc.remoteDriver.findElement(By.xpath("//div[@part='combobox']/descendant::span[text()='Closed Won']")));
		Thread.sleep(2000L);
		sfdc.remoteDriver.findElement(By.xpath("//div[@part='combobox']/descendant::span[text()='Closed Won']")).click();
		sfdc.remoteDriver.findElement(By.xpath("//div[@class='footer-full-width']/descendant::button[text()='Save']")).click();
		Thread.sleep(2000L);
		opportunities.ReloadWebPage();
		Thread.sleep(4000L);
		opportunities.AddLogToCustomReport("Validated, Successfully Opportunity Closed Won", "Pass");
		Thread.sleep(4000L);
				
		DB.Connect(DataSetup.TestData);		
		
//		Update excel with full name
		DB.UpdateXLCell("CreateOpportunity", OPPORTUNITY_NAME, "OPPORTUNITY_NAME_CREATED", "TESTCASENAME", TCName);
		
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
