package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.Query;

public class HbmRunCandidates {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            Candidate candidate1 = Candidate.of("Alex", "Junior", 100);
            Candidate candidate2 = Candidate.of("Ben", "Middle", 180);
            Candidate candidate3 = Candidate.of("Fred", "Senior", 250);
            session.save(candidate1);
            session.save(candidate2);
            session.save(candidate3);
            /**
             * Select
             */
            Query query = session.createQuery("from Candidate");
            for (Object candidate : ((org.hibernate.query.Query) query).list()) {
                System.out.println(candidate);
            }
            Query queryId = session.createQuery("from Candidate c where c.id = :fId")
                    .setParameter("fId", 2);
            System.out.println(((org.hibernate.query.Query) queryId).uniqueResult() + " - Query by ID");
            Query queryName = session.createQuery("from Candidate c where c.name = :fName")
                    .setParameter("fName", "Ben");
            System.out.println(((org.hibernate.query.Query) queryName).uniqueResult() + " - Query by NAME");
            /**
             * Update
             */
            session.createQuery(
                    "update Candidate c set c.name = :fNewName, c.salary = :fNewSalary where c.id = :fID").
                    setParameter("fNewName", "Lee").
                    setParameter("fNewSalary", 270).
                    setParameter("fID", 3).
                    executeUpdate();
            /**
             * Insert
             */
            session.createQuery("insert into Candidate (name, experience, salary)" +
                    "select concat (c.name,'Frank'), c.experience, c.salary + 100 " +
                    "from Candidate c where c.id = :fId").setParameter("fId", 3).executeUpdate();
            /**
             * Delete
             */
            session.createQuery("delete Candidate c where c.id = :fId").setParameter("fId", 4).executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
