/**
 * Tests Company Admin_Admin_Companies - Isse #946 & #964
 * This test creates a company with a given name and logo,
 *  then verifies the company was created correctly. The test
 *  goes on to edit a company's name and logo and verify if
 *  those are changed correctly
 *  @author andrew.mcgrail
 */
package com.bluesource;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.Admin;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;

public class CompanyAdminAdminCompanies extends WebBaseTest{

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
	
    @Test
    public void testCompanyAdminAdminCompanies()
    {
    	String logoFilePath = "C:\\Users\\andrew.mcgrail\\Desktop\\Fortnite Logo.png";
    	String logoName = "Fortnite Logo";
    	String logoFilePath2 = "C:\\Users\\andrew.mcgrail\\Desktop\\Skull And Crossbones Logo.png";
    	String logoName2 = "Skull And Crossbones Logo";
    	String companyName = "Pleasant Park Punishers";
    	String companyName2 = "Retail Row Renegades";
    	
    	LoginPage loginPage = new LoginPage(getDriver());
    	Header header = new Header(getDriver());
    	Admin adminPage = new Admin(getDriver());
    	
    	// Step 1 Open the browser and navigate to "http://10.238.243.127".
    	// Step 2 Login as 'company.admin'.
    	loginPage.AdminLogin();
    	// Step 3 Click on 'Admin' -> 'Companies'.
    	header.navigateCompanies();
    	TestReporter.assertTrue(adminPage.verifyAddCompany(), "Successfully landed on Companies page");
    	// Step 4 Click on the 'Add Company' button.
    	adminPage.clickAddCompany();
    	TestReporter.assertTrue(adminPage.verifyAddCompanyModal(), "Successfully opened Add Company Modal");
    	// Step 5 Type in a Name inside the 'Name' textbox.
    	adminPage.populateCompanyName(companyName);
    	// Step 6 Select an image to upload into the 'Logo' file upload field.
    	adminPage.setCompanyLogo(logoFilePath);
    	// Step 7 Click the 'Create Company' button and verify a Company is created with the Logo displaying.
    	adminPage.clickCreateCompany();
    	TestReporter.assertTrue(adminPage.verifyCreatedCompanyNameLogo(companyName, logoName), "Verified the company "+companyName+" was created with "+logoName);
    	// Step 8 Click on the pencil icon to Edit one of the Companies.
    	adminPage.clickEditThirdCompany();
    	// Step 9 Click the 'Change' link and update the Logo's file.
    	adminPage.updateCompanyLogo(logoFilePath2);
    	// Step 10 Click the 'Update Company' button and verify the Company's Logo is updated.
    	adminPage.clickUpdateCompany();
    	TestReporter.assertTrue(adminPage.verifyThirdCompanyLogo(logoName2), "Verified the Company Logo was updated to "+logoName2);
    	// Step 11 Click on the pencil icon to Edit one of the Companies.
    	adminPage.clickEditThirdCompany();
    	// Step 12 Type in another name into the 'Name' textbox.
    	adminPage.updateCompanyName(companyName2);
    	TestReporter.logStep("Updated the Company name to "+companyName2);
    	// Step 13 Click the 'Update Company' button and verify the Company's Name is updated.
    	adminPage.clickUpdateCompany();
    	TestReporter.assertTrue(adminPage.verifyThirdCompanyName(companyName2), "Verified the Company name was changed to "+companyName2);
    }
}