package com.orasi.bluesource;

import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.impl.internal.ElementFactory;
import org.openqa.selenium.support.FindBy;

public class EditRoleForm {
	private OrasiDriver driver = null;

	/**Page Elements**/
	@FindBy(xpath = "//a[contains(text(),'Delete Role')]") private Button btnDelete;

	/**Constructor**/
	public EditRoleForm(OrasiDriver driver) {
		this.driver = driver;
		ElementFactory.initElements(driver,this);
	}

	/**Page Interactions**/

	public void clickDelete(){
		btnDelete.syncEnabled(3);
		btnDelete.click();
	}
}
