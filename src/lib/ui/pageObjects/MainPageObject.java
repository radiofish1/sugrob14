package lib.ui.pageObjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import lib.Platform;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.regex.Pattern;

abstract public class MainPageObject {

    protected AppiumDriver driver;

    protected static String
            MY_LIST_BUTTON;


    public MainPageObject(AppiumDriver driver){
        this.driver = driver;
    }

    public void clickElementToTheRightUpperCorner(String locator, String errorMsg){
        WebElement element = this.waitForElementPresent(locator + "/..", errorMsg);
        int right_x = element.getLocation().getX();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;
        int width = element.getSize().getWidth();

        int point_to_click_x = (right_x + width) - 3;
        int point_to_click_y = middle_y;

        TouchAction action = new TouchAction(driver);
        action.tap(point_to_click_x, point_to_click_y).perform();
    }

    public void openBookmarks(){
        this.waitForElementAndClick(MY_LIST_BUTTON, "There is no 'My lists' button on the screen",5);
    }

    public void openMyBookmarksList(String listName){
        this.waitForElementAndClick("xpath://*[contains(@text, '" + listName + "')]", "There is no '" + listName + "' folder on the screen",5);
    }

    private By getLocatorByString(String locator_with_type){
        String[] exploded_locator = locator_with_type.split(Pattern.quote(":"), 2);
        String by_type = exploded_locator[0];
        String locator = exploded_locator[1];

        switch (by_type) {
            case "xpath":
                return By.xpath(locator);
            case "id":
                return  By.id(locator);
            default:
                throw  new IllegalArgumentException("Cannot get type of loactor. Locator: " + locator_with_type);
        }
    }


    public int getAmountOfElements(String locator){
        By by = getLocatorByString(locator);
        List elements = driver.findElements(by);
        return elements.size();
    }

    public void assertElementPresent(String locator, String errorMessage){
        int amountOfElements = getAmountOfElements(locator);
        if (amountOfElements == 0) {
            String defaultMessage = "An element " + locator.toString() + " supposed to be present";
            throw new AssertionError(defaultMessage + " " + errorMessage);

        }
    }

    public WebElement waitForElementPresent(String locator, String errorMessage, long timeoutInSeconds){
        By by = getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    public WebElement waitForElementPresent(String locator , String errorMessage){
        return waitForElementPresent(locator, errorMessage, 5);
    }

    public WebElement waitForElementAndClick(String locator, String errorMessage, long timeoutInSeconds){
        WebElement element = waitForElementPresent(locator, errorMessage);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(String locator, String value, String errorMessage, long timeoutInSeconds){
        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    public  WebElement waitForElementAndClear(String locator, String errorMessage, long timeoutInSeconds){
        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        element.clear();
        return element;
    }

    public boolean waitForElementNotPresent(String locator, String errorMessage, long timeoutInSeconds){
        By by = getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    public List<WebElement> waitForListOfElementsPresentByXPath(String locator, String errorMessage, long timeoutInSeconds){
        By by = getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(by)
        );
    }

    public String waitForElementAndGetText(String locator, String errorMessage, long timeoutInSeconds){
        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        return element.getText();
    }



    public void swipeUp(int timeOfSwipe){
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width/2;
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);
        action
                .press(x, start_y)
                .waitAction(timeOfSwipe)
                .moveTo(x, end_y)
                .release()
                .perform();
    }

    public void swipeUpQuick(){
        swipeUp(200);
    }

    public void swipeUpToFindElement(String locator, String errorMessage, int maxSwipes){

        By by = getLocatorByString(locator);
        int swipesCount = 0;
        while (driver.findElements(by).size() == 0){

            if (swipesCount > maxSwipes){
                waitForElementPresent(locator, "Cannot find element by swiping up. \n" + errorMessage, 0);
                return;
            }
            swipeUpQuick();
            swipesCount++;
        }

    }

    public void swipeElementToLeft(String locator, String errorMessage) {
        WebElement element = waitForElementPresent(
                locator,
                errorMessage
        );
        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int lower_y = element.getLocation().getY();
        int upper_y = lower_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;
        TouchAction action = new TouchAction(driver);
        action.press(right_x, middle_y);
        action.waitAction(300);
        if (Platform.getInstance().isAndroid()) {
            action.moveTo(left_x, middle_y);
        } else {
            int offset_x = (-1 * element.getSize().getWidth());
            action.moveTo(offset_x, 0);
        }
        action.release();
        action.perform();
    }

    public void pressByCoordinates(){
        Dimension size = driver.manage().window().getSize();
        int x = size.width/2;
        int y = (int) (size.height * 0.8);
        TouchAction action = new TouchAction(driver);
        action
                .press(x, y)
                .release()
                .perform();
    }
}
