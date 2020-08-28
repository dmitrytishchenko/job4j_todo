package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            Student one = Student.of("Alexey", 15, "NY");
            Student two = Student.of("Ben", 37, "Moscow");
            Student three = Student.of("Igor", 15, "Rostov-on-Don");
            session.save(one);
            session.save(two);
            session.save(three);
            /**
             * Select
             */
            Query query = session.createQuery("from Student");
            for (Object st : query.list()) {
                System.out.println(st);
            }
            Query query1 = session.createQuery("from ru.job4j.hql.Student");
            for (Object st : query1.list()) {
                System.out.println(st + "full query");
            }
            Query query2 = session.createQuery("from Student s where s.id = 1");
            System.out.println(query2.uniqueResult() + "query with where");
            Query query3 = session.createQuery("from Student s where s.id = :fId");
            query3.setParameter("fId", 1);
            System.out.println((query3.uniqueResult() + "query with out parameter in query"));
            /**
             * Update
             */
            Query query4 = session.createQuery(
                    "update Student s set s.age = :newAge, s.city = :newCity where s.id = :fId");
            query4.setParameter("newAge", 56);
            query4.setParameter("newCity", "LA");
            query4.setParameter("fId", 2);
            session.createQuery("update Student s set s.age = :newAge, s.city = :newCity where s.id = :fId")
                    .setParameter("newAge", 22)
                    .setParameter("newCity", "London")
                    .setParameter("fId", 1)
                    .executeUpdate();
            query4.executeUpdate();
            Query query5 = session.createQuery("from Student s where s.id = :fId");
            query5.setParameter("fId", 1);
            System.out.println(query5.uniqueResult() + " update student where id = 1");
            session.createQuery("delete from Student where id = :fId").
                    setParameter("fId", 1).executeUpdate();
            session.createQuery("insert into Student (name, age, city)" +
                    "select  concat(s.name, 'new'), s.age + 5, s.city " +
                    "from Student s where s.id = :fId").setParameter("fId", 3).executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
