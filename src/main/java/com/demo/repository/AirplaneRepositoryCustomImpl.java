package com.demo.repository;

import com.demo.model.Airplane;
import org.springframework.stereotype.Component;

@Component
public class AirplaneRepositoryCustomImpl implements AirplaneRepositoryCustom {

    final AirplaneRepository airplaneRepository;
    final AirplaneCharacteristicsRepository airplaneCharacteristicsRepository;
    final FlightRepositoryCustom flightRepository;
    final TemporaryPointRepository temporaryPointRepository;

    public AirplaneRepositoryCustomImpl(AirplaneRepository airplaneRepository, AirplaneCharacteristicsRepository airplaneCharacteristicsRepository,
                                        FlightRepositoryCustom flightRepository, TemporaryPointRepository temporaryPointRepository) {
        this.airplaneRepository = airplaneRepository;
        this.airplaneCharacteristicsRepository = airplaneCharacteristicsRepository;
        this.flightRepository = flightRepository;
        this.temporaryPointRepository = temporaryPointRepository;
    }

    @Override
    public void saveAirplane(Airplane airplane, boolean isTemporaryMode) {
        if( ! isTemporaryMode ){
            System.out.println(airplane.toString());
            airplaneCharacteristicsRepository.save(airplane.getAirplaneCharacteristics());
        }
        System.out.println(airplane.getPosition().toString());
        temporaryPointRepository.save(airplane.getPosition());
        flightRepository.saveFlight(airplane.getFlight(), isTemporaryMode);

        airplaneRepository.save(airplane);
    }
}
