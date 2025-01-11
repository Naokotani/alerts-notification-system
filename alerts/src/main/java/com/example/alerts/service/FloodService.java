package com.example.alerts.service;

import com.example.alerts.dto.flood.DisasterDto;

import java.util.List;

public interface FloodService {
    List<DisasterDto> getFloodHouseholds(List<Long> stationIds);
}
