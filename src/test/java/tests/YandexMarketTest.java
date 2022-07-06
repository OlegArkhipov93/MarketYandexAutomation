package tests;

import helpers.Log;
import helpers.StaticDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CatalogPage;
import pages.MainPage;
import runner.Runner;

import java.util.List;

public class YandexMarketTest extends Runner {

    @Test(description = "C1111 Returned products match the specified search parameters",
            dataProvider = "entryFields", dataProviderClass = StaticDataProvider.class, groups = {"yandexGroup"})
    public void testReturnedProductsMatchTheSpecifiedSearchParameters(
            String url, String mainOption, String subOption, String fromPrice, String toPrice, String... filters) throws Exception {

        Log.step("1. Launch the full screen browser");
        driver.manage().window().maximize();

        Log.step("2. Go to https://market.yandex.ru/");
        driver.get(url);
        MainPage yandexMarketPage = new MainPage(driver);

        Log.step("3. Go to 'Computers' and select 'Laptops'");
        yandexMarketPage.clickCatalogDropDown();

        // verify the dropdown is expands
        Assert.assertTrue(
                yandexMarketPage.searchElementByXpath(yandexMarketPage.CATALOG_DROPDOWN_CONTENT).isDisplayed(),
                "Catalog dropdown was not expands");

        yandexMarketPage
                .hoverOverMainOption(mainOption)
                .clickCatalogSubOption(subOption);

        // verify directed to the catalog page
        CatalogPage catalogPage = new CatalogPage(driver);

        Log.step("4. Set the search parameters");
        Log.action("Set manufacturer: Lenovo");
        catalogPage.setManufacturerFilter(filters);

        // verify the checkboxes are applied
        for (String filter : filters) {
            catalogPage.searchElementByXpath(catalogPage.MANUFACTURER_SEARCH_CROSS_BUTTON).click();
            Assert.assertTrue(
                    catalogPage.isChecked(filter),
                    "Checkbox for the " + filter + " filter are not selected");
        }

        Log.action("Set price: from 25000 rubles to 30000 rubles");
        catalogPage.searchElementByXpath(catalogPage.FROM_PRICE_INPUT).sendKeys(fromPrice);
        catalogPage.searchElementByXpath(catalogPage.TO_PRICE_INPUT).sendKeys(toPrice);

        Log.step("5. Click the Show button");
        try {
            catalogPage.searchElementByXpath(catalogPage.SHOW_ITEMS).click();
        } catch (Exception ex) {
            Log.error("Show button is not appeared on the screen");
//            throw new AssertionError("Show button is absent");
        }

        Log.step("6. On the first page with the results make sure the found products meet the specified search parameters");
        // verify the all returned results contain applied filter
        List<String> names = catalogPage.getAllText(catalogPage.CATALOG_ITEM_NAME);
        long returnedResultsCount = 0;
        for (String filter : filters) {
            returnedResultsCount = returnedResultsCount + names.stream().filter(n -> n.contains(filter)).count();
        }
        Assert.assertEquals(
                names.size(),
                returnedResultsCount,
                "Filters are not applied");
//        catalogPage.getAllText(catalogPage.CATALOG_ITEM_NAME).stream().allMatch(n -> n.contains(filter));
        Log.step("7. Close the browser");
    }
}
