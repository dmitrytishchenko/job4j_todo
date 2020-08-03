package ru.job4j.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.Annotation.Car;
import ru.job4j.model.Annotation.Driver;
import ru.job4j.model.Annotation.Engine;
import ru.job4j.model.xml.CarXml;
import ru.job4j.model.xml.DriverXml;
import ru.job4j.model.xml.EngineXml;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//        Car car = new Car();
//        car.setBrand("Skoda");
//        car.setModel("Oct");
//        Engine engine = new Engine();
//        engine.setType("Gas");
//        car.setEngine(engine);
//        Driver driver = new Driver();
//        driver.setName("Mark");
//        Set<Car> cars = new HashSet<>();
//        cars.add(car);
//        driver.setCars(cars);
//        Set<Driver> drivers = new HashSet<>();
//        drivers.add(driver);
//        car.setDrivers(drivers);
//        session.persist(car);
//        session.persist(engine);
//        session.persist(driver);
//        session.getTransaction().commit();
//        session.close();
//        StandardServiceRegistryBuilder.destroy(registry);

    }
}
