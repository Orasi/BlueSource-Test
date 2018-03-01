package com.orasi.bluesource;

import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Checkbox;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.impl.internal.ElementFactory;
import org.openqa.selenium.support.FindBy;

/**
 * This class is for manipulating the form shown when editing the rate of a role in a project.
 * @author david.grayson
 */
public class EditRoleRateForm {
	private OrasiDriver driver = null;

	/**Page Elements**/
	@FindBy(xpath = "//input[@id='role_budget_inherit_start_date']") private Checkbox chkInheritStartDate;
	@FindBy(xpath = "//input[@id='role_budget_inherit_end_date']") private Checkbox chkInheritEndDate;
	@FindBy(xpath = "//input[@id='role_budget_start_date']") private Element eleEditStartDate;
	@FindBy(xpath = "//input[@id='role_budget_end_date']") private Element eleEditEndDate;
	@FindBy(xpath = "//form[@class='edit_role_budget']/div/input[@id='role_budget_rate']") private Textbox txtRate;
	@FindBy(xpath = "//textarea[@id='rate_comments']") private Textbox txtComments;
	@FindBy(xpath = "//input[@value='Update Role budget']") private Button btnSubmit;

	/**Constructor**/
	public EditRoleRateForm(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	//**Page Interactions**//

	/**
	 * Checks if the Inherit Start Date checkbox is checked
	 * @author david.grayson
	 * @return returns <code>true</code> if Inherit Start Date checkbox is checked,
	 * <code>false</code> if not.
	 */
	public boolean doesInheritStartDate(){
		return chkInheritStartDate.isChecked();
	}

	/**
	 * Checks if the Inherit End Date checkbox is checked
	 * @author david.grayson
	 * @return <code>true</code> if Inherit End Date checkbox is checked,
	 * <code>false</code> if not.
	 */
	public boolean doesInheritEndDate(){
		return chkInheritEndDate.isChecked();
	}

	/**
	 * Sets the Inherit Start Date checkbox to checked
	 * @author david.grayson
	 */
	public void checkInheritStartDate(){
		chkInheritStartDate.check();
	}

	/**
	 * Sets the Inherit End Date checkbox to checked
	 * @author david.grayson
	 */
	public void checkInheritEndDate(){
		chkInheritEndDate.check();
	}

	/**
	 * Sets the Inherit Start Date checkbox to unchecked
	 * @author david.grayson
	 */
	public void uncheckInheritStartDate(){
		chkInheritStartDate.uncheck();
	}

	/**
	 * Sets the Inherit End Date checkbox to unchecked
	 * @author david.grayson
	 */
	public void uncheckInheritEndDate(){
		chkInheritEndDate.uncheck();
	}

	/**
	 * Clicks the form submit button to update the role rate
	 * @author david.grayson
	 */
	public void clickSubmit(){
		btnSubmit.click();
	}

	/**
	 * Unchecks the Inherit Start Date checkbox, and tries to set the Start Date from String provided
	 * @author david.grayson
	 * @param strStartDate MMddYYY format
	 */
	public void setStartDate(String strStartDate){
		uncheckInheritStartDate();
		eleEditStartDate.sendKeys(strStartDate);
	}

	/**
	 * Unchecks the Inherit End Date checkbox, and tries to set the End Date from String provided
	 * @author david.grayson
	 * @param strEndDate MMddYYY format
	 */
	public void setEndDate(String strEndDate){
		if (!doesInheritEndDate())
			uncheckInheritEndDate();
		eleEditEndDate.sendKeys(strEndDate);
	}

	/**
	 * Waits for the Element to be visible then clears and sets the Rate text box
	 * @author david.grayson
	 * @param strRate new rate for role e.g. "71.0"
	 */
	public void setRate(String strRate){
		txtRate.syncVisible(3);
		txtRate.clear();
		txtRate.sendKeys(strRate);
	}

	/**
	 * Waits for the Element to be visible then clears and sets the Comments textbox
	 * @author david.grayson
	 * @param strComments comments on the rate change
	 */
	public void setComments(String strComments){
		txtComments.syncVisible(3);
		txtComments.clear();
		txtComments.set(strComments);
	}

	/**
	 * automatically sets the comments to "test test test"
	 */
	public void testComments(){
		setComments("test test test");
	}

	/**
	 * increments the rate by one and outputs it as a string
	 */
	public void testRate(){
		double rate = Double.parseDouble(txtRate.getText().replace("$ ",""));
		setRate(Double.toString(rate+1.0));
	}
}
