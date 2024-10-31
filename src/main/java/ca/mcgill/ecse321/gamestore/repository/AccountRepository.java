package ca.mcgill.ecse321.gamestore.repository;
import ca.mcgill.ecse321.gamestore.model.Account;

import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, String> {
    public  Account findAccountByEmail(String email);
}