package ru.job4j.repository;

import ru.job4j.model.Annotation.Car;

import java.util.List;

interface CarsStore {
    void createCar(Car car);

    void updateCar(Car car);

    void deleteCar(Car car);

    List<Car> getAllCars();

    Car getCar(int id);
}

