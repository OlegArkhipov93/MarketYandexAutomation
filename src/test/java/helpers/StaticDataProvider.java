package helpers;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.DataProvider;

public class StaticDataProvider {

    String[] manufactures = {"Lenovo"};

//    @BeforeGroups(value = {"SmokeSingUp"})
//    @DataProvider(name = "loginDataProvider")
//    public Object[][] loginDataProvider() {
//        return new Object[][]{{"yandex_login", "yandex_password"}};
//    }

    @BeforeGroups(value = {"yandexGroup"})
    @DataProvider(name = "entryFields")
    public Object[][] entryFields() {
        return new Object[][]
                {{"https://market.yandex.ru/", "Электроника", "Ноутбуки", "25000", "30000", manufactures}};
    }

}
