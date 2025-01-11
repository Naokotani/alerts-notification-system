package com.example.alerts.service;

import com.example.alerts.dto.AddressDto;
import com.example.alerts.dto.child_alert.ChildAlertDto;
import com.example.alerts.exception.ResourceNotFound;
import com.example.alerts.mapper.ChildAlertMapper;
import com.example.alerts.model.Address;
import com.example.alerts.model.Person;
import com.example.alerts.repository.AddressRepository;
import com.example.alerts.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChildAlertServiceImpl implements ChildAlertService {
    private final AddressRepository addressRepository;
    private final PersonRepository personRepository;
    private final ChildAlertMapper childAlertMapper;

    public ChildAlertServiceImpl(AddressRepository addressRepository,
                                 PersonRepository personRepository,
                                 ChildAlertMapper childAlertMapper) {
        this.addressRepository = addressRepository;
        this.personRepository = personRepository;
        this.childAlertMapper = childAlertMapper;
    }

    private boolean checkAge(Person p){
        return p.getAge() >= 18;
    }

    @Override
    public ChildAlertDto getChildAlert(AddressDto addressDto) throws ResourceNotFound {

        Address address = addressRepository
                .findAddressBystreetNumberAndStreetAndCityAndProvinceAndPostalCode(
                        addressDto.getStreetNumber(),
                        addressDto.getStreet(),
                        addressDto.getCity(),
                        addressDto.getProvince(),
                        addressDto.getPostalCode())
                .orElseThrow(() -> new ResourceNotFound("Address not found"));

        List<Person> people = personRepository.findPersonByAddress(address);
        return childAlertMapper.personsToChildAlertDto(
                people.stream().filter(p -> !checkAge(p)).toList(),
                people.stream().filter(this::checkAge).toList());
    }
}
