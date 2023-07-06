package resource;

import models.Fruit;
import services.FruitService;

import java.util.List;
import jakarta.annotation.security.PermitAll;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/fruits")
public class FruitResource {

    private final FruitService fruitService;

    public FruitResource(FruitService fruitService) {
        this.fruitService = fruitService;
    }

    @GET
    @PermitAll()
    public List<Fruit> list() {
        return fruitService.list();
    }

    @GET
    @Path("/{id}")
    @PermitAll()
    public Fruit findById(long id) {
        return fruitService.findById(id);
    }

    @POST
    @Transactional
    @PermitAll()
    public Fruit add(Fruit inputFruit) {
        return fruitService.add(inputFruit);
    }
}