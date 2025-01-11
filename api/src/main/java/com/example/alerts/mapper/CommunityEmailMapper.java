package com.example.alerts.mapper;

import com.example.alerts.dto.EmailDto;
import com.example.alerts.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommunityEmailMapper {
    EmailDto peopleToCommunityEmail(Person person);
}
