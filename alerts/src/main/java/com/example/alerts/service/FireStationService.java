package com.example.alerts.service;

import com.example.alerts.dto.fire_station.FireStationDto;

public interface FireStationService {
    FireStationDto getPeopleByFireStation(Long stationNumber);
}
