package com.example.alerts.controller;

import com.example.alerts.dto.flood.DisasterDto;
import com.example.alerts.service.FloodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This should return a list of all the households in each fire station’s
 * jurisdiction. This list needs to group people by household address, including
 * name, phone number, and age of each person, and any medications (with dosages)
 * and allergies beside each person’s name.
*/

@RestController
@RequestMapping("/flood")
public class FloodController {
    private final FloodService floodService;
    public FloodController(FloodService floodService) {
        this.floodService = floodService;
    }

    @GetMapping
    public ResponseEntity<List<DisasterDto>> getFloodHouseholds(@RequestParam List<Long> stations) {
        List<DisasterDto> disasterDto = floodService.getFloodHouseholds(stations);
        return new ResponseEntity<>(disasterDto, HttpStatus.OK);
    }
}
