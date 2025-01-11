package com.example.alerts.mapper;

import com.example.alerts.model.Address;
import com.example.alerts.model.Person;

public class FormatString {
    public static String formatAddress(Address a) {
        return a.getStreetNumber() + ' ' +
                a.getStreet() + ' ' +
                a.getCity()+ ", " +
                a.getProvince() + ", " +
                a.getPostalCode();
    }

    public static String formatName(Person p) {
        return p.getFirstName() + " " + p.getLastName();
    }
}
