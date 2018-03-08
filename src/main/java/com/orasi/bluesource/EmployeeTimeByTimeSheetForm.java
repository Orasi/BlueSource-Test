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

	/**
	 * This method clicks the Generate Report element
	 * @author David Grayson
	 */
	public void clickGenerateReport(){
		if (canInteract(elmGenerateReport)&& tblCalendarPopup.syncHidden(5))
			elmGenerateReport.click();
	}

	/**
	 * This method sets the End Date field
	 * @author David Grayson
	 * @param strEndDate {@link String} mm/dd/yyyy format
	 */
	public void setEndDate(String strEndDate){
		if (canInteract(txtEndDate)){
			txtEndDate.clear();
			txtEndDate.sendKeys(strEndDate);
			elmFormHeader.click();
		}
	}

	/**
	 * This method sets the Start Date field
	 * @author David Grayson
	 * @param strStartDate {@link String} mm/dd/yyyy format
	 */
	public void setStartDate(String strStartDate){
		if (canInteract(txtStartDate)){
			txtStartDate.clear();
			txtStartDate.sendKeys(strStartDate);
			elmFormHeader.click(); //to make popups disappear
		}
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
	 * This method selects an employee from the list that appears
	 * @author David Grayson
	 * @param strEmployee {@link String} full name of employee to select
	 */
	public void selectEmployee(String strEmployee){
		if (canInteract(elmEmployeeSelect)){
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

	/**
	 * This method check that the elements essential to filling out the form are loaded
	 * @author David Grayson
	 * @return {@link Boolean} Returns <code>true</code> if the the form is loaded, <code>false</code> otherwise
	 */
	public boolean verifyFormLoaded() {
		return PageLoaded.isElementLoaded(this.getClass(),driver,elmEmployeeSelect,5) &&
				PageLoaded.isElementLoaded(this.getClass(),driver,txtEndDate,5) &&
				PageLoaded.isElementLoaded(this.getClass(),driver,txtStartDate,5) &&
				PageLoaded.isElementLoaded(this.getClass(),driver,elmGenerateReport,5);
	}
}
