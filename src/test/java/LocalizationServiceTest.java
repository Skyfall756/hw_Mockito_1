import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationServiceImpl;

public class LocalizationServiceTest {

    LocalizationServiceImpl ls;
    @BeforeEach
    public void newLocalizationService() {
        ls = new LocalizationServiceImpl();
    }
    @AfterEach
    public void deleteLocalizationService() {
        ls = null;
    }

    @ParameterizedTest
    @EnumSource(mode = EnumSource.Mode.EXCLUDE, names = {"RUSSIA"})
    public void localeTastOtherCountry(Country country) {
        Assertions.assertEquals("Welcome", ls.locale(country));

    }
    @Test
    public void localTestRussia() {
        Assertions.assertEquals("Добро пожаловать", ls.locale(Country.RUSSIA));

    }
}
