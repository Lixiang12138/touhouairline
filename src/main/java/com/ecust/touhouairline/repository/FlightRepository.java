package com.ecust.touhouairline.repository;

import com.ecust.touhouairline.entity.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Collection;

@Repository
public interface FlightRepository extends JpaRepository<FlightEntity,String> {
    Collection<FlightEntity> findAllByDepartTimeBetweenAndDepartPlaceEqualsAndDestinationEquals(
            Timestamp departTime, Timestamp departTimeEnd, String departPlace, String destination);
}