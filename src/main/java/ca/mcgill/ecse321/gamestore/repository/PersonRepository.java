package ca.mcgill.ecse321.gamestore.repository;

import ca.mcgill.ecse321.gamestore.model.Person;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.gamestore.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
    public Person findPersonById(int id);
}
