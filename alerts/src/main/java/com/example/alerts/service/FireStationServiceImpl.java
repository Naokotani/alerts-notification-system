package com.example.alerts.service;

import com.example.alerts.dto.fire_station.FireStationDto;
import com.example.alerts.dto.fire_station.FireStationPersonDto;
import com.example.alerts.exception.ResourceNotFound;
import com.example.alerts.mapper.FireStationMapper;
import com.example.alerts.model.Person;
import com.example.alerts.repository.FireStationRepository;
import com.example.alerts.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FireStationServiceImpl implements FireStationService {
    private final PersonRepository personRepository;
    private final FireStationMapper fireStationMapper;
    private final FireStationRepository fireStationRepository;
    public FireStationServiceImpl(PersonRepository personRepository,
                                  FireStationMapper fireStationMapper, FireStationRepository fireStationRepository) {
        this.personRepository = personRepository;
        this.fireStationMapper = fireStationMapper;
        this.fireStationRepository = fireStationRepository;
    }

    @Override
    public FireStationDto getPeopleByFireStation(Long stationNumber)
            throws ResourceNotFound
    {
        fireStationRepository.findById(stationNumber)
                .orElseThrow(() -> new ResourceNotFound("No fire station found with that id."));
        List<Person> people = personRepository.findPersonByFireStationId(stationNumber);
        long adults = people.stream().filter(p -> p.getAge() >= 18).count();
        long children = people.stream().filter(p -> p.getAge() < 18).count();
        List<FireStationPersonDto> personDto = people.stream()
                .map(fireStationMapper::personToFireStationPersonDto).toList();
        return fireStationMapper.peopleToFireStationDto(personDto, children, adults);
    }
}