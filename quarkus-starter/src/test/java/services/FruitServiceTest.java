package services;

import exceptions.HttpException;
import models.Fruit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import repository.FruitRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;

public class FruitServiceTest {

    private static FruitRepository fruitRepository;
    private static FruitService fruitService;

    @BeforeAll
    public static void setup() {
        fruitRepository = Mockito.mock(FruitRepository.class);
        fruitService = new FruitService(fruitRepository);
    }

    @Test
    public void listFruitsSuccess_0_fruits() {
        Mockito.when(fruitRepository.listAll()).thenReturn(new ArrayList<>());
        List<Fruit> fruits = fruitService.list();
        Assertions.assertEquals(0, fruits.size());
    }

    @Test
    public void listFruitsSuccess_n_fruits() {
        Mockito.when(fruitRepository.listAll()).thenReturn(Arrays.asList(new Fruit(), new Fruit()));
        List<Fruit> fruits = fruitService.list();
        Assertions.assertEquals(2, fruits.size());
    }

    @Test
    public void findById_error_not_found() {
        Mockito.when(fruitRepository.findById(anyLong()))
          .thenReturn(null);
        HttpException exception = Assertions.assertThrows(HttpException.class, () ->
          fruitService.findById(1));
        Assertions.assertEquals("Fruit not found", exception.getMessage());
        Assertions.assertEquals(404, exception.getErrorCode());
    }

    @Test
    public void findById_success() {
        Fruit fruit = new Fruit();
        fruit.setId(100);
        Mockito.when(fruitRepository.findById(anyLong()))
          .thenReturn(fruit);
        Fruit fruitOutput = fruitService.findById(1);
        Assertions.assertEquals(100 , fruitOutput.getId());
    }

}

