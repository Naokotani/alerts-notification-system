package com.example.alerts.controller;

import com.example.alerts.dto.fire_station.FireStationDto;
import com.example.alerts.exception.ResourceNotFound;
import com.example.alerts.model.Person;
import com.example.alerts.repository.PersonRepository;
import com.example.alerts.service.FireStationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This URL should return a list of people serviced by the corresponding fire station. So if station number = 1,
 * it should return the people serviced by station number 1. The list of people should include these specific
 * pieces of information: first name, last name, address, and phone number. As well, it should provide a
 * summary of the number of adults in the service area and the number of children (anyone aged 18 or younger).
*/
@RestController
@RequestMapping("/firestation")
public class FireStationController {
    private final FireStationService fireStationService;
    private final PersonRepository personRepository;
    public FireStationController(FireStationService fireStationService, PersonRepository personRepository) {
        this.fireStationService = fireStationService;
        this.personRepository = personRepository;
    }

    /**
     * Test function that returns a list of all people.
     * @return A list of a people in json format and http status 200
     */
    @GetMapping("/test")
    public ResponseEntity<List<Person>> getAllPeople() {
        List<Person> people = personRepository.findAll();
        return new ResponseEntity<>(people, HttpStatus.OK);
    }

    /**
     * Returns the list of people serviced by a fire station.
     * @param stationNumber The id of the fire station to return a list of people for.
     * @return A list of {@link Person}
     */
    @GetMapping
    public ResponseEntity<FireStationDto> getPeopleByFireStation(@RequestParam Long stationNumber)
        throws ResourceNotFound
    {
        FireStationDto fireStationDto = fireStationService.getPeopleByFireStation(stationNumber);
        return new ResponseEntity<>(fireStationDto, HttpStatus.OK);
    }
}
