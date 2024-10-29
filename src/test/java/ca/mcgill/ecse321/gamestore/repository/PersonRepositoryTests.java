package ca.mcgill.ecse321.gamestore.repository;

// Import necessary assertions for test validation
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//Import gamestore model
import ca.mcgill.ecse321.gamestore.model.*;

// Import test framework annotations
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * This class contains unit tests for the PersonRepository to ensure that
 * Person entities are correctly persisted and retrieved from the database.
 */
@SpringBootTest
class PersonRepositoryApplicationTests {

    // Autowired dependency for interacting with the Person repository
    @Autowired
    private PersonRepository personRepository;

    /**
     * Clears the database before and after each test to ensure a clean environment.
     * This method deletes all Person entities.
     */
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        personRepository.deleteAll();
    }


    /**
     * Tests that a Person entity can be saved and retrieved from the database.
     * It validates the fields of the retrieved Person against the saved Person.
     */
    @Test
    public void testPersistAndLoadPerson(){
        // Create and save a Person entity
        Person person= new Person(null,"123","Moe","m@mail.com","123");
        personRepository.save(person);

        // Retrieve the Person entity from the repository by its email
        String email = person.getEmail();
        Person personFromDb = personRepository.findPersonByEmail(email);

        // Validate that the retrieved Person is not null and matches the saved Person
         assertNotNull(personFromDb);
         assertEquals(personFromDb.getUserID(),person.getUserID());
         assertEquals(personFromDb.getName(),person.getName());
         assertEquals(personFromDb.getEmail(),person.getEmail());
         assertEquals(personFromDb.getPassword(),person.getPassword());

    }
}