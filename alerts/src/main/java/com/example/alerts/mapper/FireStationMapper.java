package com.example.alerts.mapper;

import com.example.alerts.dto.fire_station.FireStationDto;
import com.example.alerts.dto.fire_station.FireStationPersonDto;
import com.example.alerts.model.Address;
import com.example.alerts.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FireStationMapper {
    @Mapping(source="person.address", qualifiedByName = "getFullAddress", target="address")
    FireStationPersonDto personToFireStationPersonDto(Person person);
    FireStationDto peopleToFireStationDto(List<FireStationPersonDto> people, long numberOfChildren, long numberOfAdults);

    @Named("getFullAddress")
    default String getFullAddress(Address a) {
        return FormatString.formatAddress(a);
    }
}
