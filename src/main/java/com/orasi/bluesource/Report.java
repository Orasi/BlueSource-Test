package com.orasi.bluesource;

import com.orasi.utils.TestReporter;
import com.orasi.web.OrasiDriver;
import com.orasi.web.PageLoaded;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Webtable;
import com.orasi.web.webelements.impl.internal.ElementFactory;
import org.openqa.selenium.support.FindBy;

public class Report {
	private OrasiDriver driver = null;

	/**Page Elements**/
	@FindBy(xpath = "//h3[@class='report-title']") private Element elmReportTitle;
	@FindBy(xpath = "//table") private Webtable tblReport;

	/**Constructor**/
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
	 * @return {@link Boolean} Returns <code>true</code> if all the sub totals and the grand total are correct, <code>false</code> otherwise.
	 */
	public boolean checkTotals(){
		int runningTotal = 0;
		int sectionTotal = 0;
		int rowBeginSection = 1;
		int rowOfSectionTotal = 1;

		/*
		The do while loop iterates over a report of any size and checks all the sub totals and the grand total
		to make sure they add up correctly.
		 */
		do {
			/*
			This for loop find the row with the "Total:" line to set the ned point for the next section
			 */
			for (int i=rowBeginSection; i<tblReport.getRowCount(); i++){
				if (tblReport.getCell(i,2).getText().equals("Total:")){
					rowOfSectionTotal = i;
					break;
				}
			}

			/*
			this for loop iterates over the section to check the total line at the end
			 */
			for (int i=rowBeginSection; i<rowOfSectionTotal; i++){
				if (tblReport.getCell(i,4).getText().isEmpty())
					TestReporter.log(String.valueOf(i));
				sectionTotal += Integer.parseInt(tblReport.getCell(i,4).getText());
			}

			/*
			if the total matches then it is added to the runningTotal and the row markers are moved to the next section
			if not the the section is logged and false is returned
			 */
			if (tblReport.getCell(rowOfSectionTotal,4).getText().equals(String.valueOf(sectionTotal))){
				rowBeginSection = rowOfSectionTotal += 2;
				runningTotal += sectionTotal;
				sectionTotal = 0;
			}else {
				TestReporter.log("rowBeginSection = " + rowBeginSection);
				TestReporter.log("rowOfSectionTotal = " + rowOfSectionTotal);
				return false;
			}

		} while(rowBeginSection < tblReport.getRowCount());

		return tblReport.getCell(tblReport.getRowCount(), 4).getText().equals(String.valueOf(runningTotal));
	}
}