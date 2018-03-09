package com.orasi.bluesource;

import java.util.ResourceBundle;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import com.orasi.utils.Constants;
import com.orasi.web.OrasiDriver;
import com.orasi.web.PageLoaded;
import com.orasi.web.webelements.Label;
import com.orasi.web.webelements.Link;
import com.orasi.web.webelements.impl.internal.ElementFactory;


public class MailCatcher {
	private OrasiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);
	
	public int loadTime;
		
	/**Page Elements**/
	@FindBy(xpath = "//*[@id='messages']/table/tbody/tr[1]/td[2]") private Link lnkFirstEmailRecipient;
	@FindBy(xpath = "//html/body/div/div/p[1]") private Label lblFirstParagraph;
	@FindBy(xpath = "//html/body/div/div/p[2]") private Label lblSecondParagraph;
	@FindBy(xpath = "//*[@id='message']/header/nav/ul/li[3]/a") Link lnkSourceMessage;
	@FindBy(xpath = "//*[@id='message']/header/nav/ul/li[1]/a") Link lnkHTMLMessage;
	@FindBy(xpath = "//ul/li[1]/a[text()='BlueSource-User Guide-Timekeeping.pdf']") Link lnkEmailAttachment;
	
	/**Constructor**/
	public MailCatcher(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}
	
	/**Page Interactions**/

	public boolean verifyPageIsLoaded(){
		return PageLoaded.isElementLoaded(this.getClass(), driver, lnkFirstEmailRecipient);	
	}
	
	/**
	 * This method checks through the first 30 emails for a given account name, then clicks it
	 * @param accountName - Employee's firstName.lastName
	 * @author Andrew McGrail
	 */
	public void findRecipient(String accountName) {
		for(int i=1;i<30;i++) {
			if(driver.findElement(By.xpath("//*[@id='messages']/table/tbody/tr["+i+"]/td[2]")).getText().equalsIgnoreCase("<"+accountName+"@orasi.com>")) {
				driver.findElement(By.xpath("//*[@id='messages']/table/tbody/tr["+i+"]/td[2]")).click();
				break;
			}
		}
	}
	
	/**
	 * This method goes into the email already selected by switching the driver to the 
	 *  iFrame the email is inside, causing a warning, then checks to ensure the email was to 
	 *  the correct Employee Name.
	 * @param employeeAccountName - Employee account name to be checked for
	 * @return - True if the email has the correct name
	 * @author Andrew McGrail
	 */
	public boolean verifyEmailRecipient(String employeeAccountName) {
		try {
			driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@class='body']")));
			return lblFirstParagraph.getText().substring(6,employeeAccountName.length()+6).equalsIgnoreCase(employeeAccountName);
		}
		catch(Exception e) {
			return lblFirstParagraph.getText().substring(6,employeeAccountName.length()+6).equalsIgnoreCase(employeeAccountName);
		}
	}
	
	/**
	 * This method checks the selected email to make sure it has the same Account
	 *  listed as the one passed into this method.
	 * @param accountName - Account name to be checked against the email
	 * @return - True if the Account name in the email matchs the passed account name
	 * @author Andrew McGrail
	 */
	public boolean verifyEmailRole (String accountName) {
		return lblSecondParagraph.getText().substring(37,accountName.length()+37).equalsIgnoreCase(accountName);
	}
	
	public void clickAttachment() {
		driver.switchTo().defaultContent();
		lnkEmailAttachment.click();
	}
}