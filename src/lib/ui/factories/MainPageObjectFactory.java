package lib.ui.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.ui.android.AndroidMainPageObject;
import lib.ui.ios.IOSMainPageObject;
import lib.ui.pageObjects.MainPageObject;

public class MainPageObjectFactory {

    public static MainPageObject get(AppiumDriver driver){

        if(Platform.getInstance().isAndroid()) {
            return new AndroidMainPageObject(driver);
        } else {
            return new IOSMainPageObject(driver);
        }
    }
}