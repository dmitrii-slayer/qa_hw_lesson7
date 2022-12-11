package guru.qa;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class MegafonTests {
    @BeforeAll
    static void configure() {
        Configuration.browserSize = "1920x1080";
    }

    @BeforeEach
    void setUp() {
        open("https://www.megafon.ru/");
    }


    static Stream<Arguments> megafonDropdownMenuTest() {
        return Stream.of(
                Arguments.of("Тарифы и услуги", List.of("Мобильная связь", "Комплекты", "Услуги и опции", "Роуминг и путешествия")),
                Arguments.of("Оплата и финансы", List.of("Баланс", "Платежи и переводы")),
                // в следующей строке добавлен лишний заголовок "Баланс" для проверки корректности теста
                Arguments.of("Акции", List.of("Скидки и выгоды", "Партнёрские проекты", "Баланс")),
                Arguments.of("Поддержка", List.of("Решение проблем", "Контактная информация"))
        );
    }

    @MethodSource
    @ParameterizedTest(name = "Проверка наличия заголовков из списка {1} на сайте Мегафона в выпадающем меню {0}")
    void megafonDropdownMenuTest(String dropdownMenu, List<String> headings) {
//        $$(".ch-platform-menu-item__inner").find(text(dropdownMenu)).hover();
        $$(".ch-platform-menu__container li").find(text(dropdownMenu)).hover();
        $$(".ch-platform-menu-drop-sections h3").filter(visible)
                .shouldHave(CollectionCondition.texts(headings));
    }


    @ValueSource(strings = {
            "Алтайский край",
            "Москва и область",
            "Ханты-Мансийский АО"
    })
    @ParameterizedTest(name = "Проверка возможности выбора региона {0}")
    void megafonSelectRegionTest(String region) {
        // в углу страницы нажимаем на регион
        $(".ch-region__trigger").click();
        // в списке регионов находим нужный нам регион и кликаем на него
        $(".ch-region-popup__regions").$(byText(region)).scrollIntoView(true).click();
        // проверяем что регион сменился на выбранный нами
        $(".ch-header__section_type_region").shouldHave(text(region));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
