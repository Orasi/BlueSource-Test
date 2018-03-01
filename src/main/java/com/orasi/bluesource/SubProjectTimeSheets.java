package com.orasi.bluesource;

import com.orasi.web.OrasiDriver;
import com.orasi.web.PageLoaded;
import com.orasi.web.webelements.Link;
import com.orasi.web.webelements.Webtable;
import com.orasi.web.webelements.impl.internal.ElementFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class SubProjectTimeSheets {
	private OrasiDriver driver = null;

	/**Constructor**/
	public SubProjectTimeSheets(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	/**Page Elements**/
	@FindBy(xpath = "//table[@class='time-summary-table table']") private Webtable tblTimesheetSummary;
	@FindBy(xpath = "//div[@class='pull-left']/a") private Link lnkOneMonthBack;
	@FindBy(xpath = "//div[@class='pull-right']/a") private Link lnkOneMonthForward;

	/**Page Interactions**/
	/**
	 * Returns true if there are timesheet rows in the table
	 * @author david.grayson
	 * @return <code>true</code> if there are time sheets, <code>false</code> if not.
	 */
	public boolean hasTimeSheet(){
		return tblTimesheetSummary.getRowCount() > driver.findElements(By.xpath("//tr[@class='week_row']")).size();
	}

	public void goBackOneMonth(){
		PageLoaded.isDomComplete(driver);
		lnkOneMonthBack.click();
	}

	public void goForwardOneMonth(){
		lnkOneMonthForward.syncEnabled(3);
		lnkOneMonthForward.click();
	}

	public String getSOW(){
		return tblTimesheetSummary.findElement(By.xpath("//div[@class='sow']")).getText().trim();
	}
}
