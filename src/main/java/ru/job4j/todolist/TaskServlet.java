package ru.job4j.todolist;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class TaskServlet extends HttpServlet {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    private SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        List result = this.tx(session -> session.createQuery("from ru.job4j.todolist.Task").list());
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(result);
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.write(json);
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String desc = req.getParameter("desc");
        Task task = new Task(desc, new Date(), false);
        this.tx(session -> session.save(task));
        resp.sendRedirect("/todo/index.html");
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
