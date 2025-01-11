package com.example.alerts.dto.fire;

import lombok.Data;

import java.util.List;

@Data
public class FireDto {
    List<FirePersonDto> people;
    Long stationId;
}
