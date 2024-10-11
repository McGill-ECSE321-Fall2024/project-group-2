package ca.mcgill.ecse321.gamestore.repository;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.gamestore.model.Inventory;
import java.util.List;

public interface InventoryRepository extends CrudRepository<Inventory, Integer> {

    Inventory findInventoryById(int id);

    List<Inventory> findInventoriesByNumberOfItems(int numberOfItems);

    List<Inventory> findInventoriesByChangeRequestIsNotNull();
}
