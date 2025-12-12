package ma.demo;

public class User implements Cloneable {

    private final int userId;
    private int balance;

    public User(int userId, int balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public int getUserId() {
        return userId;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public User clone() {
        try {
            // Only primitives → shallow clone is enough (deep copy)
            return (User) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("User cloning failed", e);
        }
    }

    @Override
    public String toString() {
        return "User{userId=" + userId + ", balance=" + balance + "}";
    }
}

