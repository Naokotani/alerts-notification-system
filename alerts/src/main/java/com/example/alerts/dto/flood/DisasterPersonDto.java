package com.example.alerts.dto.flood;

import com.example.alerts.dto.AllergyDto;
import com.example.alerts.dto.MedicationDto;
import lombok.Data;

import java.util.List;

@Data
public class DisasterPersonDto {
    private String fullName;
    private String phoneNumber;
    private int age;
    private List<AllergyDto> allergies;
    private List<MedicationDto> medications;
}
