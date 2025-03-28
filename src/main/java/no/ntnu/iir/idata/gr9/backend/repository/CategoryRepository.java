package no.ntnu.iir.idata.gr9.backend.repository;

import no.ntnu.iir.idata.gr9.backend.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Represents a category repository.
 */
@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
  /**
   * Finds a category by its id.
   */
  Category findById(int id);

  /**
   * Finds a category by its name.
   */
  Category findByName(String name);
}
