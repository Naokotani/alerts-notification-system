package com.example.alerts.dto.person_info;

import com.example.alerts.dto.AllergyDto;
import com.example.alerts.dto.MedicationDto;
import lombok.Data;

import java.util.Set;

@Data
public class PersonInfoDto {
    private String name;
    private String address;
    private Integer age;
    private String email;
    private Set<MedicationDto> medications;
    private Set<AllergyDto> allergies;
}
