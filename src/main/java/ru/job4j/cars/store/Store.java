package ru.job4j.cars.store;

import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.Owner;

import java.util.List;

/**
 * An interface for operations with cars
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 08.07.2020
 * @version 1.0
 */
public interface Store {
    void addCar(Car car);
    void updateCar(Car car);
    void deleteCar(int id);
    Car getCar(int id);
    List<Car> getCars();
    void addOwner(Owner owner);
    void updateOwner(Owner owner);
    void deleteOwner(int id);
    Owner getOwner(int id);
    List<Owner> getOwners();
    Engine getEngine(int id);
    List<Engine> getEngines();
}
