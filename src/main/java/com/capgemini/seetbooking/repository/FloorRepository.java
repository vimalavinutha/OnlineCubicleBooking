package com.capgemini.seetbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.seetbooking.model.Floor;

//com.capgemini.seetbooking.repository.FloorRepository.java

@Repository
public interface FloorRepository extends JpaRepository<Floor, Long> {

	boolean existsByFloorNumber(String floorNumber);
}
