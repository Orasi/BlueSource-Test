package com.orasi.bluesource;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;

import javax.lang.model.util.Elements;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.orasi.utils.Randomness;
import com.orasi.utils.TestReporter;
import com.orasi.web.OrasiDriver;
import com.orasi.web.PageLoaded;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Checkbox;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Label;
import com.orasi.web.webelements.Link;
import com.orasi.web.webelements.Listbox;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.Webtable;
import com.orasi.web.webelements.impl.internal.ElementFactory;

public class Accounts {
	private OrasiDriver driver = null;
	
	
	/**Page Elements**/
	@FindBy(xpath = "//*[@id='resource-content']/div[2]/p") private Element elmNumberPages;
	@FindBy(xpath = "//*[@id=\"resource-content\"]/div[1]/table/tbody") private Webtable tblAccounts;
	@FindBy(id = "preference_resources_per_page") private Listbox lstAccountPerPage;
	@FindBy(linkText = "Industry") private Link lnkIndustry;
	@FindBy(linkText = "Accounts") private Link lnkAccountsTab;
	@FindBy(xpath = "//button[@data-target='#modal_3']") private Button btnAssignEmployee;
	@FindBy(xpath = "//div[@id='panel_body_1']//table") private Webtable tblProjects;
	@FindBy(xpath = "//button[@data-target='#modal_1']") private Button btnAddAccount;
	@FindBy(xpath = "//input[@id='account_name']") private Textbox txtAccountName;
	@FindBy(xpath = "//select[@id='account_industry_id']") private Listbox lstIndustry;
	@FindBy(xpath = "//input[@value='Create Account']") private Button btnCreateAccount;
	@FindBy(xpath = "//*[@id='accordion']/div/div[5]/div/button[2]") private Button btnEditAccount;
	@FindBy(css = "div.btn.btn-secondary.btn-xs.quick-nav") private Button btnQuickNav;
	@FindBy(xpath = "//a[contains(@ng-bind, 'n + 1')]") private List<Button> btnPages;
	@FindBy(xpath = "//*[@id=\"project-list\"]/div/div[1]/div") private Button btnCloseQuickNav;
	@FindBy(xpath = "//*[@id='panel_body_1']/div/table/tbody/tr[1]/td[1]/a") private Link lnkFirstProject;
	@FindBy(xpath = "(//*[@id='panel_body_3']/div/div/table/tbody/tr/td[1]/a)[1]") private Link lnkFirstRole;
	@FindBy(xpath = "//*[@id='panel_body_2']/div/table/tbody/tr/td[8]/span[1]/a") private Button btnEditFirstRate;
	@FindBy(xpath = "//form[@class='edit_role_budget']/div/input[@id='role_budget_inherit_start_date']") private Checkbox cbStartDate;
	@FindBy(xpath = "//form[@class='edit_role_budget']/div/input[@id='role_budget_start_date']") private Textbox txtStartDate;
	@FindBy(xpath = "//form[@class='edit_role_budget']/div/input[@id='role_budget_inherit_end_date']") private Checkbox cbEndDate;
	@FindBy(xpath = "//form[@class='edit_role_budget']/div/input[@id='role_budget_end_date']") private Textbox txtEndDate;
	@FindBy(xpath = "//form[@class='edit_role_budget']/div/input[@id='role_budget_rate']") private Textbox txtRate;
	@FindBy(xpath = "//*[@id='rate_comments']") private Textbox txtComment;
	@FindBy(xpath = "//input[@value='Update Role budget']") private Button btnUpdateComment;
	@FindBy(xpath = "//*[@id='notification-area']/div") private Label lblError;
	
	/**Constructor**/
	public Accounts(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}
	
	/**Page Interactions**/
	
	public boolean verifyPageIsLoaded(){
		return PageLoaded.isElementLoaded(this.getClass(), driver, tblAccounts);	
	}

