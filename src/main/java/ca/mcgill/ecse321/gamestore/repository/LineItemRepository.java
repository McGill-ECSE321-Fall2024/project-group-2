package ca.mcgill.ecse321.gamestore.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.gamestore.model.LineItem;

public interface LineItemRepository extends CrudRepository<LineItem, Integer> {
    public LineItem findLineItemById(int id);
}