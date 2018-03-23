package com.bluesource.accounts;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.Accounts;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;

public class CollapseExpandSubProjectHeadersTest extends WebBaseTest{
	@BeforeMethod
    @Parameters({ "runLocation", "browserUnderTest", "browserVersion",
    	"operatingSystem", "environment" })
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
    public void close(ITestContext testResults){
    	endTest("TestAlert", testResults);
    }
    
    @Test(groups = {"smoke"} )
    public void testCollapseExpandHeaders() {
    	TestReporter.logStep("Login as an admin");
    	LoginPage loginPage = new LoginPage(getDriver());
    	loginPage.Login("ADMIN");
    	TestReporter.logStep("Click on the Accounts link");
    	Header header = new Header(getDriver());
    	header.navigateAccounts();
    	TestReporter.logStep("Navigate to an account that has a project with a subproject");
    	Accounts accounts = new Accounts(getDriver());
    	accounts.clickAccountLink("AccountOnlySubs1");
    	TestReporter.logStep("Navigate to a project with a subproject");
    	accounts.clickProjectLink("Project1");
    	TestReporter.logStep("Navigate to a subproject");
    	accounts.clickSubprojectLink("SubProject1");
    	TestReporter.logStep("Click on the headers, making sure they expand/collapse as intended");
    	TestReporter.assertTrue(accounts.verifyCollapseExpandHeaders(), "Verify the headers collapse/expand");
    }

}
