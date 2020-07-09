package ru.job4j.cars.model;

import javax.persistence.*;
import java.util.*;

/**
 * Class-model for car owners
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 08.07.2020
 * @version 1.0
 */
@Entity
@Table (name = "owner")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "history_owner",
            joinColumns = {
                    @JoinColumn(name = "owner_id", nullable = true, updatable = true)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "car_id", nullable = true, updatable = true)
            }
    )
    Set<Car> cars = new HashSet<>();

    public Owner() { }

    public Owner(int id, String name, Set<Car> cars) {
        this.id = id;
        this.name = name;
        this.cars = cars;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void removeCar(Car car) {
        cars.remove(car);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Owner)) {
            return false;
        }
        Owner owner = (Owner) o;
        return getId() == owner.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        List<String> carNames = new ArrayList<>();
        String strCars;
        try {
            cars.forEach((el) -> carNames.add(el.getName()));
            strCars = (carNames.size() > 0) ? String.join(", ", carNames) : "none";
        } catch (Exception e) {
            strCars = "none";
        }
        return "Owner{" + "id=" + id + ", name='" + name + '\'' + ", cars=" + strCars + '}';
    }
}
