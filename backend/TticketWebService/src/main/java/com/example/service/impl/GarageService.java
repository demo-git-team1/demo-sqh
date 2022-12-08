package com.example.service.impl;

import com.example.model.Garage;
import com.example.repository.IGarageRepository;
import com.example.service.IGarageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GarageService implements IGarageService {
    @Autowired
    private IGarageRepository garageRepository;

    @Override
    public List<Garage> findAll() {
        return garageRepository.findAll();
    }
}
