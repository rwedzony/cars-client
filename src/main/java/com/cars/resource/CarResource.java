package com.cars.resource;

import com.cars.api.CarGraphQlApi;
import com.cars.api.CarRestApi;
import com.cars.model.Car;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

@Path("/car")
@Produces(MediaType.APPLICATION_JSON)
public class CarResource {
    @Inject
    CarGraphQlApi graphQlClient;

    @RestClient
    CarRestApi restClient;

    @GET
    @Path("/graphQl")
    public List<Car> getAllCarsUsingGraphQl() throws ExecutionException, InterruptedException {
        List<Integer> ids = List.of(0, 1, 2, 3, 4, 5, 6);
        Uni<List<Car>> uniCars = Multi.createFrom()
                .items(ids.stream())
                .onItem()
                .transformToUniAndMerge(
                        id -> getGraphQlCar(id).get()
                                .onFailure().recoverWithUni(Uni.createFrom().nullItem())
                )
                .collect()
                .asList();

        List<Car> cars = uniCars.await().indefinitely();
        return cars;
    }

    @GET
    @Path("/rest")
    public List<Car> getAllCarsUsingRest() throws ExecutionException, InterruptedException {
        List<Integer> ids = List.of(0, 1, 2, 3, 4, 5, 6);
        Uni<List<Car>> uniCars = Multi.createFrom()
                .items(ids.stream())
                .onItem()
                .transformToUniAndMerge(
                        id -> getRestCar(id).get()
                                .onFailure().recoverWithUni(Uni.createFrom().nullItem())
                )
                .collect()
                .asList();
        List<Car> cars = uniCars.await().indefinitely();
        return cars;
    }
    private Supplier<Uni<Car>> getGraphQlCar(int id) {
        return () -> graphQlClient.getCar(id);
    }

    private Supplier<Uni<Car>> getRestCar(int id) {
        return () -> restClient.getCarById(String.valueOf(id));
    }
}
