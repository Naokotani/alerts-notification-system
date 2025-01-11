package com.example.alerts.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class CriticalMedicationDto extends MedicationDto {
    private String prescribed = LocalDateTime.now().toString();
}
