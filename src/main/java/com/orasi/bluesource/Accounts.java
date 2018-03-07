package com.orasi.bluesource;

import com.orasi.utils.Randomness;
import com.orasi.web.OrasiDriver;
import com.orasi.web.PageLoaded;
import com.orasi.web.webelements.*;
import com.orasi.web.webelements.impl.internal.ElementFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;

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
	@FindBy(xpath = "//*[@id=\"accordion\"]/div/div[5]/div/button[2]") private Button btnEditAccount;
	@FindBy(css = "div.btn.btn-secondary.btn-xs.quick-nav") private Button btnQuickNav;
	@FindBy(xpath = "//a[contains(@ng-bind, 'n + 1')]") private List<Button> btnPages;
	@FindBy(xpath = "//*[@id=\"project-list\"]/div/div[1]/div") private Button btnCloseQuickNav;
	@FindBy(xpath = "//th[contains(text(),'Rate')]/../../..") private Webtable tblRole;
	@FindBy(xpath = "//h4[@class='panel-title' and contains(text(),'Project Info')]") private Element elmProjectInfoPanelHeader;
	@FindBy(xpath = "//h4[@class='panel-title' and contains(text(),'Role Information')]") private Element elmRoleInfoPanelHeader;

	/**Constructor**/
	public Accounts(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}
	
	/**Page Interactions**/

	/**
	 * would use the {@link #verifyProjectLink(String)} but it doesn't do what it's name implies
	 * @author David Grayson
	 * @param project {@link String} name of Project
	 * @return {@link Boolean} Returns <code>true</code> if the link is clickable within 5 seconds, <code>false</code> otherwise.
	 */
	public boolean verifyProjectLinkValid(String project){
		return driver.findLink(By.linkText(project)).syncEnabled(5,false) &&
				driver.findLink(By.linkText(project)).syncVisible(5,false);
	}

	/**
	 * @author David Grayson
	 * @param strRole {@link String} The name of the Role
	 * @return {@link Boolean} Returns <code>true</code> if the link is enabled and visible, <code>false</code> otherwise.
	 */
	public boolean verifyRoleLink(String strRole) {
		return driver.findLink(By.linkText(strRole)).syncEnabled(5,false) &&
				driver.findLink(By.linkText(strRole)).syncVisible(5,false);
	}

	/**
	 * @author David Grayson
	 * @param roleRate {@link String} The Rate to edit
	 */
	public void clickEditRoleRate(String roleRate) {
		String xpath = "//td[contains(text(),'" + roleRate + "')]/../td/span[@class='actions actions-edit-time']/a";
		driver.findElement(By.xpath(xpath)).click();
	}

	/**
	 * @author David Grayson
	 * @param strAccount {@link String} name of Roles parent account
	 * @param strProject {@link String} name of Roles parent project
	 * @param strRole {@link String} name of role
	 * @return {@link Boolean} Returns <code>true</code> if the provided Roles page is loaded, <code>false</code> otherwise.
	 */
	public boolean verifyRolePageIsLoaded(String strAccount, String strProject, String strRole){
		return PageLoaded.isElementLoaded(this.getClass(),driver,elmRoleInfoPanelHeader,5)
				&& driver.findElement(By.xpath("//div[@class='breadcrumbs']")).getText()
				.equals("Accounts - " + strAccount + " - " + strProject + " - " + strRole);
	}

	/**
	 * @author David Grayson
	 * @param strAccount {@link String} name of Projects parent account
	 * @param strProject {@link String} name of project
	 * @return {@link Boolean} Returns <code>true</code> if the provided Projects page is loaded, <code>false</code> otherwise.
	 */
	public boolean verifyProjectPageIsLoaded(String strAccount, String strProject){
		return PageLoaded.isElementLoaded(this.getClass(),driver, elmProjectInfoPanelHeader,5)
				&& driver.findElement(By.xpath("//div[@class='breadcrumbs']")).getText()
				.equals("Accounts - " + strAccount + " - " + strProject);
	}

	/**
	 * @author David Grayson
	 * @param strAccount {@link String} name of Account
	 * @return {@link Boolean} Returns <code>true</code> if the provided Accounts page is loaded, <code>false</code> otherwise.
	 */
	public boolean verifyAccountPageIsLoaded(String strAccount){
		String xpath = "//div[@class='breadcrumbs']/a[contains(text(),'" + strAccount + "')]";
		return PageLoaded.isElementLoaded(this.getClass(),driver,tblProjects,5)
				&& driver.findLink(By.xpath(xpath)).syncVisible(5,false);
	}

	/**
	 * @author David Grayson
	 * @return {@link Boolean} Returns <code>true</code> if the Accounts table is loaded, <code>false</code> otherwise.
	 */
	public boolean verifyAccountsPageIsLoaded(){
		return PageLoaded.isElementLoaded(this.getClass(),driver,tblAccounts,5);
	}

	/**
	 * Checks if the rate provided matches the rate field on the Role page
	 * @author David Grayson
	 * @param rate {@link String} Rate to check
	 * @return {@link Boolean} Returns <code>true</code> if provided rate is in the table, <code>false</code> if not.
	 */
	public boolean verifyRoleRate(String rate) {
		return driver.findWebtable(By.xpath("//th[contains(text(), 'Rate')]/../../..")).getRowWithCellText(rate, 5) != 0;
	}

	/**
	 * Gets the rate field in the Role Table on a project page
	 * @author David Grayson
	 * @param role {@link String} the name of the role
	 * @return {@link String} Returns the Rate for the role provided or an empty {@link String} is something went wrong
	 */
	public String getRoleRateFromProjectPage(String role){
		final int colPosition = 2;
		if (tblRole.syncEnabled(5,false) && tblRole.syncVisible(5,false))
			return tblRole.getCell(tblRole.getRowWithCellText(role),colPosition).getText();
		else
			return "";
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
}

