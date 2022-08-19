package com.cars.api;

import com.cars.model.Car;
import io.smallrye.graphql.client.typesafe.api.GraphQLClientApi;
import io.smallrye.mutiny.Uni;

@GraphQLClientApi(configKey = "cars-typesafe")
public interface CarGraphQlApi {
    Uni<Car> getCar(int carId);
}
