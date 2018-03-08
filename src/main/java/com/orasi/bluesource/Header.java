package com.orasi.bluesource;

import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Link;
import com.orasi.web.webelements.impl.internal.ElementFactory;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.FindBy;

public class Header {
	private OrasiDriver driver = null;
	
	/**Page Elements**/
	@FindBy(linkText = "Accounts") private Link lnkAccounts;
	@FindBy(linkText = "Logout") private Link lnkLogout;
	@FindBy(xpath = "//li[contains(.,'Employees')]/a") private Link lnkEmployees;
	@FindBy(xpath = "//a[contains(text(),'Project')]") private Link lnkProjemployees;
	@FindBy(xpath = "//a[contains(text(),'Project')]//..//..//..//following-sibling::a") private Link lnkEmployeeSelector;
	
	/**Constructor**/
	public Header(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}
	
	/**Page Interactions**/

	/**
	 * This method navigates to the Reporting login page
	 * @author David Grayson
	 */
	public void navigateReporting(){
		MessageCenter messageCenter = new MessageCenter(driver);
		messageCenter.closeMessageCenter();
		driver.get("http://10.238.243.127:8080/reporting/login");
	}

	/**
	 * This method navigates to Accounts page
	 * @author Paul
	 */
	public void navigateAccounts(){
		
		boolean accountBool = lnkAccounts.isDisplayed();
		
		//Displaying the boolean current status
		System.out.println("Output from account bool: " + accountBool);
		
		//Some users do not have the accounts permissions so the tab isnt present
		try
		{
			if(accountBool == true)
			{
				lnkAccounts.click();
				System.out.println("This user has account permissions");
			}
			else if (accountBool == false)
			{
				System.out.println("The user does not have account permissions");
			}
		}
		catch(NoSuchElementException e)
		{
			System.out.println("Element can not be found for accounts tab \n" + e.getLocalizedMessage());
		}
	}

	/**
	 * This method navigates to Employees page
	 * @author Paul
	 */
	public void navigateEmployees() {
		MessageCenter messageCenter = new MessageCenter(driver);
		messageCenter.closeMessageCenter();
		lnkEmployees.click();		
	}
	
	public void navigateProjectEmployees() {
		MessageCenter messageCenter = new MessageCenter(driver);
		messageCenter.closeMessageCenter();
		lnkEmployeeSelector.click();
		lnkProjemployees.click();
		
	}
	
	public void navigateLogout() {
		MessageCenter messageCenter = new MessageCenter(driver);
		messageCenter.closeMessageCenter();
		lnkLogout.click();		
	}

}