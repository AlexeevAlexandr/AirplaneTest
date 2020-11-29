package com.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@Document(collection = "characteristics")
@TypeAlias("characteristic")
public class AirplaneCharacteristics {

    @Id
    private String id;
    @Field(value = "max_speed")
    private Double maxSpeed;
    @Field(value = "max_acceleration")
    private Double maxAcceleration;
    @Field(value = "max_elevation")
    private Double maxElevation;
    @Field(value = "max_degrees")
    private Double maxDegrees;

    public AirplaneCharacteristics(Double maxSpeed, Double maxAcceleration, Double maxElevation, Double maxDegrees) {
        this.maxSpeed = maxSpeed;
        this.maxAcceleration = maxAcceleration;
        this.maxElevation = maxElevation;
        this.maxDegrees = maxDegrees;
    }

    public static AirplaneCharacteristics getTestAirplaneCharacteristics(){
        return new AirplaneCharacteristics(4000.0, 20.0, 50.0, 2.5);
    }

    @Override
    public String toString() {
        return "Max Speed: \t" + maxSpeed + ";\n" +
               "Max Acceleration: \t" + maxAcceleration + ";\n" +
               "Max Elevation: \t" + maxElevation + ";\n" +
               "Max Degrees: \t" + maxDegrees + ";\n";
    }
}
