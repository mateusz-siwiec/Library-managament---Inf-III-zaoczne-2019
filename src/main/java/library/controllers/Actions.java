package library.controllers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import library.entities.User;

import java.util.List;

public class Actions {
    
    public Session session;
    public SessionFactory sessionFactory;
    
    public void initDatabase() {

        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");

        configuration.addAnnotatedClass(User.class);
//        configuration.addAnnotatedClass(Task.class);

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
   
}
