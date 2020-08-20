package ru.job4j.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.model.Task;
import ru.job4j.repository.CarsDAO;
import ru.job4j.repository.CarsStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class TaskServlet extends HttpServlet {
private final CarsStore store = CarsDAO.getInst();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(store.getAllTasks());
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.write(json);
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String desc = req.getParameter("desc");
        Task task = new Task(desc, new Date(), false);
        store.saveTask(task);
        resp.sendRedirect(req.getContextPath() + "/index.html");
    }
}
