package com.orasi.bluesource;

import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Listbox;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.impl.internal.ElementFactory;
import org.openqa.selenium.support.FindBy;

public class AccountTimeByTimeSheetForm {
	private OrasiDriver driver = null;

	/**Page Elements**/
	@FindBy(xpath = "//select[@id='account_select']") private Listbox lstAccountSelect;
	@FindBy(xpath = "//input[@name='start_date']") private Textbox txtStartDate;
	@FindBy(xpath = "//input[@name='end_date']") private Textbox txtEndDate;
	@FindBy(xpath = "//input[@name='commit']") private Element elmGenerateReport;

	/**Constructor**/
	public AccountTimeByTimeSheetForm(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver,this);
	}

	/**Page Interactions**/
	public void selectAccount(String strAccount){
		lstAccountSelect.syncEnabled(5);
		lstAccountSelect.syncVisible(5);
		lstAccountSelect.select(strAccount);
	}

	public void setStartDate(String strStartDate){
		txtStartDate.syncEnabled(5);
		txtStartDate.syncVisible(5);
		txtStartDate.clear();
		txtStartDate.sendKeys(strStartDate);
	}

	public void setEndDate(String strEndDate){
		txtEndDate.syncEnabled(5);
		txtEndDate.syncVisible(5);
		txtEndDate.clear();
		txtEndDate.sendKeys(strEndDate);
	}

	public void clickGenerateReport(){
		elmGenerateReport.syncEnabled(5);
		elmGenerateReport.syncVisible(5);
		elmGenerateReport.click();
	}

}
