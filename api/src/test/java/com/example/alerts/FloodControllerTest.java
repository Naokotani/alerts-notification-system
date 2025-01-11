package com.example.alerts;

import com.example.alerts.mapper.FormatString;
import com.example.alerts.model.*;
import com.example.alerts.repository.AddressRepository;
import com.example.alerts.repository.FireStationRepository;
import com.example.alerts.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = AlertsApplication.class)
@AutoConfigureMockMvc
public class FloodControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    FireStationRepository fireStationRepository;
    @Autowired
    AddressRepository addressRepository;
    Person person = new Person();
    Person person2 = new Person();
    Address address = new Address();
    Address address2 = new Address();
    Medication medication = new Medication();
    Allergy allergy = new Allergy();
    Medication medication2 = new Medication();
    Allergy allergy2 = new Allergy();
    FireStation fireStation = new FireStation();
    FireStation fireStation2 = new FireStation();

    /**
     * Creates all the entities needed for the test
     */
    public FloodControllerTest() {
        fireStation.setCommunity("Post Hastings");
        fireStation2.setCommunity("Sydney");
        person.setFireStation(fireStation);
        person2.setFireStation(fireStation2);

        // Set allergies for person 1
        allergy.setName("peanuts");
        Set<Allergy> allergies = new HashSet<>();
        allergies.add(allergy);
        person.setAllergy(allergies);

        // Set medications for person 1
        medication.setName("Tylenol");
        medication.setDosage("3 a day");

        Set<Medication> medications = new HashSet<>();
        medications.add(medication);

        person.setMedication(medications);

        // Set allergies for person 2
        allergy2.setName("fish");
        Set<Allergy> allergies2 = new HashSet<>();
        allergies2.add(allergy2);
        person2.setAllergy(allergies2);

        // Set medications for person 2
        medication2.setName("Insulin");
        medication2.setDosage("4 a day");

        Set<Medication> medications2 = new HashSet<>();
        medications2.add(medication2);

        person2.setMedication(medications2);

        // Set Address for person 1
        address.setStreet("Nscc Way.");
        address.setCity("Port Hawksbury");
        address.setProvince("Nova scotia");
        address.setPostalCode("B0B 1X6");
        address.setStreetNumber("387");

        // Set Address for person 2
        address2.setStreet("Code street");
        address2.setCity("Code city");
        address2.setProvince("Nova scotia");
        address2.setPostalCode("B0B 1K6");
        address2.setStreetNumber("337");

        // Set personal info for perosn 2
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setEmail("john.doe@gmail.com");
        person.setPhone("123456789");
        person.setAge(21);
        person.setAddress(address);

        // Set personal info for person 2
        person2.setFirstName("Jane");
        person2.setLastName("Doe");
        person2.setEmail("jane.doe@gmail.com");
        person2.setPhone("123456789");
        person2.setAge(16);
        person2.setAddress(address2);
        person2.setFireStation(fireStation2);
    }

    /**
     * This method runs before each test. It deletes teh db and then saves the person.
     * This way the db is always in a state read for use before the test/s run.
     *
     * @throws IllegalAccessError It sure does, idk why it would, but it does!
     */
    @BeforeEach
    void setup() throws IllegalAccessError {
        personRepository.deleteAll();
        addressRepository.deleteAll();
        fireStationRepository.deleteAll();
        FireStation savedStation = fireStationRepository.save(fireStation);
        Address savedAddress = addressRepository.save(address);
        fireStationRepository.save(fireStation2);
        addressRepository.save(address2);
        person.setAddress(savedAddress);
        person2.setAddress(address2);
        person.setFireStation(savedStation);
        person2.setFireStation(savedStation);
        personRepository.save(person);
        personRepository.save(person2);
    }

    /**
     * This is what actually hits the controller and checks the result
     * It checks for two households with one person.
     * @throws Exception Not sure. Maybe if there is an exception in the controller?
     */
    @Test
    void personControllerTest() throws Exception {
        ResultActions result = mockMvc.perform(get("/flood")
                .param("stations", fireStation.getId().toString())
                .param("stations", fireStation2.getId().toString()));
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].fullAddress")
                        .value(FormatString.formatAddress(address)))
                .andExpect(jsonPath("$[0].household[0].fullName")
                        .value(FormatString.formatName(person)))
                .andExpect(jsonPath("$[0].household[0].phoneNumber").value(person.getPhone()))
                .andExpect(jsonPath("$[0].household[0].age").value(person.getAge()))
                .andExpect(jsonPath("$[0].household[0].allergies[0].name").value(allergy.getName()))
                .andExpect(jsonPath("$[0].household[0].medications[0].name").value(medication.getName()))
                .andExpect(jsonPath("$[0].household[0].medications[0].dosage").value(medication.getDosage()))
                .andExpect(jsonPath("$[1].fullAddress")
                        .value(FormatString.formatAddress(address2)))
                .andExpect(jsonPath("$[1].household[0].fullName")
                        .value(FormatString.formatName(person2)))
                .andExpect(jsonPath("$[1].household[0].phoneNumber").value(person2.getPhone()))
                .andExpect(jsonPath("$[1].household[0].age").value(person2.getAge()))
                .andExpect(jsonPath("$[1].household[0].allergies[0].name").value(allergy2.getName()))
                .andExpect(jsonPath("$[1].household[0].medications[0].name").value(medication2.getName()))
                .andExpect(jsonPath("$[1].household[0].medications[0].dosage").value(medication2.getDosage()));
    }
}