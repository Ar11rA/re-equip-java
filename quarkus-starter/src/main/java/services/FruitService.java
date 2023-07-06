package services;

import exceptions.HttpException;
import models.Fruit;
import repository.FruitRepository;

import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FruitService {
    private final FruitRepository fruitRepository;

    public FruitService(FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

    public List<Fruit> list() {
        return fruitRepository.listAll();
    }

    public Fruit findById(long id) {
        Fruit fruit =  fruitRepository.findById(id);
        if (fruit == null) {
            throw new HttpException("Fruit not found", 404);
        }
        return fruit;
    }

    public Fruit add(Fruit inputFruit) {
        fruitRepository.persist(inputFruit);
        return inputFruit;
    }
}
