package com.demo.service;

import com.demo.model.AirplaneCharacteristics;
import com.demo.model.TemporaryPoint;
import com.demo.model.WayPoint;
import org.gavaghan.geodesy.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaneCalculationImpl implements PlaneCalculation {

    private final List<TemporaryPoint> finalPoints;
    private final GeodeticCalculator geodeticCalculator;
    private final Ellipsoid reference;
    private final double[] endBearing;
    private AirplaneCharacteristics characteristics;
    private GlobalPosition Position_Start;
    private double V_Start;
    private double V_End;
    private double H_Start;
    private double H_End;
    private double Alpha_Start;
    private double T_Optimal;
    private double A_Optimal;
    private double H_Optimal;
    private double Alpha_Optimal;

    public PlaneCalculationImpl() {
        finalPoints = new ArrayList<>();
        geodeticCalculator = new GeodeticCalculator();
        reference = Ellipsoid.WGS84;
        endBearing = new double[1];
    }

    @Override
    public List<TemporaryPoint> calculateRoute(AirplaneCharacteristics characteristics, List<WayPoint> wayPoints) {
        for (WayPoint wayPoint : wayPoints){
            if(characteristics.getMaxSpeed() < wayPoint.getSpeed()) {
                return null;
            }
        }
        List<TemporaryPoint> points = new ArrayList<>();
        finalPoints.clear();
        this.characteristics = characteristics;

        for (int i = 0; i < wayPoints.size() - 1; i++) {
            points.clear();
            init(wayPoints.get(i), wayPoints.get(i + 1));
            points.add(new TemporaryPoint(Position_Start, V_Start, Alpha_Optimal));

            for (int t = 1, size = points.size(); t <= (int)T_Optimal; t++, size++) {
                TemporaryPoint lastPoint = points.get(size - 1);
                TemporaryPoint temporaryPoint = calculateEndingGlobalCoordinates(
                        lastPoint.getGlobalCoordinate(), lastPoint.getCourseInDegrees(), getCurrentDistance(t),
                        getCurrentElevation(t), getCurrentSpeed(t)
                );
                if (Math.abs(temporaryPoint.getCourseInDegrees() - lastPoint.getCourseInDegrees()) > characteristics.getMaxDegrees()) {
                    t = size = 0;
                    T_Optimal *= 2;
                    setOptimalVars();
                    points.clear();
                    points.add(new TemporaryPoint(Position_Start, V_Start, Alpha_Optimal));
                } else {
                    points.add(temporaryPoint);
                }
            }
            finalPoints.addAll(points);
        }
        return finalPoints;
    }

    private TemporaryPoint calculateEndingGlobalCoordinates(GlobalCoordinates globalCoordinate, double startBearing, double distance, double elevation, double speed) {
        GlobalCoordinates globalCoordinates = geodeticCalculator.calculateEndingGlobalCoordinates(reference, globalCoordinate, startBearing, distance, endBearing);
        return new TemporaryPoint(globalCoordinates.getLatitude(), globalCoordinates.getLongitude(), elevation, speed, endBearing[0]);
    }

    private void init(WayPoint from, WayPoint to) {
        V_Start = from.getSpeed();
        V_End = to.getSpeed();
        H_Start = from.getElevation();
        H_End = to.getElevation();

        Position_Start = new GlobalPosition(from.getLatitude(), from.getLongitude(), H_Start);
        GlobalPosition Position_End = new GlobalPosition(to.getLatitude(), to.getLongitude(), H_End);
        GeodeticMeasurement geoMeasurement = geodeticCalculator.calculateGeodeticMeasurement(reference, Position_Start, Position_End);

        Alpha_Start = geoMeasurement.getAzimuth();
        T_Optimal = (2 * geoMeasurement.getPointToPointDistance()) / (V_Start + V_End);
        setOptimalVars();
    }

    private void setOptimalVars() {
        double V_Delta = V_End - V_Start;
        double H_Delta = H_End - H_Start;

        A_Optimal = V_Delta / T_Optimal;
        H_Optimal = H_Delta / T_Optimal;
        Alpha_Optimal = calculateEndingGlobalCoordinates(
                Position_Start, Alpha_Start, getCurrentDistance(1), getCurrentElevation(1), getCurrentSpeed(1)).getCourseInDegrees();

        if(Math.abs(A_Optimal) > characteristics.getMaxAcceleration()) {
            T_Optimal = Math.abs(V_Delta) / characteristics.getMaxAcceleration();
            A_Optimal = V_Delta / T_Optimal;
            H_Optimal = H_Delta / T_Optimal;
            Alpha_Optimal = calculateEndingGlobalCoordinates(
                    Position_Start, Alpha_Start, getCurrentDistance(1), getCurrentElevation(1), getCurrentSpeed(1)).getCourseInDegrees();
        }

        if(Math.abs(H_Optimal) > characteristics.getMaxElevation()) {
            T_Optimal = Math.abs(H_Delta) / characteristics.getMaxElevation();
            A_Optimal = V_Delta / T_Optimal;
            H_Optimal = H_Delta / T_Optimal;
            Alpha_Optimal = calculateEndingGlobalCoordinates(
                    Position_Start, Alpha_Start, getCurrentDistance(1), getCurrentElevation(1), getCurrentSpeed(1)).getCourseInDegrees();
        }
    }

    private double getCurrentSpeed(int t) {
        return V_Start + (t * A_Optimal);
    }

    private double getCurrentElevation(int t) {
        return H_Start + (t * H_Optimal);
    }

    private double getCurrentDistance(int t) {
        return (getCurrentSpeed(t) + getCurrentSpeed(t-1)) / 2;
    }
}