package ru.job4j.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.Task;

import java.util.List;
import java.util.function.Function;

public class DBStore implements Store {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    private SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    private DBStore() {
    }

    private final static class Lazy {
        private final static Store INST = new DBStore();
    }

    public static Store getInst() {
        return Lazy.INST;
    }

    @Override
    public List getAllTasks() {
        List result = this.tx(session -> session.createQuery("from ru.job4j.model.Task").list());
        return result;
    }

    @Override
    public void save(Task task) {
        this.tx(session -> session.save(task));
    }

    private <T> T tx(final Function<Session, T> command) {
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
