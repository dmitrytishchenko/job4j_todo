package ru.job4j.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.Task;
import ru.job4j.model.User;

import java.util.List;
import java.util.function.Function;

public class DBStore implements Store {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    private SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    private DBStore() {
        checkAdmin();
    }

    private final static class Lazy {
        private final static Store INST = new DBStore();
    }

    public static Store getInst() {
        return Lazy.INST;
    }

    private void checkAdmin() {
        Task adminTask = new Task();
        saveTask(adminTask);
        User user = new User();
        user.setName("admin");
        user.setPassword("1");
        user.setTask(adminTask);
        this.tx(session -> session.save(user));
    }

    @Override
    public void addUser(String name, String password, Task task) {
        User user = new User().of(name, password, task);
        this.tx(session -> session.save(user));
    }

    @Override
    public List<User> getAllUsers(Class<User> userClass) {
        List<User> result = this.tx(session -> session.createQuery("from " + userClass.getName(), userClass).list());
        return result;
    }

    @Override
    public User checkNameAndPasswordByUser(String name, String password) {
        User result = null;
        for (User user : getAllUsers(User.class)) {
            if (user.getName().equals(name) || user.getPassword().equals(password)) {
                result = user;
            }
        }
        return result;
    }

    @Override
    public List getAllTasks() {
        List result = this.tx(session -> session.createQuery("from ru.job4j.model.Task").list());
        return result;
    }

    @Override
    public void saveTask(Task task) {
        this.tx(session -> session.save(task));
    }

    private <T> T create(T model) {
        this.tx(session -> session.save(model));
        return model;
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