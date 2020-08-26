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

public class BrandServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        CarsStore store = CarsDAO.getInst();
        String brand = req.getParameter("name");
        if (brand.equals("All brands")) {
            List<Car> cars = store.getAllCars();
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(cars);
            PrintWriter writer = new PrintWriter(resp.getOutputStream());
            writer.write(json);
            writer.flush();
        } else {
            List<Car> cars = store.getCarsByBrand(brand);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(cars);
            PrintWriter writer = new PrintWriter(resp.getOutputStream());
            writer.write(json);
            writer.flush();
        }
    }
}
