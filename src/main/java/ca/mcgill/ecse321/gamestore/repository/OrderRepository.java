package ca.mcgill.ecse321.gamestore.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.gamestore.model.Order;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    public Order findOrderById(int id);
}