	/*
	 * Click on accounts tab 
	 * Make sure that the correct page loads
	 * author: Daniel Smith
	 */
	public void click_accounts_tab(String username)
	{
		if (lnkAccountsTab.isDisplayed() == true)
		{
			System.out.println(username +" has account permissions");
			lnkAccountsTab.click();
		}
		
	}
	
	
	/**
	 * This method gets the number of rows on the Accounts page
	 * @author Paul
	 */
	public int getAccountsTableRows() {
		int rowCount = 0;
		try
		{
			rowCount = tblAccounts.getRowCount();
		}
		catch(NullPointerException e)
		{
			System.out.println("Null pointer exception, no accounts found  \n" + e.getLocalizedMessage());
		}
		return (rowCount - 1);
	}

	/**
	 * This method gets the number of Accounts reported by the page
	 * @author Paul
	 * @return 
	 */
	public Integer getNumberOfAccounts() {
		String str;
		String delims;
		String[] tokens = null;
		Integer lastItem = null;
		
		try
		{
			tblAccounts.syncEnabled(3);
			str = elmNumberPages.getText();
			delims = "[ ]";
			tokens = str.split(delims);
			lastItem = tokens.length;
			return Integer.valueOf(tokens[lastItem-1]);
		}
		catch(NoSuchElementException e)
		{
			System.out.println("No such exception, no accounts found \n" + e.getLocalizedMessage());
		}
		return lastItem;
		
	}
	
	/*
	 * Change the number showing for accounts per page to 100
	 * author: Daniel Smith
	 */
	public void accountsPerPage()
	{
		try
		{
			lstAccountPerPage.selectValue("100");
		}
		catch(NoSuchElementException e)
		{
			System.out.println("No such element found.  \n" + e.getLocalizedMessage());
		}
		catch(UndeclaredThrowableException e)
		{
			System.out.println("Undeclared throwable exception  \n" + e.getLocalizedMessage());
		}
		
	}
	
	/*
	 * Sort accounts table by industry
	 * @author: Daniel Smith
	 */
	public void sort_by_industry()
	{
		if(lnkIndustry.isDisplayed() == true)
		{
			System.out.println("Link 'Industry' is present");
			lnkIndustry.click();
		}
		else
			System.out.println("Link 'Industry' not on current page");
	
	}
	
	public void clickAccountLink(String strAccount){
		String xpathExpression;
		xpathExpression = "//td//a[contains(text(),'" + strAccount + "')]";
		Link lnkAccount = driver.findLink(By.xpath(xpathExpression));
		lnkAccount.click();
	}
	
	public void clickProjectLink(String strProject){
		/*String xpathExpression;
		xpathExpression = "//td//a[contains(text(),'" + strProject + "')]";
		Link lnkProject = driver.findLink(By.xpath(xpathExpression));
		lnkProject.syncEnabled(5,true);
		lnkProject.click();*/
		
		// verify project is in project column
		// get project column
		Integer intColumn = 1;
		Integer intRow = tblProjects.getRowWithCellText(strProject, intColumn);
		tblProjects.findElement(By.linkText(strProject)).click();
		
		//tblProjects.clickCell(intRow, intColumn);
	}
	
	public Element verifyProjectLink(String strProject){
		Integer intColumn = 1;
		Integer intRow = tblProjects.getRowWithCellText(strProject, intColumn);
		
		tblProjects.findElement(By.linkText(strProject)).click();
		Element eleProject = tblProjects.findElement(By.linkText(strProject));
		
		return eleProject;
		
	}
	
	public void clickSubprojectLink(String strSubProject){
		String xpathExpression;
		xpathExpression = "//td//a[contains(text(),'" + strSubProject + "')]";
		Link  lnkSubProject = driver.findLink(By.xpath(xpathExpression));
		lnkSubProject.syncEnabled(5,true);
		lnkSubProject.click();
	}
	
	public void clickRoleLink(String strRole){
		String xpathExpression;
		xpathExpression = "//td//a[contains(text(),'" + strRole + "')]";
		Link lnkRole = driver.findLink(By.xpath(xpathExpression));
		lnkRole.syncEnabled(5,true);
		lnkRole.click();
		PageLoaded.isDomComplete(driver, 5);
	}
	
	public void clickAssignEmployee(){
		btnAssignEmployee.syncEnabled(5,true);
		btnAssignEmployee.click();
	}

