package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.pageObjects.MainPageObject;

public class AndroidMainPageObject extends MainPageObject {

    static {
        MY_LIST_BUTTON = "xpath://*[contains(@text, '{TITLE}')]/following-sibling::*[contains(@text, '{SUBTITLE}')]/parent::*";
    }

    public AndroidMainPageObject(AppiumDriver driver){
        super(driver);
    }
}