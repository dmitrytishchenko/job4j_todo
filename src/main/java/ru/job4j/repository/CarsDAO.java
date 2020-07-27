package ru.job4j.repository;


import ru.job4j.model.Annotation.Car;

import java.util.List;

public class CarsDAO implements CarsStore {
    private HibernateUtil hb = new HibernateUtil();

    private CarsDAO() {
    }

    private final static class Lazy {
        private final static CarsStore INST = new CarsDAO();
    }

    public static CarsStore getInst() {
        return Lazy.INST;
    }

    public void createCar(Car car) {
        hb.create(car);
    }

    public void updateCar(Car car) {
        hb.update(car);
    }

    public void deleteCar(Car car) {
        hb.delete(car);
    }

    public List<Car> getAllCars() {
        List<Car> result = hb.tx(session -> session.createQuery("from ru.job4j.model.Annotation.Car").list());
        return result;
    }

    public Car getCar(int id) {
        Car result = hb.tx(session -> session.get(Car.class, id));
        return result;
    }
}
