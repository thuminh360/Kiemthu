package dataObjects;

public class User {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String pid;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String pid) {
        this.username = username;
        this.password = password;
        this.pid = pid;
    }

    public User(String username, String password, String email, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPid() {
        return pid;
    }
}