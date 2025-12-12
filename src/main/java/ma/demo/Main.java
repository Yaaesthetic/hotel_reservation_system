package ma.demo;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.Calendar;
import java.util.Date;

public class Main {

    public static void main(String[] args) {

        Service service = new Service();

        service.setUser(1, 5000);
        service.setRoom(101, RoomType.STANDARD_SUITE, 400);

        service.bookRoom(1, 101, d(2025,12,10), d(2025,12,13));

        service.printAll();

        // test change room price
        service.setRoom(101, RoomType.STANDARD_SUITE, 900);

        service.printAll();
        service.printAllUsers();
    }

    private static Date d(int y, int m, int d) {
        Calendar c = Calendar.getInstance();
        c.set(y, m - 1, d, 12, 0);
        return c.getTime();
    }
}