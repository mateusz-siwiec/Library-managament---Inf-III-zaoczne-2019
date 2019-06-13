package library.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private int id;

    @Column(name = "title")
    @NotNull
    @Size(min = 2 , max = 40)
    private String title;

    @Column(name = "author")
    @NotNull
    @Size(min = 2 , max = 40)
    private String author;

    @NotNull
    @Column(name = "yearOfPublish")
    private int yearOfPublish;

    @OneToOne(mappedBy = "book")
    private Orders orders;

    /**
     * Class constructor
     * @param title
     * @param author
     * @param yearOfPublish
     */
    public Book(String title, String author, int yearOfPublish) {
        this.title = title;
        this.author = author;
        this.yearOfPublish = yearOfPublish;
    }

    public Book(int id, String title, String author, int yearOfPublish) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.yearOfPublish = yearOfPublish;
    }

    public Book() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearOfPublish() {
        return yearOfPublish;
    }

    public void setYearOfPublish(int yearOfPublish) {
        this.yearOfPublish = yearOfPublish;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return title;
    }
}
