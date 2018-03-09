package com.orasi.bluesource;

import com.orasi.utils.TestReporter;
import com.orasi.web.OrasiDriver;
import com.orasi.web.PageLoaded;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Webtable;
import com.orasi.web.webelements.impl.internal.ElementFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class Report {
	private OrasiDriver driver = null;

	/**Page Elements**/
	@FindBy(xpath = "//h3[@class='report-title']") private Element elmReportTitle;
	@FindBy(xpath = "//table") private Webtable tblReport;

	/**
	 * Constructor
	 **/
	public Report(OrasiDriver driver) {
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	/**Page Interactions**/

	/**
	 * @author David Grayson
	 * @return {@link Boolean} Returns <code>true</code> if the report is loaded, <code>false</code> otherwise.
	 */
	public boolean verifyReportIsLoaded(){
		return PageLoaded.isElementLoaded(this.getClass(),driver,elmReportTitle,5) &&
				PageLoaded.isElementLoaded(this.getClass(),driver,tblReport,5);
	}

	/**
	 * @author David Grayson
	 * @return {@link String} Returns the title of the report
	 */
	public String getTitle(){
		return elmReportTitle.getText();
	}

	/**
	 * @author David Grayson
	 * @param strAccount {@link String} The name of the Account to get the projects for
	 * @return {@link List<String>} Returns a String List of Projects and their Sub Projects associated with the Account passed
	 */
	public List<String> getAccountProjects(String strAccount){
		ArrayList<String> projects = new ArrayList<>();
		for (int i = 1; i <= tblReport.getRowCount(); i++) {
			if (tblReport.getCell(i,1).getText().equals(strAccount) && !tblReport.getCell(i,2).getText().isEmpty())
				projects.add(tblReport.getCell(i,2).getText());
		}
		return projects;
	}

	/**
	 * @author David Grayson
	 * @param strProject {@link String} The name of the project to get the data for
	 * @return {@link String[]} Returns a String array of the reports rows data
	 */
	public String[] getRowData(String strProject){
		int row = tblReport.getRowWithCellText(strProject);
		TestReporter.logStep("Getting row "+row+" data");
		return new String[]{
				tblReport.getCell(row,3).getText().trim(), //SOW ID
				tblReport.getCell(row,4).getText().trim(), //Budget Hrs
				tblReport.getCell(row,5).getText().trim(), //Reported Hrs
				tblReport.getCell(row,6).getText().trim(), //Budget $
				tblReport.getCell(row,7).getText().trim() //Reported $
		};
	}
}