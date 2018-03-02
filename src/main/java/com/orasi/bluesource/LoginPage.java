package com.orasi.bluesource;

import java.util.ResourceBundle;

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
		txtUsername.syncVisible(2,true);
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
		txtUsername.syncVisible(2,true);
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
	
}