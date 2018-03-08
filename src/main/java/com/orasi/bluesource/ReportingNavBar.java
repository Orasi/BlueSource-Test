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
	@FindBy(xpath = "//span[contains(text(),'Projects Reports')]/..") private Link lnkProjectsReport;
	@FindBy(xpath = "//span[contains(text(),'Projects Reports')]/../..//a[contains(text(),'Time by Time Sheet')]") private Link lnkProjectsReportTimeByTimeSheet;

	/**Constructor**/
	public ReportingNavBar(OrasiDriver driver) {
		this.driver = driver;
		ElementFactory.initElements(driver,this);
	}

	/**Page Interactions**/

	/**
	 * This method expands the Projects Reports drop down
	 * @author David Grayson
	 */
	public void clickProjectsReport(){
		if (canInteract(lnkProjectsReport))
			lnkProjectsReport.click();
	}

	/**
	 * This method clicks on the "Time by Time Sheet" link in the Projects Reports dropdown
	 * @author David Grayson
	 */
	public void clickProjectsReportTimeByTimeSheet(){
		if (canInteract(lnkProjectsReportTimeByTimeSheet))
			lnkProjectsReportTimeByTimeSheet.click();
	}

	/**
	 * This method provides standard checks that an element can be interacted with
	 * @author David Grayson
	 * @param elm {@link Element} Element to check
	 * @return {@link Boolean} Returns <code>true</code> if the element is enabled and visible, <code>false</code> otherwise
	 */
	private boolean canInteract(Element elm){
		return elm.syncEnabled(5) && elm.syncVisible(5);
	}

	/**
	 * @author David Grayson
	 * @return {@link Boolean} Returns <code>true</code> if on the Reporting Home page.
	 */
	public boolean verifyHomePageIsDisplayed(){
		return elmWelcome.syncVisible(5,false);
	}
}
