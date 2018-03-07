package com.orasi.bluesource;

import com.orasi.utils.TestReporter;
import com.orasi.web.OrasiDriver;
import com.orasi.web.PageLoaded;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.impl.internal.ElementFactory;
import org.openqa.selenium.support.FindBy;

/**
 * Page driver for the Create Notes Form on the project page
 * @author David Grayson
 */
public class ProjectNoteForm {
	private OrasiDriver driver = null;

	/**Page Elements**/
	@FindBy(xpath = "//button[contains(text(),'Add Note')]") private Button btnAddNote;
	@FindBy(xpath = "//div[@class='notes_form']//textarea[@id='note_note_field']") private Textbox txtNoteField;
	@FindBy(xpath = "//div[@class='notes_form']//input[@value='Create Note']") private Element elmCreateNote;
	@FindBy(xpath = "//li/div[@class='note-text']") private Element elmNoteText;

	/**Constructor**/
	public ProjectNoteForm(OrasiDriver driver) {
		this.driver = driver;
		ElementFactory.initElements(driver,this);
	}

	/**Page Interactions**/

	/**
	 * Clicks the add note button
	 * @author David Grayson
	 */
	public void clickAddNote(){
		btnAddNote.syncEnabled(3);
		btnAddNote.click();
	}

	/**
	 * sets the Note text
	 * @author David Grayson
	 */
	public void setNoteText(String strNote){
		txtNoteField.syncEnabled(3);
		txtNoteField.clear();
		txtNoteField.set(strNote);
	}

	/**
	 * clicks the create note element
	 * @author David Grayson
	 */
	public void clickCreateNote(){
		elmCreateNote.click();
	}

	/**
	 * Gets the note text
	 * @author David Grayson
	 */
	public String getNoteText(){
		elmNoteText.syncVisible(3);
		TestReporter.log("Note Text = [" + elmNoteText.getText() + "]");
		return elmNoteText.getText();
	}

	/**
	 * @author David Grayson
	 * @return {@link Boolean} Returns <code>true</code> if the form is loaded, <code>false</code> otherwise.
	 */
	public boolean verifyFormIsLoaded() {
		return PageLoaded.isElementLoaded(this.getClass(),driver,txtNoteField,5) &&
				PageLoaded.isElementLoaded(this.getClass(),driver,elmCreateNote,5) &&
				PageLoaded.isElementLoaded(this.getClass(),driver,btnAddNote,5);
	}
}
