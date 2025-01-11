package com.example.alerts.service;

import com.example.alerts.dto.AddressDto;
import com.example.alerts.dto.fire.FireDto;
import com.example.alerts.dto.fire.FirePersonDto;

import java.util.List;

public interface FireService {
    FireDto getFireList(AddressDto addressDto);
}
