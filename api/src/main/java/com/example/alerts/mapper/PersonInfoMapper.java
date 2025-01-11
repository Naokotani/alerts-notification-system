package com.example.alerts.mapper;

import com.example.alerts.dto.person_info.PersonInfoDto;
import com.example.alerts.model.Address;
import com.example.alerts.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonInfoMapper {

    @Mapping(source="person.allergy", target="allergies")
    @Mapping(source="person.medication", target="medications")
    @Mapping(source="person.address", qualifiedByName = "getFullAddress", target="address")
    @Mapping(source="person", qualifiedByName="getFullName", target="name")
    PersonInfoDto personToPersonInfoDto(Person person);

    @Named("getFullName")
    default String getFullName(Person p) {
        return FormatString.formatName(p);
    }

    @Named("getFullAddress")
    default String getFullAddress(Address a) {
        return FormatString.formatAddress(a);
    }
}
