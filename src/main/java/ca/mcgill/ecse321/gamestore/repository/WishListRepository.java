package ca.mcgill.ecse321.gamestore.repository;
import ca.mcgill.ecse321.gamestore.model.WishList;
import org.springframework.data.repository.CrudRepository;

public interface WishListRepository extends CrudRepository<WishList, Integer> {
    public WishList findWishListById(int id);
}