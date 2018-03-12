package com.orasi.bluesource;

import com.orasi.web.OrasiDriver;
import com.orasi.web.PageLoaded;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Listbox;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.impl.internal.ElementFactory;
import org.openqa.selenium.support.FindBy;

public class EditRoleHoursForm {
	private OrasiDriver driver = null;

	/**Page Elements**/
	@FindBy(xpath = "//input[@id='role_budget_hour_hours']") private Textbox txtHours;
	@FindBy(xpath = "//select[@id='role_budget_hour_document_id']") private Listbox lstDocument;
	@FindBy(xpath = "//textarea[@id='rate_comments']") private Textbox txtComments;
	@FindBy(xpath = "//input[@value='Add Hours']") private Button btnAddHours;


	/**
	 * Constructor
	 **/
	public EditRoleHoursForm(OrasiDriver driver) {
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	/**Page Interactions**/

	public void testSetHours(){
		setHours(String.valueOf(Double.parseDouble(txtHours.getText().trim()) + 0.3513));
	}

	public void clickAddHours(){
		if (canInteract(btnAddHours))
			btnAddHours.click();
	}

	public void setComments(String comments){
		if (canInteract(txtComments)){
			txtComments.clear();
			txtComments.sendKeys(comments);
		}
	}

	public void setDocument(String documentName){
		if (canInteract(lstDocument))
			lstDocument.select(documentName);
	}

	public void setHours(String hours){
		if (canInteract(txtHours)){
			txtHours.clear();
			txtHours.sendKeys(hours);
		}
	}

	/**
	 * @author David Grayson
	 * @return {@link Boolean} Returns true if key elements of the form have loaded
	 */
	public boolean verifyFormLoaded(){
		return PageLoaded.isElementLoaded(this.getClass(),driver,txtHours,5) &&
				PageLoaded.isElementLoaded(this.getClass(),driver,lstDocument,5) &&
				PageLoaded.isElementLoaded(this.getClass(),driver,txtComments,5);
	}

	/**
	 * This method provides standard checks that an element can be interacted with
	 * @author David Grayson
	 * @param elm {@link Element} Element to check
	 * @return {@link Boolean} Returns <code>true</code> if the element is enabled and visible, <code>false</code> otherwise
	 */
	private boolean canInteract(Element elm){
		return elm.syncEnabled(5) && elm.syncVisible(5);
	}
}