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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = AlertsApplication.class)
@AutoConfigureMockMvc
public class StationControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    FireStationRepository fireStationRepository;
    @Autowired
    AddressRepository adddressRepository;

    Person person = new Person();
    Address address = new Address();
    Medication medication = new Medication();
    Allergy allergy = new Allergy();
    FireStation fireStation = new FireStation();

    /**
     * Creates all the entities needed for the test
     */
    public StationControllerTest() {
        fireStation.setCommunity("Post Hastings");

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

        // Set personal info
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setEmail("john.doe@gmail.com");
        person.setPhone("123456789");
        person.setAge(21);
    }

    /**
     * This method runs before each test. It deletes teh db and then saves the person.
     * This way the db is always in a state read for use before the test/s run.
     * @throws IllegalAccessError It sure does, idk why it would, but it does!
     */
    @BeforeEach
    void setup() throws IllegalAccessError {
        personRepository.deleteAll();
        adddressRepository.deleteAll();
        fireStationRepository.deleteAll();
        Address savedAddress = adddressRepository.save(this.address);
        FireStation savedStation = fireStationRepository.save(this.fireStation);
        person.setFireStation(savedStation);
        person.setAddress(savedAddress);
        personRepository.save(person);
    }

    /**
     * Test to make sure the api can return a findAll.
     * @throws Exception Not sure. Maybe if there is an exception in the controller?
     */
    @Test
    void stationControllerTest() throws Exception {
        ResultActions result = mockMvc.perform(get("/firestation/test"));
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

    /**
     * Test the actual api path.
     * @throws Exception throws database exceptions
     */
    @Test
    void stationController() throws Exception {
        List<FireStation> fireStations = fireStationRepository.findAll();
        ResultActions result;
        if(!fireStations.isEmpty()){
            result = mockMvc.perform(get(String.format("/firestation?stationNumber=%s", fireStations.getFirst().getId())));
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.people[0].firstName").value(person.getFirstName()))
                    .andExpect(jsonPath("$.people[0].lastName").value(person.getLastName()))
                    .andExpect(jsonPath("$.people[0].phone").value(person.getPhone()))
                    .andExpect(jsonPath("$.people[0].address").value(FormatString.formatAddress(address)))
                    .andExpect(jsonPath("$.numberOfChildren").value(0))
                    .andExpect(jsonPath("$.numberOfAdults").value(1));
        }
    }
}
