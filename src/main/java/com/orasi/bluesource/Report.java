package com.orasi.bluesource;

import com.orasi.utils.TestReporter;
import com.orasi.web.OrasiDriver;
import com.orasi.web.PageLoaded;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Webtable;
import com.orasi.web.webelements.impl.internal.ElementFactory;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class Report {
	private OrasiDriver driver = null;

	/**Page Elements**/
	@FindBy(xpath = "//h3[@class='report-title']") private Element elmReportTitle;
	@FindBy(xpath = "//table") private Webtable tblReport;

	/**Constructor**/
	public Report(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver,this);
	}

	/**Page Interactions**/

	public boolean verifyReportIsLoaded(){
		return PageLoaded.isElementLoaded(this.getClass(),driver,elmReportTitle,5) &&
				PageLoaded.isElementLoaded(this.getClass(),driver,tblReport,5);
	}

	public String getTitle(){
		return elmReportTitle.getText();
	}

	public List<String> getEmployees(){
		ArrayList<String> employees = new ArrayList<>();
		for (int i = 1; i <= tblReport.getRowCount(); i++) {
			String cell = tblReport.getCell(i,2).getText();
			if (!cell.equals("Total:") && !cell.isEmpty()){
				TestReporter.log(cell);
				employees.add(cell);
			}
		}
		return employees;
	}

	public boolean checkTotals(){
		int runningTotal = 0;
		int sectionTotal = 0;
		int rowBeginSection = 1;
		int rowOfSectionTotal = 1;

		do {
			for (int i=rowBeginSection; i<tblReport.getRowCount(); i++){
				if (tblReport.getCell(i,2).getText().equals("Total:")){
					rowOfSectionTotal = i;
					break;
				}
			}

			for (int i=rowBeginSection; i<rowOfSectionTotal; i++){
				if (tblReport.getCell(i,4).getText().isEmpty())
					TestReporter.log(String.valueOf(i));
				sectionTotal += Integer.parseInt(tblReport.getCell(i,4).getText());
			}

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