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
public class FireStation {
    @Id
    @GeneratedValue
    private Long id;
    private String community;
    @OneToMany
    private Set<Person> people = new HashSet<>();
}
