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

	public boolean doesColumnHaveEmptyValues(int column){
		for (int i = 1; i <= tblReport.getRowCount(); i++){
			if (tblReport.getCell(i,column).getText().isEmpty())
				return true;
		}
		return false;
	}

	public List<String> getAllAccounts(){
		ArrayList<String> accounts = new ArrayList<>();
		for (int i = 1; i <= tblReport.getRowCount(); i++){
			if (!accounts.contains(tblReport.getCell(i,1).getText()))
				accounts.add(tblReport.getCell(i,1).getText());
		}
		return accounts;
	}
}