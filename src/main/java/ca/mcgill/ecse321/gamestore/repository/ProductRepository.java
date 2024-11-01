package ca.mcgill.ecse321.gamestore.repository;
import ca.mcgill.ecse321.gamestore.model.Product;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    public  Product findProductById(int id);

    @Query("SELECT s FROM Product s WHERE s.category= ?1")
    public  Iterable<Product> findAllByCategoryId(int id);
}