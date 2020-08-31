import org.junit.Test;
import ru.job4j.model.annotation.Car;
import ru.job4j.model.annotation.Driver;
import ru.job4j.model.annotation.Engine;
import ru.job4j.repository.CarsDAO;
import ru.job4j.repository.CarsStore;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CarsDAOTest {
    private final CarsStore store = CarsDAO.getInst();

    @Test
    public void addCar() {
        Car car = new Car();
        car.setBrand("Mazda");
        car.setModel("626");
        Engine engine = new Engine();
        engine.setType("Gas");
        car.setEngine(engine);
        Driver driver = new Driver();
        driver.setName("Mark");
        Set<Driver> drivers = new HashSet<>();
        drivers.add(driver);
        car.setDrivers(drivers);
        store.createCar(car);
        Set<Car> cars = new HashSet<>();
        cars.add(car);
        driver.setCars(cars);
        assertThat(store.getAllCars().get(0).getBrand(), is("Mazda"));
    }

    @Test
    public void updateCar() {
        Car car = new Car();
        car.setBrand("Mazda");
        car.setModel("626");
        Engine engine = new Engine();
        engine.setType("Gas");
        car.setEngine(engine);
        Driver driver = new Driver();
        driver.setName("Mark");
        Set<Driver> drivers = new HashSet<>();
        drivers.add(driver);
        car.setDrivers(drivers);
        store.createCar(car);
        Set<Car> cars = new HashSet<>();
        cars.add(car);
        driver.setCars(cars);
        Car newCar = store.getCar(store.getAllCars().get(0).getId());
        newCar.setBrand("Mercedes");
        newCar.setModel("GL");
        Engine newEngine = new Engine();
        newEngine.setType("Diesel");
        newCar.setEngine(newEngine);
        Driver newDriver = new Driver();
        newDriver.setName("Ben");
        Set<Driver> newDrivers = new HashSet<>();
        newDrivers.add(newDriver);
        store.updateCar(newCar);
        Set<Car> newCars = new HashSet<>();
        newCars.add(newCar);
        newDriver.setCars(newCars);
        assertThat(store.getAllCars().get(0).getBrand(), is("Mercedes"));
    }

    @Test
    public void deleteCar() {
        Car car = new Car();
        car.setBrand("Mazda");
        car.setModel("626");
        Engine engine = new Engine();
        engine.setType("Gas");
        car.setEngine(engine);
        Driver driver = new Driver();
        driver.setName("Mark");
        Set<Driver> drivers = new HashSet<>();
        drivers.add(driver);
        car.setDrivers(drivers);
        store.createCar(car);
        Set<Car> cars = new HashSet<>();
        cars.add(car);
        driver.setCars(cars);
        store.deleteCar(car);
        assertThat(store.getAllCars().size(), is(0));
    }
}