package com.orasi.bluesource;

import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Link;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.impl.internal.ElementFactory;
import org.openqa.selenium.support.FindBy;

public class EditProjectForm {
	private OrasiDriver driver = null;

	/**Page Elements**/
	@FindBy(xpath = "//a[@href='#edit_form']") private Link lnkEditProjectTab;
	@FindBy(xpath = "//a[@href='#change_log']") private Link lnkChangeLogTab;
	@FindBy(xpath = "//form[@class='edit_project']//input[@id='project_notification_threshold']") private Textbox txtNotificationThreshold;
	@FindBy(xpath = "//textarea[@id='project_comments']") private Textbox txtEditProjectComments;
	@FindBy(xpath = "//div[@id='change_log']/div[1]/div[@class='note-text']") private Element elmMostRecentNoteText;
	@FindBy(xpath = "//input[@value='Update Project']") private Element elmUpdateProject;

	/**Constructor**/
	public EditProjectForm(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver,this);
	}

	/**Test Methods**/
	public void testEditNotificationThreshold() {
		if (txtNotificationThreshold.getText().equals("80"))
			editNotificationThreshold(79);
		else
			editNotificationThreshold(80);
	}

	public void testProjectComments(){
		setEditProjectComments("test test test");
	}

	/**Page Interactions**/

	public void setEditProjectComments(String comments){
		txtEditProjectComments.syncEnabled(3);
		txtEditProjectComments.clear();
		txtEditProjectComments.set(comments);
	}

	public String getChangeLogNoteText(){
		return elmMostRecentNoteText.getText();
	}

	public void clickChangeLogTab(){
		lnkChangeLogTab.syncEnabled(3);
		lnkChangeLogTab.click();
	}

	public void editNotificationThreshold(int threshold){
		txtNotificationThreshold.syncEnabled(3);
		txtNotificationThreshold.clear();
		txtNotificationThreshold.set(String.valueOf(threshold));
	}

	public void clickUpdateProject(){
		elmUpdateProject.click();
	}
}
