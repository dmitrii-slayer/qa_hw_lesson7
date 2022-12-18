package guru.qa;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class BeelineTests {
    @BeforeAll
    static void configure() {
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://www.beeline.ru/";
        Configuration.holdBrowserOpen = true;
    }

    @CsvSource(value = {
            "Apple", "Samsung"
    })
    @ParameterizedTest(name = "Проверка фильтрации телефонов по производителю {0}")
    void beelinePhoneBrandFilterCsvTest(String brand) {
        open("shop/catalog/telefony/smartfony/");
        // выбираем производителя в фильтре
        $(byText(brand)).parent().click();
//        executeJavaScript("$('.flocktory-widget').remove()");
//        $(".filtersWrapper")
        // проверяем что в каждой карточке товара есть имя производителя
        $$("div[data-identity]").shouldHave(CollectionCondition.allMatch(
                "Brand=={0}", (selenideElement -> selenideElement.getText().contains(brand))));
    }

    @CsvFileSource(resources = "/phonebrands.csv")
    @ParameterizedTest(name = "Проверка фильтрации телефонов по производителю {0}")
    void beelinePhoneBrandFilterCsvFileTest(String brand) {
        open("shop/catalog/telefony/smartfony/");
        $(byText(brand)).parent().click();
        $$("div[data-identity]").shouldHave(CollectionCondition.allMatch(
                "Brand=={0}", (selenideElement -> selenideElement.getText().contains(brand))));
    }
}