	public void assignEmployee(String strAccount, String strProject, String strSubProject, String strRole, String strEmployeeName) {
		FilledRoleForm filledRoleForm = new FilledRoleForm(driver);
		
		clickAccountLink(strAccount);
		
		clickProjectLink(strProject);
		
		clickSubprojectLink(strSubProject);
		
		clickRoleLink(strRole);
		
		filledRoleForm.selectEmployee(strEmployeeName);
		
	}
	
	public void clickAddAccount(){
		btnAddAccount.click();
	}
	
	public void setAccountNameTextbox(String strAccountName){
		txtAccountName.set(strAccountName);
	}
	
	public void selectIndustry(String strIndustry){
		lstIndustry.select(strIndustry);
	}
	
	public void clickCreateAccount(){
		btnCreateAccount.click();
	}
	
	public boolean verifyAccountLink(String strAccountName){
		String xpathExpression;
		
		xpathExpression = "//td//a[contains(text(),'" + strAccountName + "')]";
		
		Link lnkAccount = driver.findLink(By.xpath(xpathExpression));
		
		return lnkAccount.isDisplayed();
	}
	
	public String createAccount(){
		clickAddAccount();
		
		PageLoaded.isDomComplete(driver, 5);
		
		String strAccountName = Randomness.randomAlphaNumeric(10);
		
		setAccountNameTextbox(strAccountName);
		
		selectIndustry("Other");
		
		clickCreateAccount();
		
		//clickAccountLink(strAccountName);
		
		return strAccountName;
		
	}
	
	/**
	 * Checks if the add account button is visible.
	 * 
	 * @return <code>true</code> if the add account button is visible, 
	 * <code>false</code> otherwise.
	 * @author Darryl Papke
	 */
	public boolean verifyAddButtonIsVisible() {
		return btnAddAccount.syncVisible(3, false);
	}
	
	/**
	 * Checks if the edit account button is visible.
	 * 
	 * @return <code>true</code> if the edit account button is visible, 
	 * <code>false</code> otherwise.
	 * @author Darryl Papke
	 */
	public boolean verifyEditButtonIsVisible() {
		return btnEditAccount.syncVisible(3, false);
	}
	
	/**
	 * Clicks the first account link in the accounts table.
	 * 
	 * @author Darryl Papke
	 */
	public void clickFirstAccountLink() {
		tblAccounts.getCell(2, 1).findElement(By.cssSelector("a[class='ng-binding']")).click();
	}
	
	/**
	 * Checks if the Quick Nav button is displayed.
	 * 
	 * @return <code>true</code> if the Quick Nav button is visible, 
	 * <code>false</code> otherwise.
	 * @author Darryl Papke
	 */
	public boolean verifyQuickNavButtonIsVisible() {
		return btnQuickNav.syncVisible(5, true);
	}
	
	/**
	 * Goes through each page of accounts and checks if the Quick Nav button
	 * is visible on each page.
	 * 
	 * @return <code>true</code> if the Quick Nav button is on each page, 
	 * <code>false</code> otherwise.
	 * @author Darryl Papke
	 */
	public boolean verifyQuickNavButtonEachPage() {
		boolean answer = false;
		PageLoaded.isDomComplete(driver, 5);
		for(Button page : btnPages) {
			page.syncEnabled(5);
			page.click();
			answer = verifyQuickNavButtonIsVisible();
			if(!verifyQuickNavButtonIsVisible()) {
				return false;
			}
		}
		return answer;
	}
	
	/**
	 * Clicks the Quick Nav button.
	 * 
	 * @author Darryl Papke
	 */
	public void clickQuickNav() {
		btnQuickNav.click();
	}

	/**
	 * Clicks the close quick nav button.
	 * 
	 * @author Darryl Papke
	 */
	public void closeQuickNav() {
		PageLoaded.isDomComplete(driver, 5);
		btnCloseQuickNav.click();
	}
	
	/**
	 * Checks if the Quick Nav close button is visible.
	 * 
	 * @return <code>true</code> if the Quick Nav close button is 
	 * visible, <code>false</code> otherwise.
	 * @author Darryl Papke
	 */
	public boolean verifyQuickNavCloseButtonIsVisible() {
		PageLoaded.isDomComplete(driver, 5);
		return btnCloseQuickNav.syncVisible(3, false);
	}
	
