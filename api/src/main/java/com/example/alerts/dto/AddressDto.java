package com.example.alerts.dto;

import lombok.Data;

@Data
public class AddressDto {
    private String streetNumber;
    private String street;
    private String city;
    private String province;
    private String postalCode;
}
