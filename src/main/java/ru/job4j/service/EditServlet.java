package ru.job4j.service;

import ru.job4j.model.annotation.Car;
import ru.job4j.model.User;
import ru.job4j.repository.CarsDAO;
import ru.job4j.repository.CarsStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class EditServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        CarsStore store = CarsDAO.getInst();
        Car car = store.getCar(Integer.valueOf(id));
        User user = (User) req.getSession().getAttribute("user");
        if (car.getUser().getName().equals(user.getName())) {
            resp.sendRedirect(req.getContextPath() + "/edit.html");
        } else {
            PrintWriter writer = new PrintWriter(resp.getOutputStream());
            writer.write("Вы не имеете прав для изменения данного объявления");
            writer.flush();
        }
    }
}
