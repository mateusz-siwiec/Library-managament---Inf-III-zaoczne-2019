package library.entities;

import javax.persistence.*;



@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_Id")
    private User user;
    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @Column(name = "dateFrom")
    private String dateFrom;
    @Column(name = "dateTo")
    private String dateTo;

    /**
     * Class constructor
     * @param user
     * @param book
     * @param dateFrom
     * @param dateTo
     */
    public Orders(User user, Book book, String dateFrom, String dateTo) {
        this.user = user;
        this.book = book;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Orders() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }
}
