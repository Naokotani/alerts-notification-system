package com.example.alerts.service;

import com.example.alerts.dto.AddressDto;
import com.example.alerts.dto.fire.FireDto;
import com.example.alerts.dto.fire.FirePersonDto;
import com.example.alerts.exception.ResourceNotFound;
import com.example.alerts.mapper.FireMapper;
import com.example.alerts.model.Address;
import com.example.alerts.model.Person;
import com.example.alerts.repository.AddressRepository;
import com.example.alerts.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FireServiceImpl implements FireService {
    private final PersonRepository personRepository;
    private final FireMapper fireMapper;
    private final AddressRepository addressRepository;

    public FireServiceImpl(PersonRepository personRepository,
                           FireMapper fireMapper,
                           AddressRepository addressRepository) {
        this.personRepository = personRepository;
        this.fireMapper = fireMapper;
        this.addressRepository = addressRepository;
    }

    private Address findAddress(AddressDto addressDto) throws ResourceNotFound {
                return addressRepository.findAddressBystreetNumberAndStreetAndCityAndProvinceAndPostalCode(
                        addressDto.getStreetNumber(),
                        addressDto.getStreet(),
                        addressDto.getCity(),
                        addressDto.getProvince(),
                        addressDto.getPostalCode())
                .orElseThrow(() -> new ResourceNotFound("Address not found"));
    }

    @Override
    public FireDto getFireList(AddressDto addressDto) {
        Address address = findAddress(addressDto);
        List<Person> people = personRepository.findPersonByAddress(address);
        List<FirePersonDto> peopleDto = people.stream().map(fireMapper::personToFirePersonDto).toList();
        return fireMapper.peopleToFireDto(peopleDto, people.getFirst().getFireStation().getId());
    }
}
