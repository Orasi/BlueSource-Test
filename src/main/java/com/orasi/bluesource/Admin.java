package com.orasi.bluesource;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.FindBy;

import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Label;
import com.orasi.web.webelements.Listbox;

import com.orasi.web.webelements.impl.internal.ElementFactory;

public class Admin {
	private OrasiDriver driver = null;
	
	/**Page Elements**/
	@FindBy(id= "date_month") private Listbox lbMonth;
	@FindBy(xpath = "//*[@value='Create Lock']") private Button btnCreateLock;
	@FindBy(xpath = "//*[@id='notification-area']/div") private Label lblSuccessMessage;
	
	/**Constructor**/
	public Admin(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	/**Page Interactions**/
	
	/**
	 * Selects the month of the year on the Timesheet Locks screen
	 * @param month - The month of the year to be selected
	 * @author Andrew McGrail
	 */
	public void selectMonth(String month) {
		lbMonth.syncVisible(2,true);
		lbMonth.select(month);
	}
	
	public void clickCreateLock() {
		btnCreateLock.syncVisible(2,true);
		btnCreateLock.click();
		driver.switchTo().alert().accept();
	}
	
	/**
	 * Matchs the passed String with the success message at the top of the
	 * timelock screen.
	 * @param message - Message to be matched
	 * @return True the message matchs
	 * @author Andrew McGrail
	 */
	public boolean verifySuccessMessage(String message) {
		return lblSuccessMessage.getText().substring(2, 2+message.length()).equalsIgnoreCase(message);			// Stops before the # of timesheets altered
	}
	
	/**
	 * This method verifies the Create Lock button exists on the page
	 * @return - True if Create Lock exists
	 * @author Andrew McGrail
	 */
	public boolean verifyCreateLock() {
		return btnCreateLock.syncVisible(1);
	}
	
	/**
	 * This method closes the timesheet lock for the month passed to it
	 * @param month - The month of the lock
	 * @author Andrew McGrail
	 */
	public void clickCloseTimesheet(String month) {
		for(int i=1;i<5;i++) {
			try {
				if(driver.findLabel(By.xpath("//tr["+i+"]/td[1]")).getText().equalsIgnoreCase(month)) {
					driver.findButton(By.xpath("//tr["+i+"]/td[3]/a/div")).click();
					driver.switchTo().alert().accept();
				}
			}
			catch(NoSuchElementException e) {
				break;
			}
		}
	}
}