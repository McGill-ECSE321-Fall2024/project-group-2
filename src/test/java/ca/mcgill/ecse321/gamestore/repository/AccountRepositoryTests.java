package ca.mcgill.ecse321.gamestore.repository;

//import assertions for expected results
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//import model classes and test framework annotations
import ca.mcgill.ecse321.gamestore.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//import Date used for Date data type
import java.sql.Date;
/**
 * This class contains unit tests for the AccountRepository to verify
 * that accounts can be persisted and loaded correctly from the database.
 */
@SpringBootTest
class AccountRepositoryApplicationTests {

    //Autowired is used to inject dependencies for repositories
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private CustomerRepository customerRepository;
    /**
     * Clears the database before and after each test to ensure a clean state.
     * Deletes all data from the Account, Payment, ShoppingCart, and Customer repositories.
     */
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        accountRepository.deleteAll();
        paymentRepository.deleteAll();
        shoppingCartRepository.deleteAll();
        customerRepository.deleteAll();
    }

    /**
     * Tests that an Account entity can be persisted and retrieved from the database.
     * The test creates a Customer, Payment, and ShoppingCart, associates them with an Account,
     * and verifies that the saved account matches the retrieved account.
     */
    @Test
    public void testPersistAndLoadAccount(){

        // Create and save a Customer entity
        Customer customer = new Customer("Moe23","Mohamed","m.mashmoushi22@gmail.com","passowrd");
        customerRepository.save(customer);

        // Create and save a ShoppingCart entity
        ShoppingCart shoppingCart=new ShoppingCart(Date.valueOf("2024-10-08"));
        shoppingCartRepository.save(shoppingCart);

        // Create and save a Payment entity
        Payment payment = new Payment(Date.valueOf("2024-10-09"),12.1,"Good");
        paymentRepository.save(payment);

        // Create an Account entity with the above customer, payment, and shopping cart
        String billingaddress="312 saint laurent";
        Date date = Date.valueOf("2024-02-09");
        Date date2 = Date.valueOf("2024-02-12");
        boolean isClosed=false;
        Account account=new Account("m.mashmoushi@gmail.com",billingaddress,date,date2,isClosed,customer,payment,shoppingCart);
        accountRepository.save(account);

        // Retrieve the account by email
        String email = account.getEmail();
        Account accountFromDb = accountRepository.findAccountByEmail(email);


        // Verify that the retrieved account is not null and matches the saved account
        assertNotNull(accountFromDb);
        assertEquals(accountFromDb.getEmail(), account.getEmail());
        assertEquals(accountFromDb.getBillingAddress(), billingaddress);
        assertEquals(accountFromDb.getOpenDate(), date);
        assertEquals(accountFromDb.getClosedDate(), date2);
        assertEquals(accountFromDb.isIsClosed(),isClosed);
        assertEquals(accountFromDb.getAccountOwner().getEmail(),customer.getEmail());
        assertEquals(accountFromDb.getPayment().getId(),payment.getId());
        assertEquals(accountFromDb.getCart().getId(),shoppingCart.getId());
    }




}







