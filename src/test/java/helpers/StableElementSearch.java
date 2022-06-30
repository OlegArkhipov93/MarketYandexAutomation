package helpers;

import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;


public interface StableElementSearch {

    WebDriver setDriver();

    default WebElement searchElementByCss(By locator) {
        return explicitSearch(visibilityOfElementLocated(locator));
    }

    default WebElement searchElementByCss(String locator) {
        return explicitSearch(visibilityOfElementLocated(By.cssSelector(locator)));
    }

    default WebElement searchElementByXpath(By xPath) {
        return explicitSearch(visibilityOfElementLocated(xPath));
    }

    default WebElement searchElementByXpath(String xPath) {
        return explicitSearch(visibilityOfElementLocated(By.xpath(xPath)));
    }

    default List<WebElement> searchElementsByCss(By locator) {
        searchElementByCss(locator);
        return setDriver().findElements(locator);
    }

    default List<WebElement> searchElementsByXpath(By locator) {
        searchElementByCss(locator);
        return setDriver().findElements(locator);
    }

    default List<WebElement> searchElementsByCss(String locator) {
        searchElementByCss(locator);
        return setDriver().findElements(By.cssSelector(locator));
    }

    default List<WebElement> searchElementsXpath(String locator) {
        searchElementByXpath(locator);
        return setDriver().findElements(By.xpath(locator));
    }

    default <V> V explicitSearch(Function<? super WebDriver, V> condition) {
        setDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        V element = (new WebDriverWait(setDriver(), 20)).until(condition);
        setDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return element;
    }

    default boolean isLocatorsVisible(String... xPathList) {
        for (String xpath : xPathList) {
            try {
                boolean isVisible = searchElementByXpath(xpath).isDisplayed();
                if (!isVisible) {
                    Log.warn("Expected condition failed: page element [" + xpath + "] wasn't loaded");
                    return false;
                }
            } catch (Exception ex) {
                return false;
            }
        }
        return true;
    }

    default List<String> getWebElementsTextList(List<WebElement> listOfWebElements, boolean trim) {
        List<String> textList = new ArrayList();
        Iterator var4 = listOfWebElements.iterator();

        while(var4.hasNext()) {
            WebElement we = (WebElement)var4.next();
            if (trim) {
                textList.add(we.getAttribute("textContent").trim());
            } else {
                textList.add(we.getAttribute("textContent"));
            }
        }

        return textList;
    }
}
