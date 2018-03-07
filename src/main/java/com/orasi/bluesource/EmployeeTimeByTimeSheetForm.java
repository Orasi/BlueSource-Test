package com.orasi.bluesource;

import com.orasi.web.OrasiDriver;
import com.orasi.web.PageLoaded;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.Webtable;
import com.orasi.web.webelements.impl.internal.ElementFactory;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class EmployeeTimeByTimeSheetForm {
	private OrasiDriver driver = null;

	/**Page Elements**/
	@FindBy(xpath = "//input[@id='start_date']") private Textbox txtStartDate;
	@FindBy(xpath = "//input[@id='end_date']") private Textbox txtEndDate;
	@FindBy(xpath = "//input[@name='commit']") private Element elmGenerateReport;
	@FindBy(xpath = "//span[@class='select2-selection select2-selection--single']") private Element elmEmployeeSelect;
	@FindBy(xpath = "//span[@class='select2-results']/ul/li") private List<Element> employeeOptions;
	@FindBy(xpath = "//h4[@id='report-title']") private Element elmFormHeader;
	@FindBy(xpath = "//span[@class='select2-dropdown select2-dropdown--below']") private Element elmEmployeeList;
	@FindBy(xpath = "//table[@class='ui-datepicker-calendar']") private Webtable tblCalendarPopup;

	/**Constructor**/
	public EmployeeTimeByTimeSheetForm(OrasiDriver driver) {
		this.driver = driver;
		ElementFactory.initElements(driver,this);
	}

	/**Page Interactions**/

	public void clickGenerateReport(){
		if (canEnterText(elmGenerateReport)&& tblCalendarPopup.syncHidden(5))
			elmGenerateReport.click();
	}

	public void setEndDate(String endDate){
		if (canEnterText(txtEndDate)){
			txtEndDate.clear();
			txtEndDate.sendKeys(endDate);
			elmFormHeader.click();
		}
	}

	public void setStartDate(String startDate){
		if (canEnterText(txtStartDate)){
			txtStartDate.clear();
			txtStartDate.sendKeys(startDate);
			elmFormHeader.click(); //to make popups disappear
		}
	}

	private boolean canEnterText(Element elm){
		return elm.syncEnabled(5) && elm.syncVisible(5);
	}

	public void selectEmployee(String strEmployee){
		if (elmEmployeeSelect.syncEnabled(5) && elmEmployeeSelect.syncVisible(5)){
			elmEmployeeSelect.click();
			for (Element employee:employeeOptions){
				if (employee.getText().equals(strEmployee)){
					employee.scrollIntoView();
					employee.click();
					break;
				}
			}
		}
	}

	public boolean verifyFormLoaded() {
		return PageLoaded.isElementLoaded(this.getClass(),driver,elmEmployeeSelect,5) &&
				PageLoaded.isElementLoaded(this.getClass(),driver,txtEndDate,5) &&
				PageLoaded.isElementLoaded(this.getClass(),driver,txtStartDate,5) &&
				PageLoaded.isElementLoaded(this.getClass(),driver,elmGenerateReport,5);
	}
}
