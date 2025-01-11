package com.example.alerts;

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
public class ChildAlertControllerTest {
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
    FireStation fireStation = new FireStation();

    /**
     * Creates all the entities needed for the test
     */
    public ChildAlertControllerTest() {
        fireStation.setCommunity("Post Hastings");
        person.setFireStation(fireStation);

        // Set allergies
        allergy.setName("peanuts");
        Set<Allergy> allergies = new HashSet<>();
        allergies.add(allergy);
        person.setAllergy(allergies);

        // Set medications
        medication.setName("Tylenol");
        medication.setDosage("3 a day");

        Set<Medication> medications = new HashSet<>();
        medications.add(medication);

        person.setMedication(medications);

        // Set Address
        address.setStreet("Nscc Way.");
        address.setCity("Port Hawksbury");
        address.setProvince("Nova scotia");
        address.setPostalCode("B0B 1X6");
        address.setStreetNumber("387");

        address2.setStreet("Code street");
        address2.setCity("Code city");
        address2.setProvince("Nova scotia");
        address2.setPostalCode("B0B 1K6");
        address2.setStreetNumber("337");

        // Set personal info
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setEmail("john.doe@gmail.com");
        person.setPhone("123456789");
        person.setAge(21);
        person.setAddress(address);

        person2.setFirstName("Jane");
        person2.setLastName("Doe");
        person2.setEmail("jane.doe@gmail.com");
        person2.setPhone("123456789");
        person2.setAge(16);
        person2.setAddress(address);
        person2.setFireStation(fireStation);
    }

    /**
     * This method runs before each test. It deletes teh db and then saves the person.
     * This way the db is always in a state read for use before the test/s run.
     * @throws IllegalAccessError It sure does, idk why it would, but it does!
     */
    @BeforeEach
    void setup() throws IllegalAccessError {
        personRepository.deleteAll();
        addressRepository.deleteAll();
        fireStationRepository.deleteAll();
        FireStation savedStation = fireStationRepository.save(fireStation);
        Address savedAddress = addressRepository.save(address);
        addressRepository.save(address2);
        person.setAddress(savedAddress);
        person2.setAddress(savedAddress);
        person.setFireStation(savedStation);
        person2.setFireStation(savedStation);
        personRepository.save(person);
        personRepository.save(person2);
    }

    /**
     * This is what actually hits the controller and checks the result
     * @throws Exception Not sure. Maybe if there is an exception in the controller?
     */
    @Test
    void personControllerTest() throws Exception {
        ResultActions result = mockMvc.perform(get("/childAlert/test"));
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                //Personal info
                .andExpect(jsonPath("$[0].firstName").value(person.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(person.getLastName()))
                .andExpect(jsonPath("$[0].email").value(person.getEmail()))
                .andExpect(jsonPath("$[0].phone").value(person.getPhone()))

                // Address
                .andExpect(jsonPath("$[0].address.street").value(address.getStreet()))
                .andExpect(jsonPath("$[0].address.city").value(address.getCity()))
                .andExpect(jsonPath("$[0].address.province").value(address.getProvince()))
                .andExpect(jsonPath("$[0].address.postalCode").value(address.getPostalCode()))
                .andExpect(jsonPath("$[0].address.streetNumber").value(address.getStreetNumber()))

                // Fire Station
                .andExpect(jsonPath("$[0].fireStation.community").value(fireStation.getCommunity()))

                // Medications
                .andExpect(jsonPath("$[0].medication[0].name").value(medication.getName()))
                .andExpect(jsonPath("$[0].medication[0].dosage").value(medication.getDosage()))

                // Allergies
                .andExpect(jsonPath("$[0].allergy[0].name").value(allergy.getName()));
    }

    @Test
    void childAlertController() throws Exception {
        ResultActions result;
        result = mockMvc.perform(MockMvcRequestBuilders.get("/childAlert")
                .param("streetNumber", address.getStreetNumber())
                .param("street", address.getStreet())
                .param("city", address.getCity())
                .param("province", address.getProvince())
                .param("postalCode", address.getPostalCode()));
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.children[0].firstName").value(person2.getFirstName()))
                .andExpect(jsonPath("$.children[0].lastName").value(person2.getLastName()))
                .andExpect(jsonPath("$.children[0].age").value(person2.getAge()))
                .andExpect(jsonPath("$.adults[0].firstName").value(person.getFirstName()))
                .andExpect(jsonPath("$.adults[0].lastName").value(person.getLastName()))
                .andExpect(jsonPath("$.adults[0].age").value(person.getAge()));
    }

    @Test
    void emptyChildAlertController() throws Exception {
        ResultActions result;
        result = mockMvc.perform(MockMvcRequestBuilders.get("/childAlert")
                .param("streetNumber", address2.getStreetNumber())
                .param("street", address2.getStreet())
                .param("city", address2.getCity())
                .param("province", address2.getProvince())
                .param("postalCode", address2.getPostalCode()));
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.children").isEmpty())
                .andExpect(jsonPath("$.adults").isEmpty());
    }

    @Test
    void addressNotFound() throws Exception {
        ResultActions result;
        result = mockMvc.perform(MockMvcRequestBuilders.get("/childAlert")
                .param("streetNumber", address2.getStreetNumber())
                .param("street", "mistake lane")
                .param("city", address2.getCity())
                .param("province", address2.getProvince())
                .param("postalCode", address2.getPostalCode()));
        result.andExpect(status().isNotFound());

    }

    @Test
    void addressNotFoundChildAlertController() throws Exception {
        ResultActions result;
        result = mockMvc.perform(MockMvcRequestBuilders.get("/childAlert")
                .param("streetNumber", "27")
                .param("street", "Radcliff rd.")
                .param("city", "Galifax")
                .param("province", "New Scotland")
                .param("postalCode", "a1a2d3"));
        result.andExpect(status().isNotFound());
    }
}
