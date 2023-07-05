package repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import models.Fruit;


@ApplicationScoped
public class FruitRepository implements PanacheRepository<Fruit> {
}
