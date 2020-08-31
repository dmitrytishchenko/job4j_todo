package ru.job4j.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.model.User;
import ru.job4j.repository.CarsDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(CarsDAO.getInst().getAllUsers(User.class));
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.write(json);
        writer.flush();
    }
}
