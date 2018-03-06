package com.orasi.bluesource;

import com.orasi.web.OrasiDriver;
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
	public Report(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver,this);
	}

	/**Page Interactions**/

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
				sectionTotal += Integer.parseInt(tblReport.getCell(i,4).getText());
			}

			if (tblReport.getCell(rowOfSectionTotal,4).getText().equals(String.valueOf(sectionTotal))){
				rowBeginSection = ++rowOfSectionTotal;
				runningTotal += sectionTotal;
				sectionTotal = 0;
			}else {
				return false;
			}

		} while(rowBeginSection < tblReport.getRowCount());

		if (tblReport.getCell(tblReport.getRowCount()-1,4).getText().equals(String.valueOf(runningTotal)))
			return true;
		else
			return false;
	}
}
