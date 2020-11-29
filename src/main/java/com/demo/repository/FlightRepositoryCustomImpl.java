package com.demo.repository;

import com.demo.model.Flight;
import org.springframework.stereotype.Component;

@Component
public class FlightRepositoryCustomImpl implements FlightRepositoryCustom {

    final FlightRepository flightRepository;
    final WayPointRepository wayPointRepository;
    final TemporaryPointRepository temporaryPointRepository;

    public FlightRepositoryCustomImpl(FlightRepository flightRepository, WayPointRepository wayPointRepository, TemporaryPointRepository temporaryPointRepository) {
        this.flightRepository = flightRepository;
        this.wayPointRepository = wayPointRepository;
        this.temporaryPointRepository = temporaryPointRepository;
    }

    @Override
    public void saveFlight(Flight flight, boolean isTemporaryMode) {
        if( ! isTemporaryMode ){
            wayPointRepository.saveAll(flight.getWayPoints());
        }
        flightRepository.save(flight);
    }

    @Override
    public void printPatsFlights() {
        System.out.println("\n-------------------------------------------------------------------------------------\n");
        System.out.println("Past flights:");
        flightRepository.findAll().forEach(e -> System.out.println(e.getFlightInfo()));
    }
}
