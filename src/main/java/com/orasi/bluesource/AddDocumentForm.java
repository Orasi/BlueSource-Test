package com.orasi.bluesource;

import com.orasi.web.OrasiDriver;
import com.orasi.web.PageLoaded;
import com.orasi.web.webelements.Checkbox;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.impl.internal.ElementFactory;
import org.openqa.selenium.support.FindBy;

import java.text.DecimalFormat;
import java.util.List;

public class AddDocumentForm {
    private OrasiDriver driver = null;

    /**Page Elements**/
    @FindBy(xpath = "//form[@id='new_document']") private Element elmAddDocumentForm;
    @FindBy(xpath = "//input[@name='document[file]']") private Element elmDocumentFile;
    @FindBy(xpath = "//span[@class='select2-results']/ul/li") private List<Element> elmDocumentTypeOptions;
    @FindBy(xpath = "//input[@id='document_budget']") private Textbox txtTotalBudget;
    @FindBy(xpath = "//input[@id='document_hours']") private Textbox txtTotalHours;
    @FindBy(xpath = "//input[@id='document_signed']") private Checkbox chkSigned;
    @FindBy(xpath = "//input[@value='Create Document']") private Element elmCreateDocument;
    @FindBy(xpath = "//form[@id='new_document']//span[@role='combobox']") private Element elmDocumentType;
    @FindBy(xpath = "//input[@id='document_name']") private Textbox txtDocumentName;

    /**
     * Constructor
     **/
    public AddDocumentForm(OrasiDriver driver) {
        this.driver = driver;
        ElementFactory.initElements(driver, this);
    }

    /**Page Interactions**/

    public boolean verifyFormLoaded(){
        return PageLoaded.isElementLoaded(this.getClass(),driver,elmAddDocumentForm,5);
    }

    /**
     * @author David Grayson
     * @param filePath {@link String} the complete path for the file to be uploaded
     */
    public void setFile(String filePath){
        if (canInteract(elmDocumentFile))
            elmDocumentFile.sendKeys(filePath);
    }

    public String getDocumentName(){
        return txtDocumentName.getText();
    }

    public void clickCreateDocument(){
        if (canInteract(elmCreateDocument))
            elmCreateDocument.click();
    }

    public void selectDocumentType(String type){
        if (canInteract(elmDocumentType)){
            elmDocumentType.click();
            for (Element elm:elmDocumentTypeOptions){
                if (elm.getText().equals(type)){
                    elm.click();
                    break;
                }
            }
        }
    }

    public void setTotalHours(double hours){
        if (canInteract(txtTotalHours)){
            txtTotalHours.clear();
            txtTotalHours.sendKeys(new DecimalFormat("####.00").format(hours));
        }
    }

    public void setTotalBudget(int budget){
        if (canInteract(txtTotalBudget)){
            txtTotalBudget.clear();
            txtTotalBudget.sendKeys(String.valueOf(budget));
        }
    }

    public void clickSignedCheckbox(){
        if (canInteract(chkSigned))
            chkSigned.toggle();
    }

    /**
     * This method provides standard checks that an element can be interacted with
     * @author David Grayson
     * @param elm {@link Element} Element to check
     * @return {@link Boolean} Returns <code>true</code> if the element is enabled and visible, <code>false</code> otherwise
     */
    private boolean canInteract(Element elm){
        return elm.syncEnabled(5) && elm.syncVisible(5);
    }
}
