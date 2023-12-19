package com.capgemini.seetbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.seetbooking.model.Office;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Long> {
	// Add custom queries if needed
}
