package ca.mcgill.ecse321.gamestore.repository;
import ca.mcgill.ecse321.gamestore.model.Product;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer>{
    public  Product findProductById(int id);
    public  Product findProductByName(String name);

    @Query("SELECT s FROM Product s WHERE s.category.categoryId= ?1")
    public  Collection<Product> findAllByCategoryId(int id);

}