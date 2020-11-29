package com.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.gavaghan.geodesy.GlobalPosition;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Document(collection = "way_points")
@TypeAlias("wpoint")
public class WayPoint extends GlobalPosition {

    @Id
    String id;
    @Field(value = "speed")
    private Double speed;

    public WayPoint(){
        super(0d, 0d, 0d);
    }

    public WayPoint(double latitude, double longitude, double elevation, double speed){
        super(latitude, longitude, elevation);
        this.speed = speed;
    }

    public WayPoint(GlobalPosition position, double speed) {
        this(position.getLatitude(), position.getLongitude(), position.getElevation(), speed);
    }

    public static List<WayPoint> getTestWayPoints(){
        return Arrays.asList(
                new WayPoint(38.88922, -77.04978, 5000.0, 2000.0),
                new WayPoint(43.85889, -74.29583, 2000.0, 3800.0),
                new WayPoint(48.85889, -69.29583, 0.0, 0.0)
        );
    }

    public GlobalCoordinates getGlobalCoordinate(){
        return new GlobalCoordinates(getLatitude(), getLongitude());
    }

    public GlobalPosition getGlobalPosition(){
        return new GlobalPosition(getLatitude(), getLongitude(), getElevation());
    }

    @Override
    public String toString() {
        return super.toString() + ";speed=" + this.speed;
    }
}
