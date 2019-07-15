package lib.ui.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.ui.android.AndroidBookmarkListPageObject;
import lib.ui.ios.IOSBookmarkListPageObject;
import lib.ui.pageObjects.BookmarkListPageObject;

public class BookmarkListPageObjectFactory {

    public static BookmarkListPageObject get(AppiumDriver driver){

        if(Platform.getInstance().isAndroid()) {
            return new AndroidBookmarkListPageObject(driver);
        } else {
            return new IOSBookmarkListPageObject(driver);
        }
    }
}