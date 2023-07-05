package services;

import jakarta.enterprise.context.ApplicationScoped;
import models.Fruit;
import repository.FruitRepository;

import java.util.List;

@ApplicationScoped
public class FruitService {
    private final FruitRepository fruitRepository;

    public FruitService(FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

    public List<Fruit> list() {
        return fruitRepository.listAll();
    }

    public Fruit add(Fruit inputFruit) {
        fruitRepository.persist(inputFruit);
        return inputFruit;
    }
}
