package ru.job4j.cars.model;

import javax.persistence.*;
import java.util.*;

/**
 * Class - model of car
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 08.07.2020
 * @version 1.0
 */
@Entity
@Table (name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "history_owner",
            joinColumns = {
                    @JoinColumn(name = "car_id", nullable = true, updatable = true)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "owner_id", nullable = true, updatable = true)
            }
    )
    private Set<Owner> owners = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "engine_id")
    private Engine engine;

    public Car() { }

    public Car(int id, String name, Engine engine, Set<Owner> owners) {
        this.id = id;
        this.name = name;
        this.engine = engine;
        this.owners = owners;
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

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Set<Owner> getOwners() {
        return owners;
    }

    public void setOwners(Set<Owner> owners) {
        this.owners = owners;
    }

    public void addOwner(Owner owner) {
        owners.add(owner);
    }

    public void removeOwners(Owner owner) {
        owners.remove(owner);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Car)) {
            return false;
        }
        Car car = (Car) o;
        return getId() == car.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        List<String> ownerNames = new ArrayList<>();
        String strOwners;
        try {
            owners.forEach((el) -> ownerNames.add(el.getName()));
            strOwners = (ownerNames.size() > 0) ? String.join(", ", ownerNames) : "none";
        } catch (Exception e) {
            strOwners = "none";
        }
        return "Car{" + "id=" + id + ", name='" + name + '\'' + ", owners=" + strOwners + '}';
    }
}
