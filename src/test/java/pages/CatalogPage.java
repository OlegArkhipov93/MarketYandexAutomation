package pages;

import helpers.Log;
import helpers.StableElementSearch;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CatalogPage implements StableElementSearch {

    WebDriver driver;

    public CatalogPage(WebDriver driver) {
        this.driver = driver;
        pageLoading();
    }

    /* **************************************
     *************** XPATHs *****************
     ***************************************/

    // filter section
    public final String FROM_PRICE_INPUT = "//input[@id='glpricefrom']";
    public final String TO_PRICE_INPUT = "//input[@id='glpriceto']";
    public final String MANUFACTURER_SECTION = "//fieldset[./legend[text()='Производитель']]";
    public final String MANUFACTURER_SHOW_MORE_BUTTON = MANUFACTURER_SECTION + "//button";
    public final String MANUFACTURER_SEARCH_INPUT = MANUFACTURER_SECTION + "//input[@type='text']";
    public final String MANUFACTURER_SEARCH_CROSS_BUTTON = MANUFACTURER_SECTION + "//label[contains(@for,'suggester')]";
    public final String MANUFACTURER_CHECKBOX = MANUFACTURER_SECTION + "//input[@type='checkbox']";
    private final String SPECIFIC_MANUFACTURER_CHECKBOX = MANUFACTURER_SECTION + "//input[@type='checkbox' and contains(@name,'%s')]";
    public final String MANUFACTURER_CHECKBOX_LABEL = MANUFACTURER_CHECKBOX + "//parent::label";
    public final String SHOW_ITEMS = "";

    // item section
    public final String CATALOG_ITEM = "//article";
    public final String CATALOG_ITEM_NAME = CATALOG_ITEM + "//h3";
    public final String CATALOG_ITEM_PRICE = CATALOG_ITEM + "//div[contains(@data-zone-name, 'price')]//a";//div[contains(@data-zone-name, 'price')]//a[contains(@href,'product')]

    /* **************************************
     ************* Page Methods *************
     ***************************************/

    /**
     * Verify 'Products' page is loaded correctly
     *
     * @throws ExceptionInInitializerError
     */
    private boolean pageLoading() throws ExceptionInInitializerError {
        try {
            if (isLocatorsVisible(FROM_PRICE_INPUT, TO_PRICE_INPUT, CATALOG_ITEM)
                    && driver.getCurrentUrl().contains("catalog")) {
                return true;
            } else
                throw new ExceptionInInitializerError();
        } catch (ExceptionInInitializerError ex) {
            throw new ExceptionInInitializerError("Catalog page was not loaded");
        }
    }

    /**
     * Send Keys to the search input and wait for the results to be appear
     *
     * @param var
     */
    public void sendKeysToFilterInput(String var) {
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(MANUFACTURER_SEARCH_INPUT)));
        if (!searchElementByXpath(MANUFACTURER_SEARCH_INPUT).getAttribute("value").isEmpty()) {
            searchElementByXpath(MANUFACTURER_SEARCH_CROSS_BUTTON).click();
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(MANUFACTURER_CHECKBOX), 2));
        }
        searchElementByXpath(MANUFACTURER_SEARCH_INPUT).sendKeys(var);
        wait.until(ExpectedConditions.numberOfElementsToBeLessThan(By.xpath(MANUFACTURER_CHECKBOX), 2));
    }

    /**
     * Set manufacturer filters
     *
     * @param filters
     */
    public void setManufacturerFilter(String... filters) {
        searchElementByXpath(MANUFACTURER_SHOW_MORE_BUTTON).click();
        for (String filter : filters) {
            sendKeysToFilterInput(filter);
            searchElementByXpath(MANUFACTURER_CHECKBOX_LABEL).click();
        }
    }

    /**
     * Check the checkbox is selected
     *
     * @param filter
     */
    public boolean isChecked(String filter) {
        String xpath = String.format(SPECIFIC_MANUFACTURER_CHECKBOX, filter);
        sendKeysToFilterInput(filter);
        return driver.findElement(By.xpath(xpath)).isSelected();
    }

    /**
     * Get text from WebElements list
     *
     * @param xpath
     * @return
     * @throws Exception
     */
    public List<String> getAllText(String xpath) {
        try {
            return getWebElementsTextList(searchElementsXpath(xpath), true);
        } catch (StaleElementReferenceException ex) {
            Log.debug("Method 'getAllText' was interrupted by 'StaleElementReferenceException'. Trying to call the method again");
            try {
                Thread.sleep(500);
            } catch (InterruptedException ie) {
            }
            return getWebElementsTextList(searchElementsXpath(xpath), true);
        }
    }

    @Override
    public WebDriver setDriver() {
        return this.driver;
    }
}
