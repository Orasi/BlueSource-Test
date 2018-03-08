package com.orasi.bluesource;

import com.orasi.web.OrasiDriver;
import com.orasi.web.PageLoaded;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Listbox;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.Webtable;
import com.orasi.web.webelements.impl.internal.ElementFactory;
import org.openqa.selenium.support.FindBy;

public class ProjectTimeByTimeSheetForm {
	private OrasiDriver driver = null;

	/**Page Elements**/
	@FindBy(xpath = "//select[@id='project_']") private Listbox lstProjectSelect;
	@FindBy(xpath = "//input[@id='start_date']") private Textbox txtStartDate;
	@FindBy(xpath = "//input[@id='end_date']") private Textbox txtEndDate;
	@FindBy(xpath = "//input[@name='commit']") private Element elmGenerateReport;
	@FindBy(xpath = "//table[@class='ui-datepicker-calendar']") private Webtable tblCalendarPopup;
	@FindBy(xpath = "//h4[@id='report-title']") private Element elmFormTitle;

	/**Constructor**/
	public ProjectTimeByTimeSheetForm(OrasiDriver driver) {
		this.driver = driver;
		ElementFactory.initElements(driver,this);
	}

	/**Page Interactions**/

	/**
	 * @author David Grayson
	 * @return {@link Boolean} Returns <code>true</code> if the form is loaded, <code>false</code> otherwise.
	 */
	public boolean verifyFormIsLoaded(){
		return checkLoaded(lstProjectSelect) &&
				checkLoaded(txtStartDate) &&
				checkLoaded(txtEndDate) &&
				checkLoaded(elmGenerateReport);
	}

	private boolean checkLoaded(Element elm) {
		return PageLoaded.isElementLoaded(this.getClass(),driver,elm,5);
	}

	/**
	 * This method selects an employee from the list that appears
	 * @author David Grayson
	 * @param project {@link String} full name of the Project to select
	 */
	public void selectProject(String project){
		if (canInteract(lstProjectSelect)){
			lstProjectSelect.select(project);
		}
	}

	/**
	 * This method clicks the Generate Report element
	 * @author David Grayson
	 */
	public void clickGenerateReport(){
		if (canInteract(elmGenerateReport) && tblCalendarPopup.syncHidden(5)){
			elmGenerateReport.click();
		}
	}

	/**
	 * This method sets the End Date field
	 * @author David Grayson
	 * @param endDate {@link String} mm/dd/yyyy format
	 */
	public void setEndDate(String endDate){
		if (canInteract(txtEndDate)){
			txtEndDate.clear();
			txtEndDate.sendKeys(endDate);
		}
	}

	/**
	 * This method sets the Start Date field
	 * @author David Grayson
	 * @param startDate {@link String} mm/dd/yyyy format
	 */
	public void setStartDate(String startDate){
		if (canInteract(txtStartDate)){
			txtStartDate.clear();
			txtStartDate.sendKeys(startDate);
		}
	}

	/**
	 * This method provides standard checks that an element can be interacted with
	 * @author David Grayson
	 * @param elm {@link Element} Element to check
	 * @return {@link Boolean} Returns <code>true</code> if the element is enabled and visible, <code>false</code> otherwise
	 */
	private boolean canInteract(Element elm){
		elmFormTitle.click(); //to clear any popups
		return elm.syncEnabled(5) && elm.syncVisible(5);
	}
}
