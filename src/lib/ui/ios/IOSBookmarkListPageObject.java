package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.pageObjects.BookmarkListPageObject;

public class IOSBookmarkListPageObject extends BookmarkListPageObject {

    static {
        ARTICLE_TITLE = "xpath://XCUIElementTypeLink[contains(@name, '{ARTICLE_TITLE}')]";
    }

    public IOSBookmarkListPageObject(AppiumDriver driver){
        super(driver);
    }
}