	/**
	 * Clicks on a cell in the Accounts table from given coordinates.
	 * 
	 * @param row Desired row in which to search for a particular cell
	 * @param column Desired column in which to find the cell
	 * @author Darryl Papke
	 */
	public void selectCell(int row, int column) {
		tblAccounts.clickCell(row, column);
		PageLoaded.isDomComplete(driver, 1);
	}
	
	public void clickEditAccount() {
		btnEditAccount.click();
	}
	
	public void clickFirstProject() {
		lnkFirstProject.click();
	}
	
	public void clickFirstRole() {
		lnkFirstRole.click();
	}
	
	public void clickEditFirstRate() {
		btnEditFirstRate.click();
	}
	
	public void uncheckStartDate() {
		cbStartDate.syncVisible(1, true);
		if(cbStartDate.isChecked()) {
			cbStartDate.uncheck();
		}
	}

	/**
	 * This method sends the Start Date box the date passed
	 * @param dateKeys - MMDDYYYY to be entered into Start Date
	 */
	public void enterStartDate(String dateKeys) {
		txtStartDate.syncEnabled(1,true);
		txtStartDate.sendKeys(dateKeys);
	}
	
	public void uncheckEndDate() {
		cbEndDate.syncVisible(1, true);
		if(cbEndDate.isChecked()) {
			cbEndDate.uncheck();
		}
	}

	/**
	 * This method sends the End Date box the date passed
	 * @param dateKeys - MMDDYYYY to be entered into End Date
	 */
	public void enterEndDate(String dateKeys) {
		txtEndDate.syncEnabled(2,true);
		txtEndDate.sendKeys(dateKeys);
	}
	
	/**
	 * This method enters a passed rate into the role rate
	 * @param rateKeys - Rate to be entered without "$"
	 */
	public void enterRate(String rateKeys) {
		txtRate.click();
		txtRate.clear();
		txtRate.sendKeys(rateKeys);
	}
	
	/**
	 * This method enters a passed comment to the role comment
	 * @param comment - Comment to be entered
	 */
	public void enterComment(String comment) {
		txtComment.click();
		txtComment.sendKeys(comment);
	}
	
	public void clickUpdateComment() {
		btnUpdateComment.click();
	}
	
	/**
	 * Parses the error message from attempting to enter out of bounds dates for
	 *  an employee role.
	 * @Author Andrew McGrail
	 */	
	public String checkError(){
		String parseThis = lblError.getText().toString();
		String parsedString = parseThis.substring(2, 94);
		return parsedString;
	}
	
	public boolean verifyAccountPage() {
		return btnEditAccount.syncVisible(2,true);
	}
	
	public boolean verifyProjectPage() {
		return lnkFirstRole.syncVisible(2,true);
	}
	
	public boolean verifyRolePage() {
		return btnEditFirstRate.syncVisible(2,true);
	}
	
	public boolean verifyRoleEditModal() {
		return cbStartDate.syncVisible(2,true);
	}
	
	public boolean verifyStartDateEditable() {
		return txtStartDate.syncVisible(2,true);
	}
	
	public boolean verifyStartDateContent(String date) {
		String parsedDate = date.substring(4)+"-"+date.substring(0, 2)+"-"+date.substring(2,4);
		return txtStartDate.getText().equalsIgnoreCase(parsedDate);
	}
	
	public boolean verifyEndDateEditable() {
		return txtEndDate.syncVisible(2,true);
	}
	
	public boolean verifyEndDateContent(String date) {
		String parsedDate = date.substring(4)+"-"+date.substring(0, 2)+"-"+date.substring(2,4);
		return txtEndDate.getText().equalsIgnoreCase(parsedDate);
	}
	
	public boolean verifyRate(String rate) {
		return txtRate.getText().substring(2).equalsIgnoreCase(rate);
	}
	
	public boolean verifyTextCommentContent(String comment) {
		return txtComment.getText().equalsIgnoreCase(comment);
	}
}

