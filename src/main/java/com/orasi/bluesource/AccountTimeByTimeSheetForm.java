package com.orasi.bluesource;

import com.orasi.web.OrasiDriver;
import com.orasi.web.PageLoaded;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Listbox;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.impl.internal.ElementFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class AccountTimeByTimeSheetForm {
	private OrasiDriver driver = null;

	/**Page Elements**/
	@FindBy(xpath = "//select[@id='account_select']") private Listbox lstAccountSelect;
	@FindBy(xpath = "//input[@name='start_date']") private Textbox txtStartDate;
	@FindBy(xpath = "//input[@name='end_date']") private Textbox txtEndDate;
	@FindBy(xpath = "//input[@name='commit']") private Element elmGenerateReport;
	@FindBy(xpath = "//h4[@id='report-title']") private Element elmFormTitle;

	/**Constructor**/
	public AccountTimeByTimeSheetForm(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver,this);
	}

	/**Page Interactions**/

	public boolean verifyAccountTimeByTimeSheetFormLoaded(){
		return PageLoaded.isElementLoaded(this.getClass(),driver,lstAccountSelect,5) &&
				PageLoaded.isElementLoaded(this.getClass(),driver,txtStartDate,5) &&
				PageLoaded.isElementLoaded(this.getClass(),driver,txtEndDate,5) &&
				PageLoaded.isElementLoaded(this.getClass(),driver,elmGenerateReport);
	}

	public void selectAccount(String strAccount){
		if (lstAccountSelect.syncEnabled(5) && lstAccountSelect.syncVisible(5)) {
			lstAccountSelect.select(strAccount);
		}
	}

	/**
	 * @author David Grayson
	 * @param strStartDate {@link String} mm/dd/yyyy format
	 */
	public void setStartDate(String strStartDate){
		if (txtStartDate.syncEnabled(5) && txtStartDate.syncVisible(5)){
			txtStartDate.clear();
			txtStartDate.sendKeys(strStartDate);
			elmFormTitle.click(); //to get rid of the calendar popup
		}
	}

	/**
	 * @author David Grayson
	 * @param strEndDate {@link String} mm/dd/yyyy format
	 */
	public void setEndDate(String strEndDate){
		if (txtEndDate.syncEnabled(5) && 	txtEndDate.syncVisible(5)){
			txtEndDate.clear();
			txtEndDate.sendKeys(strEndDate);
			elmFormTitle.click(); //to get rid of the calendar popup
		}
	}

	/**
	 * @author David Grayson
	 */
	public void clickGenerateReport(){
		elmFormTitle.click();
		if (driver.findElement(By.xpath("//th[@scope='col']")).syncHidden(5) &&
				elmGenerateReport.syncEnabled(5) &&
				elmGenerateReport.syncVisible(5)){
			elmGenerateReport.click();
		}
	}
}
