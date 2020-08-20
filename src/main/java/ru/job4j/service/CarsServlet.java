package ru.job4j.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import ru.job4j.model.Annotation.Car;
import ru.job4j.model.Annotation.Engine;
import ru.job4j.model.User;
import ru.job4j.repository.CarsDAO;
import ru.job4j.repository.CarsStore;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class CarsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> images = new ArrayList<>();
        for (File file : new File("images").listFiles()) {
            images.add(file.getName());
        }
        req.setAttribute("images", images);
        List<Car> carList = CarsDAO.getInst().getAllCars();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(carList);
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.write(json);
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext context = this.getServletConfig().getServletContext();
        File repository = (File) context.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        Map<String, String> fields = new HashMap<>();
        File newFile = null;
        try {
            List<FileItem> items = upload.parseRequest(req);
            File folder = new File("images");
            if (!folder.exists()) {
                folder.mkdir();
            }
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    File file = new File(folder + File.separator + item.getName());
                    newFile = file;
                    FileOutputStream out = new FileOutputStream(file);
                    out.write(item.getInputStream().readAllBytes());
                } else {
                    fields.put(item.getFieldName(), item.getString());
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        Engine engine = new Engine();
        if (fields.get("gas") != null) {
            engine.setType(fields.get("gas"));
        } else {
            engine.setType(fields.get("diesel"));
        }
        Car car = new Car();
        car.setBrand(fields.get("brand"));
        car.setModel(fields.get("model"));
        car.setYear(Integer.parseInt(fields.get("year")));
        if (fields.get("actual") != null) {
            car.setActual(true);
        } else {
            car.setActual(false);
        }
        car.setPhotoName(newFile.getName());
        car.setEngine(engine);
        User user = (User) req.getSession().getAttribute("user");
        car.setUser(user);
        CarsStore store = CarsDAO.getInst();
        store.createCar(car);
        resp.sendRedirect(req.getContextPath() + "/index.html");
    }
}
