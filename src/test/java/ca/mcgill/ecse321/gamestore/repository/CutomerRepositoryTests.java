package ca.mcgill.ecse321.gamestore.repository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.gamestore.model.Customer;
import ca.mcgill.ecse321.gamestore.model.Inventory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.gamestore.repository.CategoryRepository;
import ca.mcgill.ecse321.gamestore.repository.CategoryRepository;
import ca.mcgill.ecse321.gamestore.model.ChangeRequest;
import java.sql.Date;
import ca.mcgill.ecse321.gamestore.model.ChangeRequest.RequestStatus;
@SpringBootTest
class CustomerRepositoryApplicationTests {


    @Autowired
    private CustomerRepository customerRepository;


    @BeforeEach
    @AfterEach
    public void clearDatabase() {

        customerRepository.deleteAll();




    }

    @Test
    public void testPersistAndLoadCustomer(){
        Customer customer = new Customer(null,"Moe23","Mohamed","m.mashmoushi22@gmail.com","passowrd");
        customerRepository.save(customer);
        String email = customer.getEmail();
        Customer customerFromDb = customerRepository.findCustomerByEmail(email);




        // Read Category from database
        assertNotNull(customerFromDb);
        assertEquals(customerFromDb.getEmail(),customer.getEmail());
        assertEquals(customerFromDb.getUserID(), customer.getUserID());
        assertEquals(customerFromDb.getName(), customer.getName());
        assertEquals(customerFromDb.getEmail(), customer.getEmail());
        assertEquals(customerFromDb.getPassword(),customer.getPassword());
    }




}