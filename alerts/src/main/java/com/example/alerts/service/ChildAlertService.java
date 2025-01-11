package com.example.alerts.service;

import com.example.alerts.dto.AddressDto;
import com.example.alerts.dto.child_alert.ChildAlertDto;


public interface ChildAlertService {
    ChildAlertDto getChildAlert(AddressDto addressDto);
}
