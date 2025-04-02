package no.ntnu.iir.idata.gr9.backend.repository;

import no.ntnu.iir.idata.gr9.backend.entity.FavoriteCourse;
import no.ntnu.iir.idata.gr9.backend.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents a favorite repository.
 */

@Repository
public interface FavoriteRepository extends CrudRepository<FavoriteCourse, Long> {
    /**
     * Finds a favorite by its id.
     */
    Order findById(int id);

    /**
     * Finds a favorite by userId.
     */
    List<FavoriteCourse> findByUserId(int userId);

    /**
     * Deletes a favorite by userId and courseId.
     */
    void deleteByUserIdAndCourseId(int userId, int courseId);

}
