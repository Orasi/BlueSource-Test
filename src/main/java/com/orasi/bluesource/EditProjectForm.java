package com.orasi.bluesource;

import com.orasi.web.OrasiDriver;
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

	public void editSOWNumber(String strSOW){
		txtSowNumberField.syncVisible(3);
		txtSowNumberField.clear();
		txtSowNumberField.set(strSOW);
	}

	public void setComments(String strComments){
		txtComments.syncVisible(3);
		txtComments.clear();
		txtComments.set(strComments);
	}

	public void clickButtonSubmit(){
		btnSubmit.click();
	}

	public void testEditSOWNumber(){
		editSOWNumber(txtSowNumberField.getText()+"1");
	}

	public void testSetComments(){
		setComments("test test test");
	}
}
