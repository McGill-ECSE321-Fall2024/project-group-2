package ca.mcgill.ecse321.gamestore.repository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.gamestore.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.gamestore.repository.AccountRepository;


import java.sql.Date;

@SpringBootTest
class AccountRepositoryApplicationTests {


    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private CustomerRepository customerRepository;


    @BeforeEach
    @AfterEach

    public void clearDatabase() {
        accountRepository.deleteAll();
        paymentRepository.deleteAll();
        shoppingCartRepository.deleteAll();
        customerRepository.deleteAll();


    }

    @Test
    public void testPersistAndLoadAccount(){


        Customer customer = new Customer(null,"Moe23","Mohamed","m.mashmoushi22@gmail.com","passowrd");
        customerRepository.save(customer);
        ShoppingCart shoppingCart=new ShoppingCart(Date.valueOf("2024-10-08"));
        shoppingCartRepository.save(shoppingCart);
        Payment payment = new Payment(Date.valueOf("2024-10-09"),12.1,"Good");
        paymentRepository.save(payment);
        String billingaddress="312 saint laurent";
        Date date = Date.valueOf("2024-02-09");
        Date date2 = Date.valueOf("2024-02-12");

        boolean isClosed=false;
        Account account=new Account("m.mashmoushi@gmail.com",billingaddress,date,date2,isClosed,customer,payment,shoppingCart);
        accountRepository.save(account);
        String email = account.getEmail();
        Account accountFromDb = accountRepository.findAccountByEmail(email);




        // Read person from database
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







