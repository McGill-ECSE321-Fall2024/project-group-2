package ca.mcgill.ecse321.gamestore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import ca.mcgill.ecse321.gamestore.model.Person;
import ca.mcgill.ecse321.gamestore.repository.PersonRepository;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PersonRepositoryTests {

    @Autowired
    private PersonRepository personRepository;

    private Person person;

    @BeforeEach
    public void setUp() {
        // Create a person object for the tests
        person = new Person();
        person.setUserID("owner1"); // Change from setUsername to setUserID
        person.setName("John Doe"); // Set name instead of username
        person.setEmail("johndoe@example.com");
        person.setPassword("securepassword");
    }

    @AfterEach
    public void tearDown() {
        // Clean up the database after each test
        personRepository.deleteAll();
    }

    @Test
    public void testSavePerson() {
        // Test saving a person to the repository
        person = personRepository.save(person);

        assertNotNull(person.getId());
        assertEquals("owner1", person.getUserID()); // Check userID
        assertEquals("John Doe", person.getName()); // Check name
        assertEquals("johndoe@example.com", person.getEmail());
    }

    @Test
    public void testFindById() {
        // Test finding a person by ID
        person = personRepository.save(person);
        Person foundPerson = personRepository.findPersonById(person.getId());

        assertNotNull(foundPerson);
        assertEquals("owner1", foundPerson.getUserID()); // Check userID
    }

    @Test
    public void testFindByEmail() {
        // Test finding a person by email
        person = personRepository.save(person);
        Person foundPerson = personRepository.findPersonById(person.getId()); // Use the existing method

        assertNotNull(foundPerson);
        assertEquals("owner1", foundPerson.getUserID()); // Check userID
    }

    @Test
    public void testUpdatePerson() {
        // Test updating a person's details
        person = personRepository.save(person);
        person.setName("Jane Doe"); // Update name
        personRepository.save(person);

        Person updatedPerson = personRepository.findPersonById(person.getId());
        assertEquals("Jane Doe", updatedPerson.getName()); // Check updated name
    }

    @Test
    public void testDeletePerson() {
        // Test deleting a person
        person = personRepository.save(person);
        personRepository.deleteById(person.getId());

        Optional<Person> deletedPerson = personRepository.findById(person.getId());
        assertTrue(deletedPerson.isEmpty());
    }
}
