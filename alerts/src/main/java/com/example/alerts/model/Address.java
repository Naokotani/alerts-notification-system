package com.example.alerts.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address {
    @Id
    @GeneratedValue
    private Long id;
    private String streetNumber;
    private String street;
    private String city;
    private String province;
    private String postalCode;
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Person> person = new HashSet<>();
}
