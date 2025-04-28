package no.ntnu.iir.idata.gr9.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.springframework.http.MediaType;
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
@Tag(name = "Course Management", description = "API endpoints for managing course resources")
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
  @Operation(
      summary = "Get all courses",
      description = "Retrieves a list of all courses in the system."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Courses found and returned",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = Course.class)
          )
      ),
      @ApiResponse(
          responseCode = "404",
          description = "No courses found, empty response",
          content = @Content
      )
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
  @Operation(
      summary = "Get course by ID",
      description = "Retrieves a specific course using its unique identifier."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Course found and returned",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = Course.class)
          )
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Course not found, empty response",
          content = @Content
      )
  })
  public ResponseEntity<Course> getCourse(
      @Parameter(description = "ID of the course to retrieve", required = true)
      @PathVariable int id) {
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
  @Operation(
      summary = "Search for course by name",
      description = "Searches for and retrieves a course by its title."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Course found and returned",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = Course.class)
          )
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Course not found, empty response",
          content = @Content
      )
  })
  public ResponseEntity<Course> getCourseByName(
      @Parameter(description = "Title of the course to search for", required = true)
      @RequestParam String query) {
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
  @Operation(
      summary = "Get courses by category",
      description = "Retrieves all courses belonging to a specific category."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Courses found and returned",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = Course.class)
          )
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Category not found, empty response",
          content = @Content
      )
  })
  public Iterable<Course> getCoursesByCategory(
      @Parameter(description = "ID of the category to filter courses by", required = true)
      @PathVariable int id) {
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
  @Operation(
      summary = "Get visible courses",
      description = "Retrieves all courses that are marked as visible."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Visible courses found and returned",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = Course.class)
          )
      ),
      @ApiResponse(
          responseCode = "404",
          description = "No visible courses found, empty response",
          content = @Content
      )
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
  @Operation(
      summary = "Toggle course visibility",
      description = "Toggles the visibility status of a course. "
          + "Changes visible courses to hidden and vice versa."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Course visibility toggled successfully",
          content = @Content(
              mediaType = "text/plain",
              schema = @Schema(implementation = String.class)
          )
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Course not found, empty response",
          content = @Content
      )
  })
  public ResponseEntity<String> toggleCourse(
      @Parameter(description = "ID of the course to toggle visibility", required = true)
      @PathVariable int id) {
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
  @Operation(
      summary = "Get course image",
      description = "Retrieves the URL of the image associated with a specific course."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Course image found and returned",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(type = "object", example = "{\"imagePath\": \"/course-images/filename.jpg\"}")
          )
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Course not found or no image available, empty response",
          content = @Content
      )
  })
  public ResponseEntity<Map<String, String>> getCourseImage(
      @Parameter(description = "ID of the course to get image for", required = true)
      @PathVariable int id) {
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
  @PostMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Operation(
      summary = "Upload course image",
      description = "Uploads and associates an image with a specific course. "
          + "Replaces any existing image for the course."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Course image uploaded successfully",
          content = @Content(
              mediaType = "text/plain",
              schema = @Schema(implementation = String.class)
          )
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Course not found, empty response",
          content = @Content
      ),
      @ApiResponse(
          responseCode = "500",
          description = "Failed to upload image due to server error",
          content = @Content(
              mediaType = "text/plain",
              schema = @Schema(implementation = String.class)
          )
      )
  })
  public ResponseEntity<String> uploadCourseImage(
      @Parameter(description = "ID of the course to upload image for", required = true)
      @PathVariable int id,
      @Parameter(description = "Image file to upload", required = true)
      @RequestParam("image") MultipartFile image) {
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