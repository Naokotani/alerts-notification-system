package com.example.alerts.controller;

import com.example.alerts.dto.EmailDto;
import com.example.alerts.mapper.CommunityEmailMapper;
import com.example.alerts.model.Person;
import com.example.alerts.repository.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * This will return the email addresses of all people in the city
 * For all of these endpoints, the results should be JSON, and if there is an address or fire station number not
 * found within our file, it should return an empty JSON object. We’ll also need a set of unit tests that test
 * each of the requirements and validate that the application working correctly. Also, don’t forget about
 * logging. The application should log every request and every response.
 */
@RestController
@RequestMapping("/communityEmail")
public class CommunityEmailController {
    private final PersonRepository personRepository;
    private final CommunityEmailMapper communityEmailMapper;

    public CommunityEmailController(PersonRepository personRepository, CommunityEmailMapper communityEmailMapper) {
        this.personRepository = personRepository;
        this.communityEmailMapper = communityEmailMapper;
    }

    @GetMapping
    public ResponseEntity<List<EmailDto>> getEmailsByCity(@RequestParam String city)
            throws ResponseStatusException
    {
        List<Person> people =  personRepository.findPersonByCity(city);
        List<EmailDto> emailDtos = people.stream()
                .map(communityEmailMapper::peopleToCommunityEmail).toList();
        return new ResponseEntity<>(emailDtos, HttpStatus.OK);
    }

}
