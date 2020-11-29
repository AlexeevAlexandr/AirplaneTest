package com.demo.config;

import com.demo.model.Airplane;
import com.demo.model.TemporaryPoint;
import com.demo.repository.AirplaneRepositoryCustom;
import com.demo.repository.FlightRepositoryCustom;
import com.demo.service.PlaneCalculation;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

import static com.demo.model.Airplane.getTestAirplane;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    private static int numberOfFlights = 1;
    private final PlaneCalculation planeCalculation;
    private final AirplaneRepositoryCustom airplaneRepository;
    private final FlightRepositoryCustom flightRepository;

    public SchedulerConfig(PlaneCalculation planeCalculation, AirplaneRepositoryCustom airplaneRepository, FlightRepositoryCustom flightRepository) {
        this.planeCalculation = planeCalculation;
        this.airplaneRepository = airplaneRepository;
        this.flightRepository = flightRepository;
    }

    @Scheduled(fixedDelay = 3_000)
    public void scheduleFixedDelayTask() {
        if( numberOfFlights <= 3){
            flightRepository.printPatsFlights();
            boolean isTemporaryMode = false;
            Airplane airplane = getTestAirplane();
            List<TemporaryPoint> points = planeCalculation.calculateRoute(airplane.getAirplaneCharacteristics(), airplane.getFlight().getWayPoints());

            for(TemporaryPoint point : points){
                airplane.setPosition(point);
                airplane.getFlight().getPassedPoints().add(point);
                airplaneRepository.saveAirplane(airplane, isTemporaryMode);
                isTemporaryMode = true;
            }
            numberOfFlights++;
        } else {
            System.exit(0);
        }
    }
}
