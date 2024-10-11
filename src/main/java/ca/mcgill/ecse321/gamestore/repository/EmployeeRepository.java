package ca.mcgill.ecse321.gamestore.repository;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.gamestore.model.Employee;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    Employee findEmployeeById(int id);

    Employee findEmployeeByEmail(String email);

    Employee findEmployeeByUserID(String userID);

    List<Employee> findEmployeesByChangeRequestIsNotNull();
}

