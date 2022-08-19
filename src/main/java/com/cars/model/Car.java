package com.cars.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Car {
    private String brand;
    private String model;
    private Integer engineCapacity;
    private LocalDate productionYear;
}
