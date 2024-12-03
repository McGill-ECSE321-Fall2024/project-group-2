package ca.mcgill.ecse321.gamestore.repository;
import ca.mcgill.ecse321.gamestore.model.Order;

import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    public  Order findOrderById(int id);
    public  Order findOrderByNumber(int number);
}