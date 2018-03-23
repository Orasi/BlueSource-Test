package com.orasi.bluesource;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.orasi.utils.Constants;
import com.orasi.utils.Sleeper;
import com.orasi.utils.TestReporter;
import com.orasi.web.OrasiDriver;
import com.orasi.web.PageLoaded;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Label;
import com.orasi.web.webelements.Link;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.Webtable;
import com.orasi.web.webelements.impl.internal.ElementFactory;

public class ReportedTimesSummary {
	private OrasiDriver driver = null;

	/** Page Elements **/
	@FindBy(xpath = "//div[contains(@class,'time-back')]") private Button btnBack;
	@FindBy(xpath = "//*[@id=\"content\"]/div[3]/a") private Link lnkPreviousMonth;
	@FindBy(xpath = "//*[@id=\"content\"]/div[6]/table") private Webtable tblSummary;
	@FindBy(css = "#time-entry-table > tbody") private Webtable tblEditTimesheet;
	@FindBy(xpath = "//*[@id=\"time_modal\"]/div/div/div[1]/button") private Button btnCloseEdit;
	@FindBy(xpath = "//*[@id=\"edit_employee_9\"]/div[4]/input[1]") private Button btnSaveEdit;
	@FindBy(xpath = "//*[@id=\"content\"]/h3") private Element hdrMonth;

	/** Constructor **/
	public ReportedTimesSummary(OrasiDriver driver) {
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	/** Page Interactions **/

	public void clickBackbutton() {
		// TODO Auto-generated method stub
		btnBack.click();
	}

	/**
	 * This clicks on the link to the previous month
	 * 
	 * @author Zach Apple
	 */
	public void clickPreviousMonth() {
		PageLoaded.isDomComplete(driver, 5);
		lnkPreviousMonth.click();
	}

	/**
	 * This finds the first timesheet that includes holiday time, sets the time to
	 * 0.0, and checks if the change is shown from the "Holiday" row on the reported
	 * times summary page
	 * 
	 * @return true if the change was made and shows 0.0 for the first timesheet
	 *         holiday(s) found, false otherwise.
	 */
	public boolean changeHolidayTimeToZeroAndVerify() {
		boolean backToStart = false;// used to end the loop if there were no edits to be made that contained holiday
									// time
		String startMonth = hdrMonth.getText();
		boolean anyChanged = false;// used to keep track of whether changes were made
		while (!backToStart) {
			PageLoaded.isDomComplete(driver, 5);
			try {// finds any "missing timesheet" add buttons
				List<WebElement> listOfMissingTimesheetsAddBtns = tblSummary
						.findElements(By.xpath("//td[contains(text(), 'Missing timesheets for')]/a"));
				if (!listOfMissingTimesheetsAddBtns.isEmpty()) {
					for (WebElement e : listOfMissingTimesheetsAddBtns) {
						e.click();
						int missingTimesheetHolidayRow = tblEditTimesheet.getRowWithCellText("Company Holiday");
						if (missingTimesheetHolidayRow != 0) {
							List<WebElement> list = tblEditTimesheet.findElements(By.xpath("//span[contains(@class, 'non-business-day-pto')]/..//input[contains(@class, 'time-entry-hour-fields')]"));
							List<String> dates = new ArrayList<>();// stores dates of changed holiday time cells
							if (!list.isEmpty()) {
								for (WebElement f : list) {
									f.clear();
									f.sendKeys("0");
									dates.add(f.getAttribute("data-date"));
								}
							}
							btnSaveEdit.click();
							for (String g : dates) {
								int month = Integer.parseInt(g.substring(6, 8));
								int day = Integer.parseInt(g.substring(9, 11));
								String xpathDate = "//td[contains(text(),'Holiday')]/../td/h5/div[contains(text(),'" + month + "/" + day + "')]/..";
								anyChanged = tblSummary.findElement(By.xpath(xpathDate)).getText().equals("0.0\n" + month + "/" + day);
							}
							return anyChanged;
						}
					}
				}
				// finds any subtable of an existing timesheet
				List<WebElement> listOfExistingTimeTables = tblSummary.findElements(By.xpath("//tr[contains(@class, 'time-table')]"));
				if (!listOfExistingTimeTables.isEmpty()) {
					for (WebElement e : listOfExistingTimeTables) {
						String xpath = "//span[@class='approval-section edit-icon glyphicon glyphicon-pencil']";
						e.findElement(By.xpath(xpath)).click();// find any associated edit button and click it
						List<WebElement> list = tblEditTimesheet.findElements(By.xpath(
								"//div[text()='Company Holiday']/../../../tr[6]/td/div/input[contains(@class, 'time-field time-entry-hour-fields ')]"));
						List<String> dates = new ArrayList<>();// stores dates of changed holiday time cells
						if (!list.isEmpty()) {
							for (WebElement f : list) {
								f.clear();
								f.sendKeys("0");
								dates.add(f.getAttribute("data-date"));
							}
						}
						btnSaveEdit.click();
						for (String g : dates) {
							int month = Integer.parseInt(g.substring(6, 8));
							int day = Integer.parseInt(g.substring(9, 11));
							String xpathDate = "//td[contains(text(),'Holiday')]/../td/h5/div[contains(text(),'" + month
									+ "/" + day + "')]/..";
							anyChanged = tblSummary.findElement(By.xpath(xpathDate)).getText().equals("0.0\n" + month + "/" + day);
						}
						return anyChanged;
					}
				}
				PageLoaded.isElementLoaded(ReportedTimesSummary.class, driver, lnkPreviousMonth);
				this.lnkPreviousMonth.click();
				PageLoaded.isDomComplete(driver, 5);
				backToStart = hdrMonth.getText().equals(startMonth);
			} catch (Exception e) {
				TestReporter.log("Something went wrong. See: "+e.toString());
			}
		}
		return anyChanged;
	}
}