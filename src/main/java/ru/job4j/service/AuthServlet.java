package ru.job4j.service;

import ru.job4j.model.User;
import ru.job4j.repository.CarsDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthServlet extends HttpServlet {
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
        HttpSession sc = req.getSession();
        if (name == null && password == null) {
            User user = new User();
            user.setRole(User.Role.guest);
            sc.setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/index.html");
            return;
        }
        User user = CarsDAO.getInst().checkNameAndPasswordByUser(name, password);
        if (user != null) {
            if (user.getName().equals("admin")) {
                user.setRole(User.Role.admin);
            } else {
                user.setRole(User.Role.customer);
            }
            sc.setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/index.html");
        } else {
            req.setAttribute("error", "Не верный пароль");
            doGet(req, resp);
        }
    }
}
