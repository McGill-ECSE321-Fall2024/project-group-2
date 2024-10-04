package ca.mcgill.ecse321.gamestore.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.gamestore.model.Owner;

public interface OwnerRepository extends CrudRepository<Owner, Integer> {
    public Owner findOwnerById(int id);
}