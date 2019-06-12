package library.controllers;

import library.entities.Book;
import library.entities.Orders;
import library.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

public class Actions {

    public Session session;
    public SessionFactory sessionFactory;

    public void initDatabase() {

        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");

        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Orders.class);
        configuration.addAnnotatedClass(Book.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        this.sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        this.session = sessionFactory.openSession();
    }

    public Session getSession() {
        return this.session;
    }

    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    public void closeDbConnection() {
        this.session.close();
        this.sessionFactory.close();
    }

    public List<User> getAllUsers() {
//        Query query = session.createQuery("from User");
//        List<User> users = query.getResultList();

        List<User> users = session.createQuery("from User").getResultList();

        return users;
    }

    public List<Book> getAllBooks() {
        List<Book> books = session.createQuery("from Book").getResultList();
        return books;
    }

    public List<Integer> getAllBookIds() {
        List<Orders> orders = (List<Orders>) session.createQuery("from Orders").getResultList();
        return orders.stream().map(order -> order.getBook().getId())
                .collect(Collectors.toList());
    }

    public List<Orders> getAllOrders() {
        List<Orders> orders = session.createQuery("from Orders").getResultList();
        return orders;
    }

    public void saveToDatabase(Object object) {
        Transaction transaction = session.beginTransaction();
        session.save(object);
        transaction.commit();
    }

    public void deleteFromDatabase(Object object) {
        Transaction transaction = session.beginTransaction();
        session.delete(object);
        transaction.commit();
    }

    public void updateObject(Object object) {
        Transaction transaction = session.beginTransaction();
        session.merge(object);
        transaction.commit();
    }

    public String get_SHA_512_SecurePassword(String passwordToHash, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

}
