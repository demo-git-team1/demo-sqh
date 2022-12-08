package com.example.controller;

import com.example.model.Garage;
import com.example.service.IGarageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/garages")
public class GarageRestController {
    @Autowired
    private IGarageService garageService;

    @GetMapping("")
    public ResponseEntity<List<Garage>> findAllGarage() {
        List<Garage> garageList = garageService.findAll();
        return  new ResponseEntity<>(garageList, HttpStatus.OK);
    }
}
