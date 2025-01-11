package com.example.alerts.controller;

import com.example.alerts.dto.AddressDto;
import com.example.alerts.dto.fire.FireDto;
import com.example.alerts.dto.fire.FirePersonDto;
import com.example.alerts.exception.ResourceNotFound;
import com.example.alerts.service.FireService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This URL should return the fire station number that services the provided address as well as a list of all
 * the people living at the address. This list should include each person’s name, phone number, age,
 * medications with dosage, and allergies.
 */
@RestController
@RequestMapping("/fire")
public class FireController {
    private final FireService fireService;

    public FireController(FireService fireService) {
            this.fireService = fireService;
    }

    /**
     * Provides a list of the people serviced a fire station and the station id.
     * @param addressDto The address to find the fire station for
     * @return {@link FireDto} with the list of people and the station id.
     * @throws ResourceNotFound If no station is found associated with that address.
     */
    @GetMapping
    public ResponseEntity<FireDto> fire(@ModelAttribute AddressDto addressDto)
            throws ResourceNotFound {
        FireDto fireDto = fireService.getFireList(addressDto);
        return new ResponseEntity<>(fireDto, HttpStatus.OK);
    }

}
