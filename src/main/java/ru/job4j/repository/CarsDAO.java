package ru.job4j.repository;

import ru.job4j.model.Annotation.Car;
import ru.job4j.model.Task;
import ru.job4j.model.User;

import java.util.List;

public class CarsDAO implements CarsStore {
    private HibernateUtil hb = new HibernateUtil();

    private CarsDAO() {
        checkAdmin();
    }

    private final static class Lazy {
        private final static CarsStore INST = new CarsDAO();
    }

    public static CarsStore getInst() {
        return Lazy.INST;
    }

    private void checkAdmin() {
        Task adminTask = new Task();
        saveTask(adminTask);
        User user = new User();
        user.setName("admin");
        user.setPassword("admin");
        user.setTask(adminTask);
        hb.tx(session -> session.save(user));
    }

    @Override
    public void createCar(Car car) {
        hb.create(car);
    }

    @Override
    public void updateCar(Car car) {
        hb.update(car);
    }

    @Override
    public void deleteCar(Car car) {
        hb.delete(car);
    }

    @Override
    public List<Car> getAllCars() {
        List<Car> result = hb.tx(session -> session.createQuery("from ru.job4j.model.Annotation.Car").list());
        return result;
    }

    @Override
    public List getCarsByBrand(String brandCar) {
        List result = hb.tx(session ->
                session.createQuery("from Car c where c.brand = :fBrand").setParameter("fBrand", brandCar).list());
        return result;
    }

    @Override
    public List getCarsWithPhotos() {
        List result = hb.tx(session -> session.createQuery("from Car c where c.photoName is not null ").list());
        return result;
    }

    @Override
    public Car getCar(int id) {
        Car result = hb.tx(session -> session.get(Car.class, id));
        return result;
    }

    @Override
    public void addUser(String name, String password, Task task) {
        User user = new User().of(name, password, task);
        hb.tx(session -> session.save(user));
    }

    @Override
    public List<User> getAllUsers(Class<User> userClass) {
        List<User> result = hb.tx(session -> session.createQuery("from " + userClass.getName(), userClass).list());
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
        List result = hb.tx(session -> session.createQuery("from ru.job4j.model.Task").list());
        return result;
    }

    @Override
    public void saveTask(Task task) {
        hb.tx(session -> session.save(task));
    }
}
