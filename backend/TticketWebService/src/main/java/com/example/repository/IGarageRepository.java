package com.example.repository;

import com.example.model.Garage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGarageRepository extends JpaRepository<Garage, Integer> {
}
