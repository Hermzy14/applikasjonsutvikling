package no.ntnu.iir.idata.gr9.backend.repository;

import no.ntnu.iir.idata.gr9.backend.entity.Category;
import no.ntnu.iir.idata.gr9.backend.entity.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Represents a course repository.
 */
@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {
  /**
   * Finds a course by its id.
   */
  Course findById(int id);

  /**
   * Finds a course by its title.
   */
  Course findByTitle(String title);

  /**
   * Finds a course by its category.
   */
  Course findByCategory(Category category);

  /**
   * Finds visible courses.
   */
  Iterable<Course> findByIsVisibleTrue();

  /**
   * Finds not visible courses.
   */
  Iterable<Course> findByIsVisibleFalse();

  /**
   * Finds courses by their keywords.
   */
  Iterable<Course> findByKeywords(String keywords);

  /**
   * Finds courses by their difficulty.
   */
  Iterable<Course> findByDifficulty(String difficulty);

  /**
   * Finds courses by their ECTS.
   */
  Iterable<Course> findByEcts(Double ects);
}
