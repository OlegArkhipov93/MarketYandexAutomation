package pages;

import helpers.StableElementSearch;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;


public class MainPage implements StableElementSearch {

    WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        pageLoading();
    }


    /* **************************************
     *************** XPATHs *****************
     ***************************************/

    public final String SIGN_IN_BUTTON = "//div//a[contains(@href,'passport')]/span";
    public final String CATALOG_DROPDOWN = "//div[contains(@data-apiary-widget-name,'HeaderCatalogEntrypoint')]";
    public final String CATALOG_DROPDOWN_CONTENT = "//div[contains(@data-apiary-widget-name, 'HeaderCatalog') and not(contains(@data-apiary-widget-name,'point'))]";
    //    By SPECIFIC_CATALOG_OPTION = By.xpath("//li//a/span[contains(text(),'%s')]");
    public final String SPECIFIC_CATALOG_MAIN_OPTION = CATALOG_DROPDOWN_CONTENT + "//li//a/span[contains(text(),'%s')]";
    public final String SPECIFIC_CATALOG_SUB_OPTION = CATALOG_DROPDOWN_CONTENT + "//a[text()='%s']";
    public final String CATALOG_BUTTON = "//div//a[contains(@href,'catalog')]";
    public final String SEARCH_INPUT = "//input[@id='header-search']";


    /* **************************************
     ************* Page Methods *************
     ***************************************/
    /**
     * Verify 'Yandex market' page is loaded correctly
     *
     * @throws ExceptionInInitializerError
     */
    private boolean pageLoading() throws ExceptionInInitializerError {
        try {
            if (isLocatorsVisible(SIGN_IN_BUTTON, CATALOG_BUTTON)
                    && driver.getCurrentUrl().contains("yandex")) {
                return true;
            } else
                throw new ExceptionInInitializerError();
        } catch (ExceptionInInitializerError ex) {
            throw new ExceptionInInitializerError("Yandex market page was not loaded");
        }
    }

    @Override
    public WebDriver setDriver() {
        return this.driver;
    }

    public MainPage clickCatalogDropDown() {
        searchElementByXpath(CATALOG_DROPDOWN).click();
        return new MainPage(driver);
    }

    public MainPage hoverOverMainOption(String mainLink) throws Exception {
        (new Actions(this.driver)).moveToElement(this.searchElementByXpath(String.format(SPECIFIC_CATALOG_MAIN_OPTION, mainLink))).perform();
        return new MainPage(driver);
    }

    public MainPage clickCatalogSubOption(String option) throws Exception {
        searchElementByXpath(String.format(SPECIFIC_CATALOG_SUB_OPTION, option)).click();
        return new MainPage(driver);
    }
}
