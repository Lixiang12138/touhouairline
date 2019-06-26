package com.ecust.touhouairline.repository;

import com.ecust.touhouairline.entity.FlightEntity;
import com.ecust.touhouairline.entity.OrderMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface OrderMasterRepository extends JpaRepository<OrderMasterEntity,Integer> {
    Collection<OrderMasterEntity> findAllByFlightByFlightNoAndStateIsNotIn(FlightEntity flightEntity, Collection<String> states);
    List<OrderMasterEntity> findAllByFlightByFlightNo(FlightEntity flightEntity);
}
