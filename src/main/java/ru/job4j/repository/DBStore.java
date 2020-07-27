package ru.job4j.repository;

import ru.job4j.model.Task;

import java.util.List;

public class DBStore implements Store {
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
        List result = new HibernateUtil().tx(session -> session.createQuery("from ru.job4j.model.Task").list());
        return result;
    }

    @Override
    public void save(Task task) {
        new HibernateUtil().tx(session -> session.save(task));
    }
}
