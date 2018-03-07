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

	/**
	 * @author David Grayson
	 * @return {@link String} Returns the Account name in the first row of the audit table
	 */
	public String getNewestName(){
		return tblFullAuditRecords.getCell(1,3).getText();
	}

	/**
	 * @author David Grayson
	 * @return {@link String} Returns the newest "changed by" column
	 */
	public String getNewestChangedBy(){
		return tblFullAuditRecords.getCell(1, 7).getText();
	}

	/**
	 * @author David Grayson
	 * @return {@link String} Returns the audited changes to the Accounts Name
	 */
	public String getNewestAuditedNameChange() {
		return tblSpecificAuditRecords.getCell(1,1).getText();
	}

	/**
	 * @author David Grayson
	 * @return {@link String} Returns the audited changes to the Accounts Industry
	 */
	public String getNewestAuditedIndustryChange(){
		return tblSpecificAuditRecords.getCell(2,1).getText();
	}

	/**
	 * @author David Grayson
	 * @return {@link Boolean} Returns <code>true</code> if there are no gaps in the first row of the table
	 * except column 4 and 6, <code>false</code> otherwise.
	 */
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

	/**
	 * @author David Grayson
	 * @return {@link Boolean} Returns <code>true</code> if the page is loaded, <code>false</code> otherwise.
	 */
	public boolean verifyPageIsLoaded() {
		return PageLoaded.isElementLoaded(this.getClass(),driver,tblFullAuditRecords);
	}
}
