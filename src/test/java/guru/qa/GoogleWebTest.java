package guru.qa;

import com.codeborne.selenide.CollectionCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

public class GoogleWebTest {

    @BeforeEach
    void setUp() {
        open("https://google.com");
    }

//    static Stream<Arguments> googleSearchTest() {
//        return Stream.of(
//                Arguments.of("selenide", "https://selenide.org", 1),
//                Arguments.of("junit 5", "https://junit.org", 1)
//        );
//    }

    @CsvSource(value = {
            "selenide, https://selenide.org, 1",
            "junit 5, https://junit.org, 1"
    })
    @ParameterizedTest(name = "Проверка наличия урла {1}" +
            "в результатах выдачи гугла по запросу {0}")
    @Tags({@Tag("BLOCKER"), @Tag("FEATURE")})
    void googleSearchTest(String searchQuery, String expectedUrl, int resultCount) {
        $("[name=q]").setValue(searchQuery).pressEnter();
        $("[id=search]").shouldHave(text(expectedUrl));
        $$("[id=search]").should(CollectionCondition.size(resultCount));
    }


    @DisplayName("Проверка попапа загрузки фото")
    @Test
    @Tag("BLOCKER")
    void googlePhotoPopupTest() {
        $("img[alt='Camera search']").click();
        $(byText("Search any image with Google Lens")).shouldBe(visible);
    }
}
