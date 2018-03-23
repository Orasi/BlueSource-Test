package com.orasi.bluesource;

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
import com.orasi.web.webelements.Listbox;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.Webtable;
import com.orasi.web.webelements.impl.internal.ElementFactory;

public class ReportedTimesSummary {
	private OrasiDriver driver = null;

	/** Page Elements **/
	@FindBy(xpath = "//div[contains(@class,'time-back')]") private Button btnBack;
	@FindBy(xpath = "//*[@id'content']/div[3]/a") private Link lnkPreviousMonth;
	@FindBy(xpath = "//span[contains(text(),'Add Non-Billable')]") private Element addNonBillable;
	@FindBy(xpath = "//select[@id = 'flavor']") private Listbox boxFirstList;

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
	 * Finds the first timesheet to click add/edit on, and clicks it.
	 * 
	 * @author Zach Apple
	 */
	public void clickOnTimesheet() {
		boolean goBackAMonth = true;//in case there are none, need to go back a month
		while (goBackAMonth) {
			try {
				driver.findElement(By.xpath("//a[@class='add-resource-btn btn btn-default btn-xs pull-right']")).click();
				goBackAMonth = false;
				continue;
			} catch (Exception e) {
				TestReporter.log("There are no add buttons, look for the edit button");
				try {
					driver.findElement(
							By.xpath("//span[@class='approval-section edit-icon glyphicon glyphicon-pencil']")).click();
					goBackAMonth = false;
					continue;
				} catch (Exception f) {
					TestReporter.log("There are no editable timesheets, go back a month");
					lnkPreviousMonth.click();
				}
			}
		}
	}

	/**
	 * Returns true if the option 'Travel' is on the 'Non-Billable' list.
	 * 
	 * @author Zach Apple
	 * @return true if Travel is an option on the dropdown list, false otherwise
	 */
	public boolean verifyTravelOption() {
		PageLoaded.isDomComplete(driver, 5);
		addNonBillable.click();
		List<WebElement> listOptions = boxFirstList.getOptions();
		for (WebElement e : listOptions) {
			if (e.getText().contains("Travel")) {
				return true;
			}
		}
		return false;
	}

}