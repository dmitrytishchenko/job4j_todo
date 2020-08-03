package ru.job4j.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.xml.CarXml;
import ru.job4j.model.xml.DriverXml;
import ru.job4j.model.xml.EngineXml;
import ru.job4j.model.xml.Event;

import java.util.HashSet;
import java.util.Set;

public class MainXml {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        CarXml carXml = new CarXml();
        carXml.setBrand("Skoda");
        carXml.setModel("Oct");
        EngineXml engineXml = new EngineXml();
        engineXml.setType("Gas");
        carXml.setEngineXml(engineXml);
        DriverXml driverXml = new DriverXml();
        driverXml.setName("Mark");
        Set<DriverXml> driverXmls = new HashSet<>();
        driverXmls.add(driverXml);
        carXml.setDriverXmls(driverXmls);
        Set<CarXml> cars = new HashSet<>();
        cars.add(carXml);
        driverXml.setCarsXml(cars);
        session.persist(carXml);
        session.persist(engineXml);
        session.persist(driverXml);

//        Event event = new Event();
//        event.setS("fvdofvmkdop");
//        session.save(event);
        session.getTransaction().commit();
        session.close();
        StandardServiceRegistryBuilder.destroy(registry);
    }
}

