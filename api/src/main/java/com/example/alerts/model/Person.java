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
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Person {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private int age;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private FireStation fireStation;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Address address;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Allergy> allergy = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Medication> medication = new HashSet<>();

    public String getFullName(){
        return firstName + " " + lastName;
    }
}
