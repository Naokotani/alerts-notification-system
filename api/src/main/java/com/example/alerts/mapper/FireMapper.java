package com.example.alerts.mapper;


import com.example.alerts.dto.fire.FireDto;
import com.example.alerts.dto.fire.FirePersonDto;
import com.example.alerts.model.Address;
import com.example.alerts.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.List;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FireMapper {
    @Mapping(source="person.medication", target="medications")
    @Mapping(source="person.allergy", target="allergies")
    @Mapping(source="person", qualifiedByName="getFullName", target="name")
    @Mapping(source="person.phone", target="phoneNumber")
    FirePersonDto personToFirePersonDto(Person person);

    FireDto peopleToFireDto(List<FirePersonDto> people, Long stationId);

    @Named("getFullName")
    default String getFullName(Person p) {
        return FormatString.formatName(p);
    }
}
