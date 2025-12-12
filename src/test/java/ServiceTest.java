import ma.demo.*;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTest {

    @Test
    void test_givenScenario() {
        Service service = new Service();

        // Create 3 rooms
        service.setRoom(1, RoomType.STANDARD_SUITE, 1000);
        service.setRoom(2, RoomType.JUNIOR_SUITE, 2000);
        service.setRoom(3, RoomType.MASTER_SUITE, 3000);

        // Create 2 users
        service.setUser(1, 5000);
        service.setUser(2, 10000);

        // User 1 tries booking Room 2 from 30/06/2026 to 07/07/2026 (7 nights) -> fails (insufficient balance)
        assertThrows(InsufficientBalanceException.class, () ->
                service.bookRoom(1, 2, d(2026, 6, 30), d(2026, 7, 7))
        );

        // User 1 tries booking Room 2 from 07/07/2026 to 30/06/2026 -> fails (invalid dates)
        assertThrows(InvalidInputException.class, () ->
                service.bookRoom(1, 2, d(2026, 7, 7), d(2026, 6, 30))
        );

        // User 1 tries booking Room 1 from 07/07/2026 to 08/07/2026 (1 night) -> succeeds
        assertDoesNotThrow(() ->
                service.bookRoom(1, 1, d(2026, 7, 7), d(2026, 7, 8))
        );

        // User 2 tries booking Room 1 from 07/07/2026 to 09/07/2026 (2 nights) -> fails (room unavailable)
        assertThrows(RoomUnavailableException.class, () ->
                service.bookRoom(2, 1, d(2026, 7, 7), d(2026, 7, 9))
        );

        // User 2 tries booking Room 3 from 07/07/2026 to 08/07/2026 (1 night) -> succeeds
        assertDoesNotThrow(() ->
                service.bookRoom(2, 3, d(2026, 7, 7), d(2026, 7, 8))
        );

        // setRoom(1, suite, 10000) -> updates live room only; bookings should remain with snapshot values
        assertDoesNotThrow(() ->
                service.setRoom(1, RoomType.MASTER_SUITE, 10000)
        );

        // End result prints (for your screenshots)
        assertDoesNotThrow(service::printAll);
        assertDoesNotThrow(service::printAllUsers);
    }

    private static Date d(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1); // Calendar months are 0-based
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, 12);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }
}

