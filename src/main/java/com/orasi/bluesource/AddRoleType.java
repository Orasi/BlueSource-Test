package com.orasi.bluesource;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

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

	@FindBy(linkText = "Admin") private Link lnkAdminTab;
	@FindBy(linkText = "Role Types") private Link lnkRoleTypes;
	@FindBy(linkText = "Add New Role Type") private Link lnkAddNewRoleTypeButton;
	@FindBy(id = "role_type_name") private Textbox txtRoleName;
	@FindBy(id = "role_type_billable") private Checkbox billableCheckBox;
	@FindBy(name = "commit") private Button btnCreateRoleType;
	@FindBy(css = "table.table-responsive.table-bordered") private Webtable rolesTable;
	@FindBy(xpath = "//*[@id=\'new_role\']/div[1]/div/div") private Element billingLbl;
	@FindBy(css = "table.table-striped.table-hover") private Webtable projectsTable;
	@FindBy(xpath = "//*[@id=\"accordion\"]/div/div[13]/div/div[1]/button[1]") private Button newRoleButton;
	@FindBy(xpath = "//*[@id=\"new_role\"]/div[1]/div/div") private Label lblBilling;
	@FindBy(id = "role_role_type_id") private Listbox listRoleTypes;
	@FindBy(xpath = "//input[@value='Create Role']") private Button btnAssignRoleType;
	
	private String newRoleTypeName;

	public AddRoleType(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	public void openRoleType() {

		if (lnkAdminTab.isDisplayed() == true)
		{
			lnkAdminTab.click();
			lnkRoleTypes.click();
		}
	}

	public void click_AddNewRoleTypeButton() {

		if (lnkAddNewRoleTypeButton.isDisplayed() == true)
		{

			lnkAddNewRoleTypeButton.click();
		}
	}

	public void createNewRole(String roleType) {
		
		this.newRoleTypeName = roleType;

		if (txtRoleName.isDisplayed()==true) {
			txtRoleName.sendKeys(newRoleTypeName);
			billableCheckBox.uncheck();

			Select select = new Select(driver.findElement(By.name("role_type[permission]")));
			select.selectByValue("View");

			btnCreateRoleType.click();
		}
	}


	public boolean verifyNewRole() {

		Boolean verify = true;

		if (rolesTable.isDisplayed() == verify) {

			if (rolesTable.getRowWithCellText(newRoleTypeName) > 0) {		
				System.out.println("New Role Type is created");
			}
			else {
				System.out.println("New role type is not created");
				verify = false;
			}
		}

		return verify;
	}

	public int getProjectsTableRows() {
		int rowCount = 0;
		try
		{
			rowCount = projectsTable.getRowCount();
		}
		catch(NullPointerException e)
		{
			System.out.println("Null pointer exception, no accounts found  \n" + e.getLocalizedMessage());
		}
		return (rowCount);
	}

	public void openFirstProject() {
		if (projectsTable.isDisplayed() == true) {

			if (getProjectsTableRows() > 0) {
				projectsTable.getCell(2, 1).findElement(
						By.xpath("//*[@id=\'panel_body_1\']/div/table/tbody/tr[1]/td[1]/a")).click();
			}
			else {
				System.out.println("Projects not found");
			}
		}
	}

	public void clickNewRole() {
		if (newRoleButton.isDisplayed() == true) {
			newRoleButton.click();
		}
		else {
			System.out.println("New Role Button was not found");
		}
	}

	public void toggleBilling() {
		if (lblBilling.isEnabled() == true) {
			String str = lblBilling.getAttribute("class");
			if (str.contains("toggle btn btn-xs btn-primary")) {
				lblBilling.jsClick();
				System.out.println("New Label is : " + lblBilling.getAttribute("class"));
			}
			else {
				System.out.println("No need to change because it is already: " 
							+ lblBilling.getAttribute("class"));
			}

		}
	}
		
	public void assignNewRole() {
		
		listRoleTypes.syncVisible(5);
		
		listRoleTypes.select(newRoleTypeName);
		
		btnAssignRoleType.click();
		}
	























}
