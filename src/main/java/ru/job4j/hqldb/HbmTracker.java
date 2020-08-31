package ru.job4j.hqldb;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.Query;
import java.util.List;

public class HbmTracker {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    public Item create(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    public void delete(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("delete Item i where i.id = :fId")
                .setParameter("fId", id).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public Item findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item result = null;
        Query query = session.createQuery("from Item i where i.id = :fId")
                .setParameter("fId", id);
        result = (Item) ((org.hibernate.query.Query) query).uniqueResult();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public List<Item> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Item> result = session.createQuery("from Item").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }
}
