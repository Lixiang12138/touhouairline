package com.ecust.touhouairline.repository;

import com.ecust.touhouairline.entity.PassengerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<PassengerEntity,Integer> {
}
