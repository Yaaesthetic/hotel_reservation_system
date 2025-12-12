package ma.demo;

import java.time.LocalDate;

public class Booking {

    private final long bookingId;

    private final User userSnapshot;
    private final Room roomSnapshot;

    private final LocalDate checkIn;
    private final LocalDate checkOut;

    public Booking(long bookingId,
                   User userSnapshot,
                   Room roomSnapshot,
                   LocalDate checkIn,
                   LocalDate checkOut) {

        this.bookingId = bookingId;
        this.userSnapshot = userSnapshot;
        this.roomSnapshot = roomSnapshot;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public int getRoomNumber() {
        return roomSnapshot.getRoomNumber();
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", userSnapshot=" + userSnapshot +
                ", roomSnapshot=" + roomSnapshot +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                '}';
    }
}