package com.example.alerts.controller;

import com.example.alerts.dto.person_info.PersonInfoDto;
import com.example.alerts.dto.person_info.PersonInfoRequest;
import com.example.alerts.exception.ResourceNotFound;
import com.example.alerts.mapper.PersonInfoMapper;
import com.example.alerts.model.Person;
import com.example.alerts.repository.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/personInfo")
public class PersonInfoController {
    private final PersonRepository personRepository;
    private final PersonInfoMapper personInfoMapper;

    public PersonInfoController(PersonRepository personRepository,
                                PersonInfoMapper personInfoMapper) {
        this.personRepository = personRepository;
        this.personInfoMapper = personInfoMapper;
    }

    @GetMapping
    public ResponseEntity<PersonInfoDto> getPersonInfo(@ModelAttribute PersonInfoRequest personInfoRequest)
            throws ResourceNotFound {
        Person person = personRepository.findPersonByFirstNameAndLastName(personInfoRequest.getFirstName(),
                        personInfoRequest.getLastName())
                .orElseThrow(() -> new ResourceNotFound("Person not found"));
        return new ResponseEntity<>(personInfoMapper.personToPersonInfoDto(person), HttpStatus.OK);
    }
}
