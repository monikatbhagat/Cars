package com.example.cars.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Car {
    private String id;
    private String brand;
    private String model;
    private String year;
    private String variant;
    private String state;
    private String kilom;

    public Car()
    {

    }

    public Car(String id,String brand, String model, String year, String variant, String state, String kilom) {
       this.id=id;
       this.brand=brand;
       this.model=model;
       this.year=year;
       this.variant=variant;
       this.state=state;
       this.kilom=kilom;
    }

    public String getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getYear() {
        return year;
    }

    public String getVariant() {
        return variant;
    }

    public String getState() {
        return state;
    }

    public String getKilom() {
        return kilom;
    }
}
