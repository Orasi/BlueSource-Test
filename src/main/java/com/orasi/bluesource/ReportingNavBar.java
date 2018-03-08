package com.orasi.bluesource;

import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Link;
import com.orasi.web.webelements.impl.internal.ElementFactory;
import org.openqa.selenium.support.FindBy;

public class ReportingNavBar {
	private OrasiDriver driver = null;

	/**Page Elements**/
	@FindBy(xpath = "//h1[text()='Welcome']") private Element elmWelcome;
	@FindBy(xpath = "//span[contains(text(),'Employee Reports')]/..") private Link lnkEmployeeReportsDropDown;
	@FindBy(xpath = "//span[contains(text(),'Employee Reports')]/../..//a[contains(text(),'Time by Time Sheet')]") private Link lnkEmployeeTimeByTimeSheet;

	/**Constructor**/
	public ReportingNavBar(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver,this);
	}

	/**Page Interactions**/

	/**
	 * @author David Grayson
	 * @return {@link Boolean} Returns <code>true</code> if on the Reporting Home page.
	 */
	public boolean verifyHomePageIsDisplayed(){
		return elmWelcome.syncVisible(5,false);
	}

	/**
	 * This method expands the Employee Reports drop down
	 * @author David Grayson
	 */
	public void clickEmployeeReportsDropDown(){
		if (lnkEmployeeReportsDropDown.syncEnabled(5) && lnkEmployeeReportsDropDown.syncVisible(5))
			lnkEmployeeReportsDropDown.click();
	}

	/**
	 * This method clicks on the "Time by Time Sheet" link in the Employee Reports dropdown
	 * @author David Grayson
	 */
	public void clickEmployeeTimeByTimeSheet(){
		if (lnkEmployeeTimeByTimeSheet.syncEnabled(5) && lnkEmployeeTimeByTimeSheet.syncVisible(5))
			lnkEmployeeTimeByTimeSheet.click();
	}
}