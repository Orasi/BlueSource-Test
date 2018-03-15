package com.orasi.bluesource;

import org.openqa.selenium.support.FindBy;

import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Label;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.impl.internal.ElementFactory;

public class Admin {
private OrasiDriver driver = null;
	
	/**Page Elements**/
	@FindBy(xpath = "//a[contains(text(),'Add New Holiday')]") private Button btnAddNewHoliday;
	@FindBy(xpath = "//*[@id='content']/table/tbody/tr/td[1]") private Label lblFirstHolidayName;
	@FindBy(xpath = "//*[@id='content']/table/tbody/tr/td[2]") private Label lblFirstHolidayDate;
	@FindBy(xpath = "//*[@id='holiday_name']") private Textbox txtHolidayName;
	@FindBy(xpath = "//*[@id='holiday_date']") private Textbox txtHolidayDate;
	@FindBy(xpath = "//input[@value='Create Holiday']") private Button btnCreateHoliday;
	@FindBy(xpath = "//*[@id='notification-area']/div") private Label lblFailureMessage;
	
	/**Constructor**/
	public Admin(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}
	
	/**Page Interactions**/
	
	public void clickAddNewHoliday() {
		btnAddNewHoliday.syncVisible(2,true);
		btnAddNewHoliday.click();
	}
	
	/**
	 * Verifies the Add New Holiday button is onscreen
	 * @return - True if button is onscreen
	 */
	public boolean verifyAddNewHoliday() {
		return btnAddNewHoliday.syncVisible(2,true);
	}
	
	/**
	 * Retrives the first holiday name from the table
	 * @return - String with Holiday Name
	 */
	public String getFirstHolidayName() {
		return lblFirstHolidayName.getText();
	}
	
	/**
	 * Gets the first holiday listed's end date in form "YYYY-MM-DD"
	 * @return - String of the date
	 */
	public String getFirstHolidayDate() {
		return lblFirstHolidayDate.getText();
	}
	
	/**
	 * Verifies the Textbox for Holiday Name on the new Holiday screen appears
	 * @return - True if exists on screen
	 */
	public boolean verifyNewHolidayName() {
		return txtHolidayName.syncVisible(2,true);
	}
	
	/**
	 * Populates the New Holiday Name field with whats passed
	 * @param holidayName - Name of the new holiday
	 */
	public void populateHolidayname(String holidayName) {
		txtHolidayName.syncVisible(2,true);
		txtHolidayName.set(holidayName);
	}
	
	/**
	 * Populates the New Holiday date field in form "MMDDYYYY"
	 * @param holidayDate
	 */
	public void populateHolidayDate(String holidayDate) {
		String parsedDate = holidayDate.substring(5,7)+holidayDate.substring(8,10)+holidayDate.substring(0, 4);
		txtHolidayDate.sendKeys(parsedDate);
	}
	
	public void clickCreateHoliday() {
		btnCreateHoliday.syncVisible(2,true);
		btnCreateHoliday.click();
	}
	
	/**
	 * Verifies the notification message onscreen matches what is passed
	 * @param failMessage - Full Error message
	 * @return - True if what is shown onscreen matches what's passed
	 */
	public boolean verifyFailureMessage(String failMessage) {
		System.out.println(lblFailureMessage.getText().substring(2));
		return lblFailureMessage.getText().substring(2).equalsIgnoreCase(failMessage);
	}
}
