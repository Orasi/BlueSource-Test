package com.orasi.bluesource;

import org.openqa.selenium.support.FindBy;
import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Checkbox;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Label;
import com.orasi.web.webelements.Link;
import com.orasi.web.webelements.Listbox;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.Webtable;
import com.orasi.web.webelements.impl.internal.ElementFactory;

public class AddRoleType {

	private OrasiDriver driver = null;

	/**Page Elements**/
	@FindBy(linkText = "Admin") private Link lnkAdminTab;
	@FindBy(linkText = "Role Types") private Link lnkRoleTypes;
	@FindBy(linkText = "Add New Role Type") private Link lnkAddNewRoleTypeButton;
	@FindBy(xpath = "//*[@id='panel_body_1']/div/table/tbody/tr[1]/td[1]/a") private Link lnkFirstProject;
	@FindBy(id = "role_type_name") private Textbox txtRoleName;
	@FindBy(id = "role_type_billable") private Checkbox billableCheckBox;
	@FindBy(name = "role_type[permission]") private Listbox listPermissionType;
	@FindBy(name = "commit") private Button btnCreateRoleType;
	@FindBy(xpath = "//*[@id='content']/table[1]") private Webtable rolesTable;
	@FindBy(xpath = "//*[@id=\'new_role\']/div[1]/div/div") private Element billingLbl;
	@FindBy(css = "table.table-striped.table-hover") private Webtable projectsTable;
	@FindBy(xpath = "//*[@id=\"accordion\"]/div/div[13]/div/div[1]/button[1]") private Button newRoleButton;
	@FindBy(xpath = "//*[@id='new_role']/div[1]/div/div") private Label lblBilling;
	@FindBy(id = "role_role_type_id") private Listbox listRoleTypes;
	@FindBy(xpath = "//*[@id='select2-role_role_type_id-container']") private Element newRoleTypeSelected;

	private String newRoleTypeName;

	/**Constructor**/
	public AddRoleType(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	/**
	 * Opens Role Types page
	 * @return true
	 * @author shahrukh.rehman
	 */
	public boolean openRoleType() {

		if (lnkAdminTab.syncVisible(10)) 
		{
			lnkAdminTab.click();
			lnkRoleTypes.click();
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Clicks Add New Role Type Button
	 * @return true
	 * @author shahrukh.rehman
	 */
	public boolean clickAddNewRoleTypeButton() {

		if (lnkAddNewRoleTypeButton.syncVisible(10))
		{
			lnkAddNewRoleTypeButton.click();
			return true;
		}

		else {
			return false;
		}
	}

	/**
	 * Creates a new role
	 * @param roleType {@link String}
	 * @author shahrukh.rehman
	 */
	public void createNewRole(String roleType) {

		txtRoleName.syncVisible(10);
		txtRoleName.sendKeys(roleType);
		billableCheckBox.uncheck();
		listPermissionType.select("View");
		btnCreateRoleType.click();
	}
	
	/**
	 * Checks to see if new role type is created
	 * @param roleType {@link String}
	 * @return true
	 * @author shahrukh.rehman
	 */
	public boolean verifyNewRole(String roleType) {

		rolesTable.syncVisible(10);

		if (rolesTable.getRowWithCellText(roleType) > 0) {	
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Checks if Projects table contains projects and clicks on the first project link
	 * @return true
	 * @author shahrukh.rehman
	 */
	public boolean openFirstProject() {

		projectsTable.syncVisible(10);

		if (projectsTable.getRowCount() > 0) {
			lnkFirstProject.click();
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Checks to see if new role type is created
	 * @return true
	 * @author shahrukh.rehman
	 */
	public boolean clickNewRole() {
		if (newRoleButton.syncVisible(10)) {
			newRoleButton.click();
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Selects billing type in the Project Role info box 
	 * @author shahrukh.rehman
	 */
	public void toggleBilling() {
		lblBilling.syncEnabled(10);
		String str = lblBilling.getAttribute("class");
		if (str.contains("toggle btn btn-xs btn-primary")) {
			lblBilling.jsClick();
		}
	}
	
	/**
	 * Selects the role from the drop-down list
	 * @return true
	 * @author shahrukh.rehman
	 */
	public boolean assignNewRole(String roleType) {

		if (listRoleTypes.syncVisible(10)) {
			listRoleTypes.select(roleType);
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Checks to see if the role type is selected
	 * @return true
	 * @author shahrukh.rehman
	 */
	public boolean newRoleSelected(String roleType) {

		if (newRoleTypeSelected.syncAttributeContainsValue("title", roleType , 1)) {
			return true;
		}
		else {
			return false;
		}
	}
}
