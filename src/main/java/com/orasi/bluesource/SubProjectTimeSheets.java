package com.orasi.bluesource;

import com.orasi.web.OrasiDriver;
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
	public boolean hasTimeSheet(){
		return tblTimesheetSummary.getRowCount() > driver.findElements(By.xpath("//tr[@class='week_row']")).size();
	}

	public void goBackOneMonth(){
		lnkOneMonthBack.click();
	}

	public void goForwardOneMonth(){
		lnkOneMonthForward.click();
	}

	public String getSOW(){
		return tblTimesheetSummary.findElement(By.xpath("//div[@class='sow']")).getText().trim();
	}
}
