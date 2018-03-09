package com.orasi.bluesource;

import com.orasi.web.OrasiDriver;
import com.orasi.web.PageLoaded;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Listbox;
import com.orasi.web.webelements.impl.internal.ElementFactory;
import org.openqa.selenium.support.FindBy;

public class AccountBurnDownDataReportForm {
	private OrasiDriver driver = null;

	/**Page Elements**/
	@FindBy(xpath = "//input[@name='commit']") private Element elmGenerateReport;
	@FindBy(xpath = "//select[@id='account_select']") private Listbox lstAccountSelect;

	/**
	 * Constructor
	 **/
	public AccountBurnDownDataReportForm(OrasiDriver driver) {
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	/**Page Interactions**/

	public boolean verifyFormLoaded(){
		return PageLoaded.isElementLoaded(this.getClass(),driver,elmGenerateReport,5) &&
				PageLoaded.isElementLoaded(this.getClass(),driver,lstAccountSelect,5);
	}

	public void clickGenerateReport(){
		if (canInteract(elmGenerateReport))
			elmGenerateReport.click();
	}

	public void selectAccount(String strAccount){
		if (canInteract(lstAccountSelect)){
			lstAccountSelect.select(strAccount);
		}
	}

	private boolean canInteract(Element elm){
		return elm.syncEnabled(5) && elm.syncVisible(5);
	}
}