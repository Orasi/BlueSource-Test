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

	public int getProjectCount(String strAccount){
		int count = 0;
		for (int i = 1; i<=tblReport.getRowCount(); i++){
			if (!tblReport.getCell(i,2).getText().contains("/") && tblReport.getCell(i,1).getText().equals(strAccount))
				count++;
		}
		return count;
	}

	public List<String> getProjects(String strAccount){
		ArrayList<String> projects = new ArrayList<>();
		for (int i = 1; i <= tblReport.getRowCount(); i++) {
			if (tblReport.getCell(i,1).getText().equals(strAccount) && !tblReport.getCell(i,2).getText().contains("/"))
				projects.add(tblReport.getCell(i,2).getText());
		}
		return projects;
	}

	public List<String> getSubProjects(String strProject){
		ArrayList<String> subProjects = new ArrayList<>();
		for (int i = 1; i <= tblReport.getRowCount(); i++){
			String temp = tblReport.getCell(i,2).getText();
			//TestReporter.log("temp: "+temp);
			if (temp.contains(strProject+" /")){
				subProjects.add(temp);
			}
		}
		return subProjects;
	}

	public String[] getProjectData(String strProject){
		int row = tblReport.getRowWithCellText(strProject);
		return new String[]{
				tblReport.getCell(row,3).getText().trim(), //SOW ID
				tblReport.getCell(row,4).getText().trim(), //Budget Hrs
				tblReport.getCell(row,5).getText().trim(), //Reported Hrs
				tblReport.getCell(row,6).getText().trim(), //Budget $
				tblReport.getCell(row,7).getText().trim() //Reported $
		};
	}

	public String[] getSubProjectData(String strSubProject){
		int row = tblReport.getRowWithCellText(strSubProject);
		//TestReporter.log(strProject + " / " + strSubProject);
		return new String[]{
				tblReport.getCell(row,3).getText().trim(), // 0 - SOW ID
				tblReport.getCell(row,4).getText().trim(), // 1 - Budget Hrs
				tblReport.getCell(row,5).getText().trim(), // 2 - Reported Hrs
				tblReport.getCell(row,6).getText().trim(), // 3 - Budget $
				tblReport.getCell(row,7).getText().trim() // 4 - Reported $
		};
	}
}