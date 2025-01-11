package com.example.alerts.dto.fire_station;

import lombok.Data;

import java.util.List;

@Data
public class FireStationDto {
    private List<FireStationPersonDto> people;
    private long numberOfChildren;
    private long numberOfAdults;
}
