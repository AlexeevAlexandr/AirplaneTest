package com.demo.repository;

import com.demo.model.AirplaneCharacteristics;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirplaneCharacteristicsRepository extends MongoRepository<AirplaneCharacteristics, Long> {
}
