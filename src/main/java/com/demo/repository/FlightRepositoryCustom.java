package com.demo.repository;

import com.demo.model.Flight;

public interface FlightRepositoryCustom {

    void saveFlight(Flight flight, boolean isTemporaryMode);
    void printPatsFlights();
}
