package guru.qa;

import com.codeborne.selenide.CollectionCondition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

public class MegafonTests {
    @BeforeAll
    static void setUp() {
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
    @ParameterizedTest(name = "Проверка наличия заголовков из списка {1} на сайте Мегафона в выпадающем списке {0}")
    @Tag("BLOCKER")
    void megafonDropdownMenuTest(String dropdownMenu, List<String> headings) {
//        $$(".ch-platform-menu-item__inner").find(text(dropdownMenu)).hover();
        $$(".ch-platform-menu__container li").find(text(dropdownMenu)).hover();
        $$(".ch-platform-menu-drop-sections h3").filter(visible)
                .shouldHave(CollectionCondition.texts(headings));
    }
}
