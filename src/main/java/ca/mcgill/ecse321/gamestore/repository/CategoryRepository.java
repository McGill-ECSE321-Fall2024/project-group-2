package ca.mcgill.ecse321.gamestore.repository;
import ca.mcgill.ecse321.gamestore.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
    public  Category findCategoryById(int id);
}