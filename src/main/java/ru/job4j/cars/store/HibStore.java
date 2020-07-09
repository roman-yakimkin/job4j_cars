package ru.job4j.cars.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.Owner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Function;

public class HibStore implements Store {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    private final SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    private HibStore() {
    }

    public static final class Lazy {
        private static final Store INST = new HibStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    private <T> T tx(Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T result = command.apply(session);
            tx.commit();
            return result;
        } catch (final Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public void addCar(Car car) {
        this.tx(
            session -> session.save(car)
        );
    }

    @Override
    public void updateCar(Car car) {
        this.tx(
            session -> {
                session.update(car);
                return null;
            }
        );
    }

    @Override
    public void deleteCar(int id) {
        this.tx(
            session -> {
                Car car = new Car();
                car.setId(id);
                session.delete(car);
                return null;
            }
        );
    }

    @Override
    public Car getCar(int id) {
        return this.tx(
            session -> {
                Query query = session.createQuery("from ru.job4j.cars.model.Car where id = :id");
                query.setParameter("id", id);
                return (Car) query.uniqueResult();
            }
        );
    }

    @Override
    public List<Car> getCars() {
        return this.tx(
           session -> session.createQuery("from ru.job4j.cars.model.Car").list()
        );
    }

    @Override
    public void addOwner(Owner owner) {
        this.tx(
           session -> session.save(owner)
        );
    }

    @Override
    public void updateOwner(Owner owner) {
        this.tx(
            session -> {
                session.update(owner);
                return null;
            }
        );
    }

    @Override
    public void deleteOwner(int id) {
        this.tx(
            session -> {
                Owner owner = new Owner();
                owner.setId(id);
                session.delete(owner);
                return null;
            }
        );
    }

    @Override
    public Owner getOwner(int id) {
        return this.tx(
            session -> {
                Query query = session.createQuery("from ru.job4j.cars.model.Owner where id = :id");
                query.setParameter("id", id);
                return (Owner) query.uniqueResult();
            }
        );
    }

    @Override
    public List<Owner> getOwners() {
        return this.tx(
           session -> session.createQuery("from ru.job4j.cars.model.Owner").list()
        );
    }

    @Override
    public Engine getEngine(int id) {
        return this.tx(
            session -> {
                Query query = session.createQuery("from ru.job4j.cars.model.Engine where id = :id");
                query.setParameter("id", id);
                return (Engine) query.uniqueResult();
            }
        );
    }

    @Override
    public List<Engine> getEngines() {
        return this.tx(
           session -> session.createQuery("from ru.job4j.cars.model.Engine").list()
        );
    }

    public static void main(String[] args) {
        System.out.println("Cars app test");
        Engine carburetor = HibStore.instOf().getEngine(1);
        Engine diesel = HibStore.instOf().getEngine(2);
        Engine electric = HibStore.instOf().getEngine(3);

        Car car1 = new Car();
        car1.setName("Car 1 with carburetor engine");
        car1.setEngine(carburetor);
        HibStore.instOf().addCar(car1);
        Car car2 = new Car();
        car2.setName("Car 2 with diesel engine");
        car2.setEngine(diesel);
        HibStore.instOf().addCar(car2);
        Car car3 = new Car();
        car3.setName("Car 3 with electric engine");
        car3.setEngine(electric);
        HibStore.instOf().addCar(car3);

        System.out.println("Three cars were added ================");
        HibStore.instOf().getCars().forEach(System.out::println);

        Owner owner1 = new Owner();
        owner1.setName("Ivanov");
        HibStore.instOf().addOwner(owner1);
        Owner owner2 = new Owner();
        owner2.setName("Petrov");
        HibStore.instOf().addOwner(owner2);
        Owner owner3 = new Owner();
        owner3.setName("Sidorov");
        HibStore.instOf().addOwner(owner3);

        System.out.println("Three owners were added ===============");
        HibStore.instOf().getOwners().forEach(System.out::println);

        System.out.println("Owners get cars ====================");
        owner1.addCar(car1);
        HibStore.instOf().updateOwner(owner1);
        owner2.addCar(car2);
        HibStore.instOf().updateOwner(owner2);
        owner3.addCar(car3);
        HibStore.instOf().updateOwner(owner3);

        System.out.println("Owner 1 and owner 3 change their cars ==========");
        owner1.addCar(car3);
        HibStore.instOf().updateOwner(owner1);
        owner3.addCar(car1);
        HibStore.instOf().updateOwner(owner3);
        System.out.println("List of owners =====================");
        HibStore.instOf().getOwners().forEach(System.out::println);
        System.out.println("List of cars =====================");
        HibStore.instOf().getCars().forEach(System.out::println);

        HibStore.instOf().getCars().forEach(el -> HibStore.instOf().deleteCar(el.getId()));
        System.out.println("Cars were deleted ====================");
        HibStore.instOf().getOwners().forEach(el -> HibStore.instOf().deleteOwner(el.getId()));
        System.out.println("Owners were deleted ===================");
    }
}
