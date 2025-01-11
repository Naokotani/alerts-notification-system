package com.example.alerts.mapper;

import com.example.alerts.dto.PhoneDto;
import com.example.alerts.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PhoneAlertMapper {
    @Mapping(target="fullName", source=".")
    @Mapping(source="person.phone", target="phoneNumber")
    PhoneDto personToPhoneAlertDto(Person person);

    default String getFullName(Person p) {
        return FormatString.formatName(p);
    }
}
