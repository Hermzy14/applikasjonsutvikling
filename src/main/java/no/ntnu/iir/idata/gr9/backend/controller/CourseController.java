package no.ntnu.iir.idata.gr9.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import no.ntnu.iir.idata.gr9.backend.entity.Category;
import no.ntnu.iir.idata.gr9.backend.entity.Course;
import no.ntnu.iir.idata.gr9.backend.repository.CategoryRepository;
import no.ntnu.iir.idata.gr9.backend.repository.CourseRepository;
import no.ntnu.iir.idata.gr9.backend.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

/**
 * REST API controller for managing courses.
 */
@RestController
@RequestMapping("/courses")
public class CourseController {
  private final CourseRepository courseRepository;
  private final CategoryRepository categoryRepository;
  private final FileStorageService fileStorageService;
  private static final Logger logger = LoggerFactory.getLogger(CourseController.class);
  private Map<Integer, Course> courses;

  /**
   * Constructor for CourseController.
   *
   * @param courseRepository   the repository for managing courses
   * @param categoryRepository the repository for managing categories
   * @param fileStorageService the service for managing file storage
   */
  public CourseController(CourseRepository courseRepository,
                          CategoryRepository categoryRepository,
                          FileStorageService fileStorageService) {
    this.courseRepository = courseRepository;
    this.categoryRepository = categoryRepository;
    this.fileStorageService = fileStorageService;
  }

  /**
   * Get all courses.
   * <p>
   * Endpoint: {@code GET /courses}.
   *
   * @return a collection of all courses
   */
  @GetMapping
  @Operation(summary = "Get all courses", description = "Returns a list of all courses.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Courses found and returned"),
      @ApiResponse(responseCode = "404", description = "No courses found, empty response",
          content = @Content)
  })
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
  @Operation(summary = "Get course by ID", description = "Returns a course by its ID.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Course found and returned"),
      @ApiResponse(responseCode = "404", description = "Course not found, empty response",
          content = @Content)
  })
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
  @Operation(summary = "Get course by name", description = "Returns a course by its name.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Course found and returned"),
      @ApiResponse(responseCode = "404", description = "Course not found, empty response",
          content = @Content)
  })
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

  /**
   * Get courses by their category.
   * <p>
   * Endpoint: {@code GET /courses/category/{category}}.
   *
   * @param id the category id of the courses to retrieve
   * @return a collection of courses in the specified category
   */
  @GetMapping("/category/{id}")
  @Operation(summary = "Get courses by category", description = "Returns courses by their category.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Courses found and returned"),
      @ApiResponse(responseCode = "404", description = "Category not found, empty response",
          content = @Content)
  })
  public Iterable<Course> getCoursesByCategory(@PathVariable int id) {
    logger.info("Getting courses in category with ID: {}", id);
    Category category = this.categoryRepository.findById(id);
    if (category != null) {
      return this.courseRepository.findByCategory(category);
    } else {
      logger.error("Category with ID {} not found", id);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "Category with ID " + id + " not found");
    }
  }

  /**
   * Get courses by their visibility status.
   * <p>
   * Endpoint: {@code GET /courses/visible}.
   *
   * @return a collection of courses with the specified visibility status
   */
  @GetMapping("/visible")
  @Operation(summary = "Get visible courses", description = "Returns courses that are visible.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Visible courses found and returned"),
      @ApiResponse(responseCode = "404", description = "No visible courses found, empty response",
          content = @Content)
  })
  public Iterable<Course> getCoursesByVisibility() {
    logger.info("Getting courses that are visible");
    return this.courseRepository.findByIsVisibleTrue();
  }

  /**
   * Toggle visibility for a course.
   * <p>
   * Endpoint: {@code PATCH /courses/toggle_visibility/{id}}.
   * TODO: Should only be available for admins!
   *
   * @param id the ID of the course to toggle visibility
   * @return a response entity indicating the result of the operation
   */
  @PatchMapping("/toggle_visibility/{id}")
  @Operation(summary = "Toggle course visibility", description = "Toggles the visibility of a course.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Course visibility toggled"),
      @ApiResponse(responseCode = "404", description = "Course not found, empty response",
          content = @Content)
  })
  public ResponseEntity<String> toggleCourse(@PathVariable int id) {
    logger.info("Toggling visibility for course with ID: {}", id);
    Course course = this.courseRepository.findById(id);
    if (course != null) {
      course.setIsVisible(!course.getIsVisible());
      this.courseRepository.save(course);
      return ResponseEntity.ok("Course visibility toggled.");
    } else {
      logger.error("Course with ID {} not found", id);
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Get course images.
   * <p>
   * Endpoint: {@code GET /courses/{id}/image}.
   *
   * @param id the ID of the course to retrieve the image for
   * @return the image URL of the course, or a 404 error if not found
   */
  @GetMapping("/{id}/image")
  @Operation(summary = "Get course image", description = "Returns the image URL of a course.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Course image found and returned"),
      @ApiResponse(responseCode = "404", description = "Course not found, empty response",
          content = @Content)
  })
  public ResponseEntity<Map<String, String>> getCourseImage(@PathVariable int id) {
    Course course = this.courseRepository.findById(id);
    if (course == null || course.getImagePath() == null) {
      return ResponseEntity.notFound().build();
    }

    Map<String, String> response = new HashMap<>();
    response.put("imagePath", "/course-images/" + course.getImagePath());

    return ResponseEntity.ok(response);
  }

  /**
   * Upload a course image.
   * <p>
   * Endpoint: {@code POST /courses/{id}/image}.
   *
   * @param id    the ID of the course to upload the image for
   * @param image the image file to upload
   * @return a response entity indicating the result of the operation
   */
  @PostMapping("/{id}/image")
  @Operation(summary = "Upload course image", description = "Uploads an image for a course.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Course image uploaded successfully"),
      @ApiResponse(responseCode = "404", description = "Course not found, empty response",
          content = @Content),
      @ApiResponse(responseCode = "500", description = "Failed to upload image",
          content = @Content)
  })
  public ResponseEntity<String> uploadCourseImage(@PathVariable int id, @RequestParam("image")
  MultipartFile image) {
    try {
      Course course = this.courseRepository.findById(id);
      if (course == null) {
        return ResponseEntity.notFound().build();
      }
      // Delete the old image if it exists
      if (course.getImagePath() != null) {
        this.fileStorageService.deleteFile(course.getImagePath());
      }

      // Upload the new image
      String fileName = this.fileStorageService.storeFile(image);
      course.setImagePath(fileName);
      this.courseRepository.save(course);

      return ResponseEntity.ok("Image uploaded successfully.");
    } catch (IOException e) {
      logger.error("Error uploading image for course with ID {}: {}", id, e.getMessage());
      return ResponseEntity.internalServerError().body("Failed to upload image: " + e.getMessage());
    }
  }
}
