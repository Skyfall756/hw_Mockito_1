package sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSenderImpl;

import java.util.Map;
import java.util.stream.Stream;

public class MessageSenderTest {


    @ParameterizedTest
    @MethodSource("sourceForSend")
    public void testSend2 (Map <String,String> argument, String exp) {

        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(Mockito.any()))
                .thenReturn(new Location("Moscow", Country.RUSSIA, "Lenina", 15));

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Mockito.any()))
                .thenReturn(exp);

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);

        String res = messageSender.send(argument);
        Assertions.assertEquals(exp, res);

    }
    public static Stream<Arguments> sourceForSend() {
        return Stream.of(Arguments.of(Map.of(MessageSenderImpl.IP_ADDRESS_HEADER, "172.123.12.19"),
                "Добро пожаловать"),
                Arguments.of(Map.of(MessageSenderImpl.IP_ADDRESS_HEADER, "127.0.0.1"),
                        "Welcome"),
                Arguments.of(Map.of(MessageSenderImpl.IP_ADDRESS_HEADER, "96.44.183.149"),
                        "Welcome"),
                Arguments.of(Map.of(MessageSenderImpl.IP_ADDRESS_HEADER, "172.56.11.19"),
                        "Добро пожаловать"));
    }


}

