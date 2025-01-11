package com.example.alerts.dto.fire;

import com.example.alerts.dto.AllergyDto;
import com.example.alerts.dto.MedicationDto;
import lombok.Data;

import java.util.List;

@Data
public class FirePersonDto {
    private String name;
    private String phoneNumber;
    private int age;
    private List<MedicationDto> medications;
    private List<AllergyDto> allergies;
}
