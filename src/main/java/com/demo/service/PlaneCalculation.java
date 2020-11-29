package com.demo.service;

import com.demo.model.AirplaneCharacteristics;
import com.demo.model.TemporaryPoint;
import com.demo.model.WayPoint;

import java.util.List;

public interface PlaneCalculation {

    List<TemporaryPoint> calculateRoute(AirplaneCharacteristics characteristics, List<WayPoint> wayPoints);
}
