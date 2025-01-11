package com.example.alerts.dto.flood;

import lombok.Data;

import java.util.List;

@Data
public class DisasterDto {
    private String fullAddress;
    private List<DisasterPersonDto> household;
}
