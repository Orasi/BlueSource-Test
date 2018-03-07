package com.orasi.bluesource;

import com.orasi.web.OrasiDriver;
import com.orasi.web.PageLoaded;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.impl.internal.ElementFactory;
import org.openqa.selenium.support.FindBy;

public class EditProjectForm {
	private OrasiDriver driver = null;
	@FindBy(xpath = "//input[@id='project_sow_id']") private Textbox txtSowNumberField;
	@FindBy(xpath = "//textarea[@id='project_comments']") private Textbox txtComments;
	@FindBy(xpath = "//input[@value='Update Project']") private Button btnSubmit;

	/**Constructor**/
	public EditProjectForm(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver,this);
	}

	/**
	 * @author David Grayson
	 * @param strSOW {@link String} The new SOW number
	 */
	public void editSOWNumber(String strSOW){
		txtSowNumberField.syncVisible(3);
		txtSowNumberField.clear();
		txtSowNumberField.set(strSOW);
	}

	/**
	 * @author David Grayson
	 * @param strComments {@link String} The comments on the Project Edit
	 */
	public void setComments(String strComments){
		txtComments.syncVisible(3);
		txtComments.clear();
		txtComments.set(strComments);
	}

	/**
	 * @author David Grayson
	 */
	public void clickButtonSubmit(){
		btnSubmit.click();
	}

	/**
	 * @author David Grayson
	 */
	public void testEditSOWNumber(){
		editSOWNumber(txtSowNumberField.getText()+"1");
	}

	/**
	 * @author David Grayson
	 */
	public void testSetComments(){
		setComments("test test test");
	}

	/**
	 * @author David Grayson
	 * @return {@link Boolean} Returns <code>true</code> if the form loaded correctly, <code>false</code> otherwise.
 	 */
	public boolean verifyFormLoaded() {
		return PageLoaded.isElementLoaded(this.getClass(),driver,txtComments,5) &&
				PageLoaded.isElementLoaded(this.getClass(),driver,btnSubmit,5) &&
				PageLoaded.isElementLoaded(this.getClass(),driver,txtSowNumberField,5);
	}
}
