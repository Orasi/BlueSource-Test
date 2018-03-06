package com.orasi.bluesource;

import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Link;
import com.orasi.web.webelements.impl.internal.ElementFactory;
import org.openqa.selenium.support.FindBy;

public class ReportsNavBar {
	private OrasiDriver driver = null;

	/**Page Elements**/
	@FindBy(xpath = "//span[contains(text(),'Account Reports')]/..") private Link lnkAccountReportsDropdown;
	@FindBy(xpath = "//span[contains(text(),'Account Reports')]/../..//a[contains(text(),'Time by Time Sheet')]") private Link lnkAccountTimeByTimeSheet;

	/**Constructor**/
	public ReportsNavBar(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver,this);
	}

	/**Page Interactions**/
	public void clickAccountTimeByTimeSheet(){
		lnkAccountTimeByTimeSheet.syncEnabled(5);
		lnkAccountTimeByTimeSheet.syncVisible(5);
		lnkAccountTimeByTimeSheet.click();
	}

	public void clickAccountReportsDropdown(){
		lnkAccountReportsDropdown.syncEnabled(5);
		lnkAccountReportsDropdown.syncVisible(5);
		lnkAccountReportsDropdown.click();
	}
}
