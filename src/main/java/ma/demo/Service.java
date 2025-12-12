package ma.demo;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

public class Service {

    private final ArrayList<Room> rooms = new ArrayList<>();
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<Booking> bookings = new ArrayList<>();

    private long nextBookingId = 1;

    public void setRoom(int roomNumber, RoomType roomType, int pricePerNight) {
        if (roomNumber <= 0 || pricePerNight <= 0 || roomType == null) {
            throw new InvalidInputException("Invalid room input.");
        }

        Room room = findRoom(roomNumber);
        if (room == null) {
            rooms.add(new Room(roomNumber, roomType, pricePerNight));
        } else {
            room.setRoomType(roomType);
            room.setPricePerNight(pricePerNight);
        }
    }

    public void setUser(int userId, int balance) {
        if (userId <= 0 || balance < 0) {
            throw new InvalidInputException("Invalid user input.");
        }

        User user = findUser(userId);
        if (user == null) {
            users.add(new User(userId, balance));
        } else {
            user.setBalance(balance);
        }
    }

    public void bookRoom(int userId, int roomNumber, Date checkIn, Date checkOut) {

        User user = findUser(userId);
        if (user == null) throw new NotFoundException("User not found.");

        Room room = findRoom(roomNumber);
        if (room == null) throw new NotFoundException("Room not found.");

        LocalDate in = toLocalDate(checkIn);
        LocalDate out = toLocalDate(checkOut);

        if (!in.isBefore(out)) {
            throw new InvalidInputException("Invalid date range.");
        }

        if (!isRoomAvailable(roomNumber, in, out)) {
            throw new RoomUnavailableException("Room unavailable.");
        }

        int nights = (int) ChronoUnit.DAYS.between(in, out);
        int totalCost = nights * room.getPricePerNight();

        if (user.getBalance() < totalCost) {
            throw new InsufficientBalanceException("Insufficient balance.");
        }

        // Update live user
        user.setBalance(user.getBalance() - totalCost);

        // Deep-copy snapshot after update
        User userSnapshot = user.clone();
        Room roomSnapshot = room.clone();

        bookings.add(new Booking(
                nextBookingId++,
                userSnapshot,
                roomSnapshot,
                in,
                out
        ));
    }

    public void printAll() {
        System.out.println("\n=== BOOKINGS ===");
        for (int i = bookings.size() - 1; i >= 0; i--) {
            System.out.println(bookings.get(i));
        }
        System.out.println("\n=== ROOMS ===");
        for (int i = rooms.size() - 1; i >= 0; i--) {
            System.out.println(rooms.get(i));
        }

    }

    public void printAllUsers() {
        System.out.println("\n=== USERS ===");
        for (int i = users.size() - 1; i >= 0; i--) {
            System.out.println(users.get(i));
        }
    }

    private Room findRoom(int roomNumber) {
        for (Room r : rooms)
            if (r.getRoomNumber() == roomNumber) return r;
        return null;
    }

    private User findUser(int userId) {
        for (User u : users)
            if (u.getUserId() == userId) return u;
        return null;
    }

    private boolean isRoomAvailable(int roomNumber, LocalDate in, LocalDate out) {
        for (Booking b : bookings) {
            if (b.getRoomNumber() == roomNumber) {
                boolean overlap = in.isBefore(b.getCheckOut()) && out.isAfter(b.getCheckIn());
                if (overlap) return false;
            }
        }
        return true;
    }

    private LocalDate toLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
