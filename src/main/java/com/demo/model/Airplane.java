package com.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import static com.demo.model.AirplaneCharacteristics.getTestAirplaneCharacteristics;
import static com.demo.model.Flight.getTestFlight;

@NoArgsConstructor
@Data
@Document(collection = "airplanes")
@TypeAlias("airplane")
public class Airplane {

    @Id
    String id;
    @DBRef
    AirplaneCharacteristics airplaneCharacteristics;
    @DBRef
    TemporaryPoint position;
    @DBRef
    Flight flight;

    public Airplane(AirplaneCharacteristics airplaneCharacteristics, Flight flight){
        this.airplaneCharacteristics = airplaneCharacteristics;
        this.flight = flight;
    }

    public static Airplane getTestAirplane(){
        return new Airplane(getTestAirplaneCharacteristics(), getTestFlight());
    }

    @Override
    public String toString() {
        return "\n------------------------------------------------------------------------------------------\n" +
                "Airplane characteristics:\n" +
                airplaneCharacteristics.toString() +
                "Starts flight by WayPoints:\n" +
                flight.toString() +
                "------------------------------------------------------------------------------------------\n";
    }
}
