package ma.demo;

public class Room implements Cloneable {

    private final int roomNumber;
    private RoomType roomType;
    private int pricePerNight;

    public Room(int roomNumber, RoomType roomType, int pricePerNight) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public int getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(int pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    @Override
    public Room clone() {
        try {
            // RoomType is enum (immutable) → safe
            return (Room) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Room cloning failed", e);
        }
    }

    @Override
    public String toString() {
        return "Room{roomNumber=" + roomNumber +
                ", roomType=" + roomType +
                ", pricePerNight=" + pricePerNight + "}";
    }
}