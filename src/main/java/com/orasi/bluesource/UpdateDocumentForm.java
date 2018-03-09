package com.orasi.bluesource;

import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Link;
import com.orasi.web.webelements.impl.internal.ElementFactory;
import org.openqa.selenium.support.FindBy;

public class UpdateDocumentForm {
	private OrasiDriver driver = null;

	/**Page Elements**/
	@FindBy(xpath = "//a[@data-method='delete']") private Link lnkDelete;

	/**
	 * Constructor
	 **/
	public UpdateDocumentForm(OrasiDriver driver) {
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	/**Page Interactions**/

	public void clickDelete(){
		lnkDelete.syncVisible(5);
		lnkDelete.click();
	}
}