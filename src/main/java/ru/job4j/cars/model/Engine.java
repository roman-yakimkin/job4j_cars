package ru.job4j.cars.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * Class - model for car engine
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 08.07.2020
 * @versin 1.0
 */
@Entity
@Table(name = "engine")
public class Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    public Engine() { }

    public Engine(int id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Engine)) {
            return false;
        }
        Engine engine = (Engine) o;
        return getId() == engine.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
