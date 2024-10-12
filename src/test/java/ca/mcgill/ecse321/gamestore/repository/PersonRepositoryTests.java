package ca.mcgill.ecse321.gamestore.repository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.gamestore.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.gamestore.repository.CategoryRepository;
import ca.mcgill.ecse321.gamestore.repository.CategoryRepository;
import ca.mcgill.ecse321.gamestore.model.Order.OrderStatus;

import java.sql.Date;
import ca.mcgill.ecse321.gamestore.model.ChangeRequest.RequestStatus;
@SpringBootTest
class PersonRepositoryApplicationTests {



    @Autowired
    private PersonRepository personRepository;




    @BeforeEach
    @AfterEach
    public void clearDatabase() {

        personRepository.deleteAll();




    }

    @Test
    public void testPersistAndLoadPerson(){
         Person person= new Person(null,"123","Moe","m@mail.com","123");
          personRepository.save(person);
          String email = person.getEmail();
          Person personFromDb = personRepository.findPersonByEmail(email);




        // Read Category from database
          assertNotNull(personFromDb);

         assertEquals(personFromDb.getUserID(),person.getUserID());
         assertEquals(personFromDb.getName(),person.getName());
         assertEquals(personFromDb.getEmail(),person.getEmail());
          assertEquals(personFromDb.getPassword(),person.getPassword());

    }




}