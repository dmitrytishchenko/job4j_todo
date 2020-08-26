package ru.job4j.repository;

import ru.job4j.model.Annotation.Car;
import ru.job4j.model.Task;
import ru.job4j.model.User;

import java.util.List;

public interface CarsStore {
    void createCar(Car car);

    void updateCar(Car car);

    void deleteCar(Car car);

    List<Car> getAllCars();

    List<Car> getCarsByBrand(String brandCar);

    List<Car> getCarsWithPhotos();

    Car getCar(int id);

    void addUser(String name, String password, Task task);

    List<User> getAllUsers(Class<User> userClass);

    User checkNameAndPasswordByUser(String name, String password);

    List getAllTasks();

    void saveTask(Task task);
}

