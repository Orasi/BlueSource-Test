package com.bluesource.accounts;

import com.orasi.bluesource.*;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;
import org.testng.ITestContext;
import org.testng.annotations.*;

public class AuditTableHasErrors extends WebBaseTest {
	@BeforeMethod
	@Parameters({"runLocation", "browserUnderTest", "browserVersion",
			"operatingSystem", "environment"})
	public void setup(@Optional String runLocation, String browserUnderTest,
					  String browserVersion, String operatingSystem, String environment) {
		setApplicationUnderTest("BLUESOURCE");
		setBrowserUnderTest(browserUnderTest);
		setBrowserVersion(browserVersion);
		setOperatingSystem(operatingSystem);
		setRunLocation(runLocation);
		setEnvironment(environment);
		setThreadDriver(true);
		testStart("");
	}

	@AfterMethod
	public void close(ITestContext testResults) {
		endTest("TestAlert", testResults);
	}

	@Test
	public void auditTableHasErrors() {
		//Test Variables
		String changedBy = "company admin";
		String newAccountName;
		int newIndustry;
		String oldAccountName;
		int oldIndustry;

		//Page Models
		LoginPage loginPage = new LoginPage(getDriver());
		Header header = new Header(getDriver());
		Accounts accounts = new Accounts(getDriver());
		EditAccountForm editAccountForm = new EditAccountForm(getDriver());
		AuditHistory auditHistory = new AuditHistory(getDriver());

		TestReporter.assertTrue(loginPage.verifyPageIsLoaded(), "Verifying login page is loaded");

		TestReporter.logStep("Logging in as Admin");
		loginPage.AdminLogin();

		TestReporter.logStep("Navigating to Accounts");
		header.navigateAccounts();

		TestReporter.assertTrue(accounts.verifyAccountsPageIsLoaded(),"Verifying Accounts page loaded");

		TestReporter.logStep("Clicking first Accounts link");
		accounts.clickFirstAccountLink();

		TestReporter.assertTrue(accounts.verifyFirstAccountPageIsLoaded(),"Verifying first account page is loaded");

		TestReporter.logStep("Clicking 'Edit Account' button");
		accounts.clickEditAccount();

		TestReporter.logStep("Getting account name");
		oldAccountName = editAccountForm.getAccountName();

		TestReporter.assertNotEquals(oldAccountName,"","Verifying account name isn't empty");

		TestReporter.logStep("Getting account industry");
		oldIndustry = editAccountForm.getIndustry();

		TestReporter.assertGreaterThanZero(oldIndustry);

		TestReporter.logStep("Renaming account");
		newAccountName = editAccountForm.testRenameAccount();

		TestReporter.assertNotEquals(newAccountName,"","Verifying new account name isn't empty");

		TestReporter.logStep("Changing account industry");
		newIndustry = editAccountForm.testChangeIndustry();

		TestReporter.assertGreaterThanZero(newIndustry);

		TestReporter.logStep("Clicking 'Update Account'");
		editAccountForm.clickUpdateAccount();

		TestReporter.logStep("Clicking 'View All' link");
		accounts.clickViewAllAudits();

		TestReporter.assertTrue(auditHistory.verifyPageIsLoaded(),"Verifying Audit History page is loaded");

		String auditedNameChange = "Name: From '" + oldAccountName + "' to '" + newAccountName + "'";
		String auditedIndustryChange = "Industry: From '" + oldIndustry + "' to '" + newIndustry + "'";

		TestReporter.assertTrue(auditHistory.verifyNoAuditRecordGaps(),"Verifying that the Audit table has no gaps");

		TestReporter.assertEquals(auditHistory.getNewestChangedBy(),changedBy,"Verifying user that made the changes");

		TestReporter.assertEquals(auditHistory.getNewestName(),newAccountName,"Verifying new Account name");

		TestReporter.assertEquals(auditHistory.getNewestAuditedNameChange(),auditedNameChange,"Verifying Audited Changes to name are accurate");

		TestReporter.assertEquals(auditHistory.getNewestAuditedIndustryChange(),auditedIndustryChange,"Verifying Audited Changes to industry are accurate");
	}
}