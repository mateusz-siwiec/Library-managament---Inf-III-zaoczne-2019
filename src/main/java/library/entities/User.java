package library.entities;



import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private int id;
    @NotNull(message = "required")
    @Size(min = 2 , max = 40)
    @Column(name = "login", unique = true)
    private String login;

    @NotNull(message = "required")
    @Size(min = 5 , max = 255)
    @Column(name = "password")
    private String password;
    @NotNull
    @Size(min = 2 , max = 30)
    @Column(name = "name")
    private String name;
    @NotNull
    @Size(min = 2 , max = 30)
    @Column(name = "surname")
    private String surname;
    @NotNull
    @Column(name = "age")
    private int age;
    @NotNull
    @Column(name = "pesel")
    private long pesel;
    @NotNull
    @Column(name = "phoneNumber")
    private long phoneNumber;

    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "user")
    private List<Orders> orders;

    public User() {
    }

    /**
     * Class constructor
     * @param login
     * @param password
     * @param name
     * @param surname
     * @param age
     * @param pesel
     * @param phoneNumber
     * @param role
     */
    public User(String login, String password, String name, String surname, int age, long pesel, long phoneNumber, String role) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.pesel = pesel;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public User(int id, String login, String password, String name, String surname, int age, long pesel, long phoneNumber, String role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.pesel = pesel;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public User(int id, String login, String password, String name, String surname, int age, long pesel, long phoneNumber) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.pesel = pesel;
        this.phoneNumber = phoneNumber;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    /**
     * Getters and setters
     * @return
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getPesel() {
        return pesel;
    }

    public void setPesel(long pesel) {
        this.pesel = pesel;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }
}
