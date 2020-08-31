package ru.job4j.mapping;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        final SessionFactory sf = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory();
        try {
            Role role = create(Role.of("ADMIN"), sf);
            create(Customer.of("Petr Arsentev", role), sf);
            for (Customer customer : findAll(Customer.class, sf)) {
                System.out.println(customer.getName() + " " + customer.getRole().getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sf.close();
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static <T> T create(T model, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(model);
        session.getTransaction().commit();
        session.close();
        return model;
    }

    public static <T> List<T> findAll(Class<T> tClass, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        List<T> list = session.createQuery("from " + tClass.getName(), tClass).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }
}
