package com.bluesource.accounts;

import com.orasi.bluesource.*;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;
import org.testng.ITestContext;
import org.testng.annotations.*;

public class Audit_Table_Has_Errors extends WebBaseTest {
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
	public void audit_Table_Has_Errors() {
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

		TestReporter.logStep("Logging in as Admin");
		loginPage.AdminLogin();

		TestReporter.logStep("Navigating to Accounts");
		header.navigateAccounts();

		TestReporter.logStep("Clicking first Accounts link");
		accounts.clickFirstAccountLink();

		TestReporter.logStep("Clicking 'Edit Account' button");
		accounts.clickEditAccount();

		TestReporter.logStep("Getting account name");
		oldAccountName = editAccountForm.getAccountName();

		TestReporter.logStep("Getting account industry");
		oldIndustry = editAccountForm.getIndustry();

		TestReporter.assertGreaterThanZero(oldIndustry);

		TestReporter.logStep("Renaming account");
		newAccountName = editAccountForm.testRenameAccount();

		TestReporter.logStep("Changing account industry");
		newIndustry = editAccountForm.testChangeIndustry();

		TestReporter.assertGreaterThanZero(newIndustry);

		TestReporter.logStep("Clicking 'Update Account'");
		editAccountForm.clickUpdateAccount();

		TestReporter.logStep("Clicking 'View All' link");
		accounts.clickViewAllAudits();

		TestReporter.assertTrue(auditHistory.verifyNoAuditRecordGaps(),"Verifying that the Audit table has no gaps");

		TestReporter.assertTrue(auditHistory.verifyChangedBy(changedBy),"Verifying user that made the changes");

		TestReporter.assertTrue(auditHistory.verifyName(newAccountName),"Verifying new Account name");

		TestReporter.assertTrue(auditHistory.verifyAuditedChangeName(oldAccountName,newAccountName),"Verifying Audited Changes to name are accurate");

		TestReporter.assertTrue(auditHistory.verifyAuditedChangeIndustry(oldIndustry, newIndustry),"Verifying Audited Changes to industry are accurate");
	}
}