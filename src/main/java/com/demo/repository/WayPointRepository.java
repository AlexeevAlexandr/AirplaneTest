package com.demo.repository;

import com.demo.model.WayPoint;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WayPointRepository extends MongoRepository<WayPoint, Long> {
}
