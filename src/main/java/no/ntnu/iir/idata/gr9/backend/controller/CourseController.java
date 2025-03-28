package no.ntnu.iir.idata.gr9.backend.controller;

import java.time.Clock;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import no.ntnu.iir.idata.gr9.backend.entity.Course;
import no.ntnu.iir.idata.gr9.backend.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for managing courses.
 */
@RestController
@RequestMapping("/courses")
public class CourseController {
  private final CourseRepository courseRepository;
  private static final Logger logger = LoggerFactory.getLogger(CourseController.class);
  private Map<Integer, Course> courses;

  /**
   * Constructor for CourseController.
   *
   * @param courseRepository the repository for managing courses
   */
  public CourseController(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  /**
   * Get all courses.
   * <p>
   * Endpoint: {@code GET /courses}.
   *
   * @return a collection of all courses
   */
  @GetMapping
  public Iterable<Course> getCourses() {
    logger.info("Getting all courses");
    return this.courseRepository.findAll();
  }

  /**
   * Get a specific course by ID.
   * <p>
   * Endpoint: {@code GET /courses/{id}}.
   *
   * @param id the ID of the course to retrieve
   * @return the course with the specified ID, or a 404 error if not found
   */
  @GetMapping("/{id}")
  public ResponseEntity<Course> getCourse(@PathVariable int id) {
    logger.info("Getting course with ID: {}", id);
    Course course = this.courseRepository.findById(id);
    if (course != null) {
      return ResponseEntity.ok(course);
    } else {
      logger.error("Course with ID {} not found", id);
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Get a specific course by searching for its name.
   * <p>
   * Endpoint: {@code GET /courses/search?query=}.
   *
   * @param query the name of the course to search for
   * @return the course with the specified name, or a 404 error if not found
   */
  @GetMapping("/search")
  public ResponseEntity<Course> getCourseByName(@RequestParam String query) {
    logger.info("Searching for course with name: {}", query);
    Course course = this.courseRepository.findByTitle(query);
    if (course != null) {
      return ResponseEntity.ok(course);
    } else {
      logger.error("Course with name {} not found", query);
      return ResponseEntity.notFound().build();
    }
  }
}
