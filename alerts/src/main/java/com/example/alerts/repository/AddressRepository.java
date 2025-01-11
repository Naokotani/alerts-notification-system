package com.example.alerts.repository;

import com.example.alerts.model.Address;
import com.example.alerts.model.FireStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findAddressBystreetNumberAndStreetAndCityAndProvinceAndPostalCode(String streetNumber,
                                                                                        String street,
                                                                                        String city,
                                                                                        String province,
                                                                                        String postalCode
    );

    @Query("SELECT a FROM Person p JOIN p.address a WHERE p.fireStation.id = ?1")
    List<Address> findAddressByStation(Long id);
}
