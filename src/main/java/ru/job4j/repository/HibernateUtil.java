package ru.job4j.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.function.Function;

public class HibernateUtil {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    private SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public <T> void create(T model) {
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.save(model);
            tx.commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public <T> void delete(T model) {
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.delete(model);
            tx.commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public <T> void update(T model) {
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.update(model);
            tx.commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
