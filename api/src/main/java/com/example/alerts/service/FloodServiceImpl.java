package com.example.alerts.service;

import com.example.alerts.dto.flood.DisasterDto;
import com.example.alerts.exception.ResourceNotFound;
import com.example.alerts.mapper.FloodMapper;
import com.example.alerts.model.Address;
import com.example.alerts.model.Person;
import com.example.alerts.repository.AddressRepository;
import com.example.alerts.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FloodServiceImpl implements FloodService {
    private final PersonRepository personRepository;
    private final AddressRepository addressRepository;
    private final FloodMapper floodMapper;

    public FloodServiceImpl(PersonRepository personRepository,
                            AddressRepository addressRepository,
                            FloodMapper floodMapper) {
        this.personRepository = personRepository;
        this.addressRepository = addressRepository;
        this.floodMapper = floodMapper;
    }

    private List<Address> findAddresses(List<Long> stationIds) throws ResourceNotFound {
        List<Address> addresses = stationIds.stream()
                .flatMap(s-> addressRepository.findAddressByStation(s).stream()).toList();

        if (addresses.isEmpty()) {
            throw new ResourceNotFound("No addresses not found");
        }
        return addresses;
    }

    @Override
    public List<DisasterDto> getFloodHouseholds(List<Long> stationIds) {
        List<Address> addresses = findAddresses(stationIds);
        return addresses.stream()
                .map(address -> {
                    List<Person> people = personRepository.findPersonByAddress(address);
                    return floodMapper.peopleToFloodDto(
                            address,
                            people.stream()
                                    .map(floodMapper::persontoFloodPersonDto)
                                    .collect(Collectors.toList())
                    );
                }).toList();
    }
}
