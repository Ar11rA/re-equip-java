package commons;

public class User {
    private String firstName;
    private String lastName;
    private int level;

    public User(String firstName, String lastName, int level) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.level = level;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
