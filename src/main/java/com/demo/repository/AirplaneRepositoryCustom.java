package com.demo.repository;

import com.demo.model.Airplane;

public interface AirplaneRepositoryCustom {

    void saveAirplane(Airplane airplane, boolean isTemporaryMode);

}
