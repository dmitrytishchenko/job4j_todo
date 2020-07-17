package ru.job4j.service;


import ru.job4j.model.User;
import ru.job4j.repository.DBStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        if (name.equals("admin") && password.equals("admin")) {
            HttpSession sc = req.getSession();
            User user = DBStore.getInst().checkNameAndPasswordByUser(name, password);
            if (user != null) {
                sc.setAttribute("user", user);
                req.getRequestDispatcher("index.html").forward(req, resp);
            } else {
                req.setAttribute("error", "Не верный пароль");
                doGet(req, resp);
            }
        }
    }
}
