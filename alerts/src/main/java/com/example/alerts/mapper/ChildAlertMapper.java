package com.example.alerts.mapper;

import com.example.alerts.dto.child_alert.ChildAlertDto;
import com.example.alerts.dto.child_alert.ChildAlertPersonDto;
import com.example.alerts.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ChildAlertMapper {
    ChildAlertPersonDto personToPersonDto(Person person);
    ChildAlertDto personsToChildAlertDto(List<Person> children, List<Person> adults);
}
