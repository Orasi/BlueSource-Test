package com.orasi.bluesource;

import com.orasi.utils.TestReporter;
import com.orasi.web.OrasiDriver;
import com.orasi.web.PageLoaded;
import com.orasi.web.webelements.Webtable;
import com.orasi.web.webelements.impl.internal.ElementFactory;
import org.openqa.selenium.support.FindBy;

public class AuditHistory {
	private OrasiDriver driver = null;

	/**Page Elements**/
	@FindBy(xpath = "//th[contains(text(),'Action')]/../../..") private Webtable tblFullAuditRecords;
	@FindBy(xpath = "//table/tbody[1]/tr/td[5]/table") private Webtable tblSpecificAuditRecords;

	/**Constructor**/
	public AuditHistory(OrasiDriver driver) {
		this.driver = driver;
		ElementFactory.initElements(driver,this);
	}

	/**Page Interactions**/

	public boolean verifyName(String strName){
		return tblFullAuditRecords.getRowWithCellText(strName,3) == 1;
	}

	public boolean verifyChangedBy(String strChangedBy){
		return tblFullAuditRecords.getRowWithCellText(strChangedBy, 7) == 1;
	}

	public boolean verifyAuditedChangeName(String oldName, String newName){
		if (oldName.equals(newName)){
			return false;
		}

		return  tblSpecificAuditRecords.getRowWithCellText("Name: From '" + oldName + "' to '" + newName + "'") != 0;
	}

	public boolean verifyAuditedChangeIndustry(int oldIndustry, int newIndustry){
		TestReporter.log("["+tblSpecificAuditRecords.getCell(2,1).getText()+"]");
		TestReporter.log("[Industry: From '" + oldIndustry + "' to '" + newIndustry + "']");
		return tblSpecificAuditRecords.getCell(2,1).getText().equals("Industry: From '" + oldIndustry + "' to '" + newIndustry + "'");
	}

	public boolean verifyNoAuditRecordGaps(){
		PageLoaded.isDomComplete(driver);
		for (int i = 1; i < 9; i++) {
			if (tblFullAuditRecords.getCell(1, i).getText().isEmpty() && i!=4 && i!=6){
				TestReporter.log("column = " + i + "\n" + tblFullAuditRecords.getCell(1,i).getText());
				return false;
			}
		}
		return true;
	}
}
