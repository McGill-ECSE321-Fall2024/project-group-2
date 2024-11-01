package ca.mcgill.ecse321.gamestore.repository;

import ca.mcgill.ecse321.gamestore.model.LineItem;
import org.springframework.data.repository.CrudRepository;

public interface LineItemRepository extends CrudRepository<LineItem, Integer> {
    public LineItem findLineItemById(int id);
}
