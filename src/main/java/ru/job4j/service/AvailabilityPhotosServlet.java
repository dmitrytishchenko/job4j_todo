package ru.job4j.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.model.Annotation.Car;
import ru.job4j.repository.CarsDAO;
import ru.job4j.repository.CarsStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class AvailabilityPhotosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        CarsStore store = CarsDAO.getInst();
        List<Car> cars = store.getCarsWithPhotos();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(cars);
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.write(json);
        writer.flush();
    }
}
