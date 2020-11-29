package com.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

import static com.demo.model.WayPoint.getTestWayPoints;

@Data
@NoArgsConstructor
@Document(collection = "flights")
@TypeAlias("flight")
public class Flight {

    @Id
    private String number;
    @DBRef
    private List<WayPoint> wayPoints;
    @DBRef
    private List<TemporaryPoint> passedPoints = new ArrayList<>();

    public Flight(List<WayPoint> wayPoints){
        this.wayPoints = wayPoints;
    }

    public static Flight getTestFlight(){
        return new Flight(getTestWayPoints());
    }

    public String getFlightInfo(){
        return String.format("Flight number: %s; Duration of flight: %d sec.;", number, passedPoints.size());
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        wayPoints.forEach(w -> buffer.append(w.toString()).append("\n"));
        return buffer.toString();
    }

}
