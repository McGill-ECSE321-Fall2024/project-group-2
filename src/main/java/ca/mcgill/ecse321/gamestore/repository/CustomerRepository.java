package ca.mcgill.ecse321.gamestore.repository;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.gamestore.model.Customer;
import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    Customer findCustomerById(int id);

    Customer findCustomerByEmail(String email);

    List<Customer> findCustomersByName(String name);

    Customer findCustomerByUserID(String userID);

    List<Customer> findCustomersByWishListIsNotNull();
}

