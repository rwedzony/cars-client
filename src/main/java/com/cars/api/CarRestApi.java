package com.cars.api;

import com.cars.model.Car;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@RegisterRestClient(configKey = "car-api")
@Path("/car")
public interface CarRestApi {
    @GET
    @Path("/{id}")
    Uni<Car> getCarById(@PathParam("id") String id);
}
