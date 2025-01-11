package com.example.alerts.repository;

import com.example.alerts.model.FireStation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FireStationRepository extends JpaRepository<FireStation, Long> {
}
