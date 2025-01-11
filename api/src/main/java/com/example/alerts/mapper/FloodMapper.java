package com.example.alerts.mapper;

import com.example.alerts.dto.flood.DisasterDto;
import com.example.alerts.dto.flood.DisasterPersonDto;
import com.example.alerts.model.Address;
import com.example.alerts.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FloodMapper {
    @Mapping(source="person.medication", target="medications")
    @Mapping(source="person.allergy", target="allergies")
    @Mapping(source="person", qualifiedByName = "getFullName", target="fullName")
    @Mapping(source="person.phone", target="phoneNumber")
    DisasterPersonDto persontoFloodPersonDto(Person person);

    @Mapping(source="address", qualifiedByName = "getFullAddress", target="fullAddress")
    @Mapping(source="people", target="household")
    DisasterDto peopleToFloodDto(Address address, List<DisasterPersonDto> people);

    @Named("getFullName")
    default String getFullName(Person p) {
        return FormatString.formatName(p);
    }

    @Named("getFullAddress")
    default String getFullAddress(Address a) {
        return FormatString.formatAddress(a);
    }
}
