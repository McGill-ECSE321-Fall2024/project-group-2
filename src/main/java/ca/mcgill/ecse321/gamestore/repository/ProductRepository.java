package ca.mcgill.ecse321.gamestore.repository;

import ca.mcgill.ecse321.gamestore.model.Owner;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.gamestore.model.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    public Product findProductById(int id);
}
