package com.orasi.bluesource;

import java.util.ResourceBundle;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;

import com.orasi.utils.Constants;
import com.orasi.utils.TestReporter;
import com.orasi.web.OrasiDriver;
import com.orasi.web.PageLoaded;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Label;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.Webtable;
import com.orasi.web.webelements.impl.internal.ElementFactory;


public class LoginPage {
	private OrasiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);
	
	public int loadTime;
		
	/**Page Elements**/
	@FindBy(id = "employee_username") private Textbox txtUsername;
	@FindBy(id = "employee_password") private Textbox txtPassword;
	@FindBy(xpath = "//input[@value='Login']") private Button btnLogin;
	@FindBy(xpath = "//h1[contains(.,'Welcome')]") private Label lblWelcome;
	@FindBy(xpath = "//img[@class = 'img-responsive brand']") private Element elmLogo;
	@FindBy(xpath  ="(//table[@class='table'])[3]") private Webtable wtbDetails;
	@FindBy(xpath = "//*[@id='content']/div[2]/div/h3") private Label lblBadLogin;
	@FindBy(xpath = "//*[@id='time-entry-table']/tbody/tr[2]/td[1]/span/span[1]/span/span[2]") private Button btnTimesheetDropdown;
	@FindBy(xpath = "/html/body/span/span/span[1]/input") private Textbox txtTimesheetDropdown;
	@FindBy(xpath = "//*[@id='employee_reported_times___date_hours_hours']") private Webtable wtHoursTable;
	
	/**Constructor**/
	public LoginPage(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}
	
	/**Page Interactions**/

	public boolean verifyPageIsLoaded(){
		return PageLoaded.isElementLoaded(this.getClass(), driver, txtUsername);	
	}
		
	/**
	 * This method logins to the application with provided credentials
	 * @author
	 * @param username
	 * @param password
	 */
	public boolean Login(String role){
		txtUsername.set(userCredentialRepo.getString(role));
		txtPassword.set(userCredentialRepo.getString("PASSWORD"));
		btnLogin.syncVisible(90, false);
		btnLogin.jsClick();
		
		return PageLoaded.isElementLoaded(this.getClass(), driver, elmLogo);
	}
	
	public void AdminLogin(){
		Login("ADMIN");
	}

	
	/*
	 * Method logs you into the page with the username and password 
	 * passed as the parameters
	 * @author:
	 */
	public void LoginWithCredentials(String username, String password) {
		txtUsername.set(username);
		txtPassword.set(password);
		btnLogin.syncVisible(90, false);
		btnLogin.jsClick();
	}
	
	/*
	 * Method checks that the label welcome is present on the screen
	 * @author: Daniel Smith
	 */
	public void check_login(String username)
	{
		if (lblWelcome.isDisplayed() == true)
		{
			System.out.println("On the correct page. User: "+ username + " has logged in. ");
			TestReporter.assertEquals(lblWelcome.isDisplayed(), true, "Welcome label present");
		}
		else
		{
			System.out.println("Was not able to login with: " + username );
		}
	}
	
	/*
	 * Method checks that invalid login name and password is handled
	 * @author: Daniel Smith
	 */
	public void invalid_login()
	{
		String innerText = lblBadLogin.getText();
		
		System.out.println("User not logged in or found.. \n" + "Output from the current page: " + innerText);
	}
	
	/**
	 * This method determines if the hours entered into the timesheet are shown
	 * @return boolean - If the input hours and displayed hours match
	 * @author Andrew McGrail
	 */
	public boolean checkTimesheetHours() {
		String whatWeSet=null;
		String whatIsShown=null;
		boolean answer=true;
		
		for(int i=1;i<7;i++) {
			whatWeSet = driver.findTextbox(By.xpath("(//*[@id='employee_reported_times___date_hours_hours'])["+i+"]")).getText();
			whatIsShown=driver.findLabel(By.xpath("//tr[6]/td["+(i+1)+"]")).getText();
			if(!(whatWeSet.equals(whatIsShown)))
				answer=false;
		}
		return answer;
	}
	
	/**
	 * This method sets the "Non-billable type" for the Current Timesheet
	 * @param type - The non-billable type
	 * @author Andrew McGrail
	 */
	public void setTimesheetDropdown(String type) {
		txtTimesheetDropdown.sendKeys(type+Keys.RETURN);
	}
	
	/**
	 * This method sets the hours for the timesheet
	 * @author Andrew McGrail
	 */
	public void setTimesheetHours() {
		for(int i=1;i<8;i++) {
			driver.findTextbox(By.xpath("(//*[@id='employee_reported_times___date_hours_hours'])["+i+"]")).set(String.valueOf(i*2));
		}
	}
	
	public void clickTimesheetDropdown() {
		btnTimesheetDropdown.syncVisible(2,true);
		btnTimesheetDropdown.click();
	}	
}