package com.orasi.bluesource;

import com.orasi.utils.Randomness;
import com.orasi.utils.TestReporter;
import com.orasi.web.OrasiDriver;
import com.orasi.web.PageLoaded;
import com.orasi.web.webelements.*;
import com.orasi.web.webelements.impl.internal.ElementFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
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
	@FindBy(xpath = "//div[@class='test']/table") private Webtable tblRoles;
	@FindBy(xpath = "//th[contains(text(),'Project')]/../../..") private Webtable tblSubProjects;

	/**Constructor**/
	public Accounts(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}
	
	/**Page Interactions**/

	/**
	 * @author David Grayson
	 * @param strAccount {@link String} name of parent account
	 * @param strProject {@link String} name of project to verify
	 * @return {@link Boolean} Returns <code>true</code> if all roles, sub projects, and sub project roles are correct and displayed in the quick nav menu
	 */
	public boolean verifyQuickNavProjectExpansion(String strAccount, String strProject){
		List<String> roles = getAllRoleNames();
		List<String> subProjects = getAllSubProjectNames();

		for (String role:roles){
			TestReporter.log("verifying role: " + role);
			if (!verifyQuickNavProjectRoleExists(strAccount, strProject, role))
				return false;
		}

		for (String subProject:subProjects){
			if (!verifyQuickNavSubProjectExists(strAccount, strProject, subProject))
				return false;
			if (!verifyQuickNavSubProjectExpansion(strAccount, strProject, subProject))
				return false;
		}

		return true;
	}

	/**
	 * navigates to the sub projects page, gets and verifies all roles on the page,
	 * then navigates back to parent project page with quick nav menu opened to its starting location
	 * @author David Grayson
	 * @param strAccount {@link String} name of parent account
	 * @param strProject {@link String} name of parent project
	 * @param strSubProject {@link String} name of selected sub project
	 * @return {@link Boolean} Returns <code>true</code> if all sub project roles exist in quick nav menu
	 */
	private boolean verifyQuickNavSubProjectExpansion(String strAccount, String strProject, String strSubProject){
		clickQuickNavSubProject(strAccount, strProject, strSubProject);
		clickQuickNav();
		expandQuickNavAccount(strAccount);
		expandQuickNavProject(strAccount, strProject);
		expandQuickNavSubProject(strAccount, strProject, strSubProject);

		for (String subRole:getAllRoleNames()){
			if (!verifyQuickNavSubProjectRoleExists(strAccount, strProject, strSubProject, subRole))
				return false;
		}

		clickQuickNavProject(strAccount, strProject);
		clickQuickNav();
		expandQuickNavAccount(strAccount);
		expandQuickNavProject(strAccount, strProject);

		return true;
	}

	/**
	 * @author David Grayson
	 * @param strAccount {@link String} name of parent account
	 * @param strProject {@link String} name of parent project
	 * @param strSubProject {@link String} name of parent sub project
	 * @param strSubRole {@link String} name of selected sub project role
	 * @return {@link Boolean} <code>true</code> if the sub project pages link URL has the same id as the quick nav menu link URL
	 */
	private boolean verifyQuickNavSubProjectRoleExists(String strAccount, String strProject, String strSubProject, String strSubRole) {
		String xpath = buildQuickNavSubProjectRoleXPath(strAccount, strProject, strSubProject, strSubRole);
		String[] split = driver.findLink(By.xpath(xpath)).getURL().split("[?]");
		return driver.findLink(By.linkText(strSubRole)).getURL().contains(split[0]);
	}

	/**
	 * @author David Grayson
	 * @param strAccount {@link String} name of parent account
	 * @param strProject {@link String} name of parent project
	 * @param strSubProject {@link String} name of selected sub project
	 * @return {@link Boolean} <code>true</code> if the sub project link URL on the project page matches the quick nav menu link URL
	 */
	private boolean verifyQuickNavSubProjectExists(String strAccount, String strProject, String strSubProject){
		String xpath = buildQuickNavSubProjectXPath(strAccount, strProject, strSubProject);
		return driver.findLink(By.xpath(xpath)).getURL().equals(driver.findLink(By.linkText(strSubProject)).getURL());
	}

	/**
	 * @author David Grayson
	 * @param strAccount {@link String} name of parent account
	 * @param strProject {@link String} name of parent project
	 * @param strRole {@link String} name of selected role
	 * @return {@link Boolean} <code>true</code> if the role link in the quick nav menu has the same id
	 * 							as the role link on the project page
	 */
	private boolean verifyQuickNavProjectRoleExists(String strAccount, String strProject, String strRole){
		String xpath = buildQuickNavProjectRoleXPath(strAccount, strProject, strRole);
		String[] split = driver.findLink(By.xpath(xpath)).getURL().split("[?]");
		return driver.findLink(By.linkText(strRole)).getURL().contains(split[0]);
	}

	/**
	 * @author David Grayson
	 * @return {@link List<String>} of all sub project names on the page
	 */
	private List<String> getAllSubProjectNames(){
		ArrayList<String> subProjectNames = new ArrayList<>();
		for (int i=1; i<tblSubProjects.getRowCount(); i++){
			subProjectNames.add(tblSubProjects.getCell(i,1).getText());
		}
		return subProjectNames;
	}

	/**
	 * @author David Grayson
	 * @return {@link List<String> } of all role names on the page
	 */
	private List<String> getAllRoleNames(){
		ArrayList<String> roleNames = new ArrayList<>();
		//Webtable tblRoles = driver.findWebtable(By.xpath("//div[@class='test']/table"));
		//TestReporter.log(String.valueOf(tblRoles.getRowCount()));
		for (int i=1; i<=tblRoles.getRowCount(); i++){
			if (!tblRoles.getCell(i,1).getText().isEmpty())
				roleNames.add(tblRoles.getCell(i,1).getText());
			//TestReporter.log(tblRoles.getCell(i,1).getText());
		}
		return roleNames;
	}

	/**
	 * Takes in a random integer that selects one of the Accounts in the Quick Nav menu to click
	 * @author David Grayson
	 * @param selector {@link Integer }
	 */
	public void clickRandomQuickNavAccountLink(int selector){
		PageLoaded.isDomComplete(driver);
		clickQuickNav();
		Link lnkRandom = driver.findLinks(By.xpath("//a[@data-depth=1]")).get(selector);

		lnkRandom.syncEnabled(5);
		lnkRandom.syncVisible(5);
		lnkRandom.click();
	}

	/**
	 * @author David Grayson
	 * @param strAccount {@link String} name of parent account
	 * @param strProject {@link String} name of parent project
	 * @param strSubProject {@link String} name of selected sub project
	 * @return {@link Boolean} <code>true</code> if the page URL matches the quick nav link
	 */
	public boolean verifySubProjectPage(String strAccount, String strProject, String strSubProject){
		String xpathExpression = buildQuickNavSubProjectXPath(strAccount, strProject, strSubProject);
		PageLoaded.isDomComplete(driver);
		return driver.getCurrentUrl().equals(driver.findLink(By.xpath(xpathExpression)).getURL());
	}

	/**
	 * @author David Grayson
	 * @param strAccount {@link String} name of parent account
	 * @param strProject {@link String} name of parent project
	 * @param strRole {@link String} name of selected role
	 * @return {@link Boolean} <code>true</code> if the page URL matches the quick nav link
	 */
	public boolean verifyRolePage(String strAccount, String strProject, String strRole){
		String xpathExpression = buildQuickNavProjectRoleXPath(strAccount, strProject, strRole);
		PageLoaded.isDomComplete(driver);
		return driver.getCurrentUrl().equals(driver.findLink(By.xpath(xpathExpression)).getURL());
	}

	/**
	 * @author David Grayson
	 * @param strAccount {@link String} name of selected account
	 * @return {@link Boolean} <code>true</code> if the page URL matches the quick nav link
	 */
	public boolean verifyAccountPage(String strAccount){
		PageLoaded.isDomComplete(driver);
		return driver.getCurrentUrl().equals(driver.findLink(By.xpath("//a[text()='" + strAccount + "']")).getURL());
	}

	public void clickQuickNavSubProjectRole(String strAccount, String strProject, String strSubProject, String strSubProjectRole) {
		String xpath = buildQuickNavSubProjectRoleXPath(strAccount,strProject,strSubProject,strSubProjectRole);
		Link lnkSubProjectRole = driver.findLink(By.xpath(xpath));

		if (verifyLinkClickable(lnkSubProjectRole)){
			lnkSubProjectRole.click();
		} else {
			clickQuickNav();
			if (verifyLinkClickable(lnkSubProjectRole)){
				lnkSubProjectRole.click();
			} else {
				expandQuickNavAccount(strAccount);
				expandQuickNavProject(strAccount,strProject);
				expandQuickNavSubProject(strAccount,strProject,strSubProject);
				clickQuickNavSubProjectRole(strAccount,strProject,strSubProject,strSubProjectRole);
			}
		}
	}

	/**
	 * clicks the quick nav menu link to the provided sub project
	 * @author David Grayson
	 * @param strAccount {@link String} name of the parent account
	 * @param strProject {@link String} name of the parent project
	 * @param strSubProject {@link String} name of the selected sub project
	 */
	public void clickQuickNavSubProject(String strAccount, String strProject, String strSubProject){
		String xpathExpression = buildQuickNavSubProjectXPath(strAccount, strProject, strSubProject);
		Link lnkQuickNavSubProject = driver.findLink(By.xpath(xpathExpression));

		if (verifyLinkClickable(lnkQuickNavSubProject))
		{
			lnkQuickNavSubProject.click();
		}
		else
		{
			clickQuickNav();
			if (verifyLinkClickable(lnkQuickNavSubProject))
			{
				lnkQuickNavSubProject.click();
			}
			else
			{
				expandQuickNavAccount(strAccount);
				expandQuickNavProject(strAccount,strProject);
				lnkQuickNavSubProject.click();
			}
		}
	}

	/**
	 * clicks on the expansion div for the sub project
	 * @author David Grayson
	 * @param strAccount {@link String} name of the parent account
	 * @param strProject {@link String} name of the parent project
	 * @param strSubProject {@link String} name of the selected sub project
	 */
	public void expandQuickNavSubProject(String strAccount, String strProject, String strSubProject){
		String xpathExpression = buildExpandQuickNavXPath(buildQuickNavSubProjectXPath(strAccount, strProject, strSubProject));
		Element elmExpandSubProject = driver.findElement(By.xpath(xpathExpression));

		if (verifyElementClickable(elmExpandSubProject))
		{
			elmExpandSubProject.click();
		}
		else if (!verifyQuickNavCloseButtonIsVisible())
		{
			clickQuickNav();
			if (verifyElementClickable(elmExpandSubProject))
			{
				elmExpandSubProject.click();
			}
			else
			{
				expandQuickNavAccount(strAccount);
				expandQuickNavProject(strAccount,strProject);
				elmExpandSubProject.click();
			}
		}
	}

	/**
	 * clicks on a role in the quick nav menu
	 * @author David Grayson
	 * @param strAccount {@link String} name of the parent account
	 * @param strProject {@link String} name of the parent project
	 * @param strRole {@link String} name of the selected project role
	 */
	public void clickQuickNavRole(String strAccount, String strProject, String strRole){
		String xpathExpression = buildQuickNavProjectRoleXPath(strAccount, strProject, strRole);
		Link lnkQuickNavProjectRole = driver.findLink(By.xpath(xpathExpression));

		if (verifyLinkClickable(lnkQuickNavProjectRole))
		{
			lnkQuickNavProjectRole.click();
		}
		else
		{
			clickQuickNav();
			if (verifyLinkClickable(lnkQuickNavProjectRole))
			{
				lnkQuickNavProjectRole.click();
			}
			else
			{
				expandQuickNavAccount(strAccount);
				expandQuickNavProject(strAccount,strProject);
				clickQuickNavRole(strAccount,strProject,strRole);
			}
		}
	}

	/**
	 * clicks on a project in the quick nav menu
	 * @author David Grayson
	 * @param strAccount {@link String} name of the parent account
	 * @param strProject {@link String} name of the parent project
	 */
	public void clickQuickNavProject(String strAccount, String strProject){
		String xpathExpression = buildQuickNavProjectXPath(strAccount, strProject);
		Link lnkQuickNavProject = driver.findLink(By.xpath(xpathExpression));

		if(verifyLinkClickable(lnkQuickNavProject))
		{
			lnkQuickNavProject.click();
		}
		else
		{
			clickQuickNav();
			if (verifyLinkClickable(lnkQuickNavProject))
			{
				clickQuickNavProject(strAccount,strProject);
			}
			else
			{
				expandQuickNavAccount(strAccount);
				clickQuickNavProject(strAccount,strProject);
			}
		}
	}

	/**
	 * expands a project in the quick nav menu
	 * @author David Grayson
	 * @param strAccount {@link String} name of the parent account
	 * @param strProject {@link String} name of the parent project
	 */
	public void expandQuickNavProject(String strAccount, String strProject){
		String xpathExpression = buildExpandQuickNavXPath(buildQuickNavProjectXPath(strAccount, strProject));
		Element elmExpandProject = driver.findElement(By.xpath(xpathExpression));

		if(verifyElementClickable(elmExpandProject))
		{
			elmExpandProject.click();
		}
		else
		{
			clickQuickNav();
			if (verifyElementClickable(elmExpandProject))
			{
				expandQuickNavProject(strAccount,strProject);
			}
			else
			{
				expandQuickNavAccount(strAccount);
				expandQuickNavProject(strAccount,strProject);
			}
		}

	}

	/**
	 * expands an account in the quick nav menu
	 * @author David Grayson
	 * @param strAccount {@link String} name of the account
	 */
	public void expandQuickNavAccount(String strAccount){
		String xpathExpression = buildExpandQuickNavXPath(buildQuickNavAccountXPath(strAccount));
		Element elmExpandAccount = driver.findElement(By.xpath(xpathExpression));

		if (verifyElementClickable(elmExpandAccount)) //checks that the element is enables and visible
		{
			elmExpandAccount.click();
		}
		else //if quick nav menu isn't open then open it
		{
			clickQuickNav();
			expandQuickNavAccount(strAccount);
		}
	}

	/**
	 * clicks on an account in the quick nav menu
	 * @author David Grayson
	 * @param strAccount {@link String} name of the account
	 */
	public void clickQuickNavAccount(String strAccount){
		String xpathExpression = buildQuickNavAccountXPath(strAccount);
		Link lnkQuickNavAccount = driver.findLink(By.xpath(xpathExpression));

		if (verifyLinkClickable(lnkQuickNavAccount))//checks that the link is enabled and visible
		{
			lnkQuickNavAccount.click();
		}
		else if (!verifyQuickNavCloseButtonIsVisible())//if quick nav menu isn't open then open it and try again
		{
			clickQuickNav();
			clickQuickNavAccount(strAccount);
		}
	}

	/**
	 * @author David Grayson
	 * @param link {@link Link} Link to check
	 * @return {@link Boolean} Returns <code>true</code> is element is enabled and visible, <code>false</code> otherwise
	 */
	public boolean verifyLinkClickable(Link link){
		return link.syncEnabled(5,false) && link.syncVisible(5,false);
	}

	/**
	 * @author David Grayson
	 * @param element {@link Element} Element to check
	 * @return {@link Boolean} Returns <code>true</code> is element is enabled and visible, <code>false</code> otherwise
	 */
	public boolean verifyElementClickable(Element element){
		return element.syncEnabled(5,false) && element.syncVisible(5,false);
	}

	/**
	 * @author David Grayson
	 * @param strAccount {@link String} name of the parent account
	 * @param strProject {@link String} name of the parent project
	 * @param strSubProject {@link String} name of the parent sub project
	 * @param strSubRole {@link String} name of the role to be selected
	 * @return {@link String} xapth to select the sub project role
	 */
	public String buildQuickNavSubProjectRoleXPath(String strAccount, String strProject, String strSubProject, String strSubRole) {
		return buildQuickNavSubProjectXPath(strAccount, strProject, strSubProject) + "//following::a[@data-depth=4 and contains(text(),'" + strSubRole + "')]";
	}

	/**
	 * @author David Grayson
	 * @param strAccount {@link String} name of the parent account
	 * @param strProject {@link String} name of the parent project
	 * @param strSubProject {@link String} name of the sub project to select
	 * @return {@link String} the xpath to select the sub project provided
	 */
	private String buildQuickNavSubProjectXPath(String strAccount, String strProject, String strSubProject){
		return buildQuickNavProjectXPath(strAccount, strProject) + "//following::a[@data-depth=3 and contains(text(),'" + strSubProject + "')]";
	}

	/**
	 * @author David Grayson
	 * @param strAccount {@link String } name of the parent account
	 * @param strProject {@link String } name of the parent project
	 * @param strRole {@link String } name of the role to select
	 * @return {@link String } the xpath to select the role provided
	 */
	private String buildQuickNavProjectRoleXPath(String strAccount, String strProject, String strRole){
		return buildQuickNavProjectXPath(strAccount, strProject) + "//following::a[@data-depth=3 and contains(text(),'" + strRole + "')]";
	}

	/**
	 * @author David Grayson
	 * @param strAccount {@link String } name of the parent account
	 * @param strProject {@link String } name of the project to select
	 * @return {@link String } xpath to select project provided
	 */
	private String buildQuickNavProjectXPath(String strAccount, String strProject){
		return buildQuickNavAccountXPath(strAccount) + "//following::a[@data-depth=2 and contains(text(),'" + strProject + "')]";
	}

	/**
	 * @author David Grayson
	 * @param xpath {@link String } xpath to element to be expanded
	 * @return {@link String } xpath to the expansion div of the element
	 */
	private String buildExpandQuickNavXPath(String xpath){
		return xpath + "/div[@class='pull-right expand-quick-nav']";
	}

	/**
	 * @author David Grayson
	 * @param strAccount {@link String } name of account to select
	 * @return {@link String } xpath to select the account provided
	 */
	private String buildQuickNavAccountXPath(String strAccount){
		return "//div[@class='quick-nav-group']//a[contains(text(),'" + strAccount + "')]";
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
		PageLoaded.isDomComplete(driver,5);
		btnQuickNav.syncEnabled(5);
		btnQuickNav.syncVisible(5);
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

