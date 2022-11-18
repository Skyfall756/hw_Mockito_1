import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;

import java.util.stream.Stream;

public class GeoServiceTest {
    GeoServiceImpl gs;

    @BeforeEach
    public void newGeoService() {
        gs = new GeoServiceImpl();
    }

    @AfterEach
    public void deleteGeoService() {
        gs = null;
    }

    @ParameterizedTest
    @MethodSource("source")
    public void testWithStringParameter(String argument, Location exp) {
        Location result = gs.byIp(argument);
        Assertions.assertEquals(exp, result);

    }

    public static Stream<Arguments> source() {
        return Stream.of(Arguments.of(GeoServiceImpl.LOCALHOST,
                        new Location(null, null, null, 0)),
                Arguments.of(GeoServiceImpl.MOSCOW_IP,
                        new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of(GeoServiceImpl.NEW_YORK_IP,
                        new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of("172.",
                        new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.",
                        new Location("New York", Country.USA, null, 0)),
                Arguments.of("989", null));
    }

    @Test
    public void byCoordinatesTest() {
        Assertions.assertThrows(RuntimeException.class, () -> gs.byCoordinates(28.123, 12.24));
    }
}

