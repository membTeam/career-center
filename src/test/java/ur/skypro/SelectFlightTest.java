package ur.skypro;

import org.junit.jupiter.api.Test;
import ru.skypro.SelectFlight;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тестовый модуль выборки рейса на заданную дату и не более 2 часов в ожидании
 */
public class SelectFlightTest {

    @Test
    public void getFlightTest() {
        var res = SelectFlight.getFlight();

        assertEquals(2, res.size());
    }
}
