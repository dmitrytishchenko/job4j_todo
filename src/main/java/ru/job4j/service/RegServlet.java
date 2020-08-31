package ru.job4j.service;

import ru.job4j.model.Task;
import ru.job4j.repository.CarsDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class RegServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String password1 = req.getParameter("password1");
        String desc = req.getParameter("desc");
        String done = req.getParameter("done");
        Task task;
        if (done.equals("true")) {
            task = new Task(desc, new Date(), true);
            CarsDAO.getInst().saveTask(task);
        } else {
            task = new Task(desc, new Date(), false);
        }
        CarsDAO.getInst().saveTask(task);
        if (password.equals(password1)) {
            CarsDAO.getInst().addUser(name, password, task);
        }
        doGet(req, resp);
    }
}
