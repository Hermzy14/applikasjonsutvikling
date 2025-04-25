package no.ntnu.iir.idata.gr9.backend.controller;

import no.ntnu.iir.idata.gr9.backend.entity.Course;
import no.ntnu.iir.idata.gr9.backend.entity.User;
import no.ntnu.iir.idata.gr9.backend.entity.FavoriteCourse;
import no.ntnu.iir.idata.gr9.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST API controller for managing users.
 */
@RestController
@RequestMapping("/users")
public class UserController {
private final UserRepository userRepository;
private static final Logger logger = LoggerFactory.getLogger(UserController.class);

/**
 * constructor for UserController.
 *
 * @param userRepository the repository for managing users
 */
public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
}

/**
 * Register a new user.
 * <p>
 * Endpoint: {@code POST /users/register}.
 *
 * @param user the user to register
 */
@PostMapping("/register")
public ResponseEntity<User> registerUser(@RequestBody User user) {
    if (userRepository.existsByUsername(user.getUsername())) {
        logger.error("Username {} already exists", user.getUsername());
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
    if (userRepository.existsByEmail(user.getEmail())) {
        logger.error("Email {} already exists", user.getEmail());
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
    user.setIsAdmin(false);
    userRepository.save(user);
    logger.info("User {} registered successfully", user.getUsername());
    return ResponseEntity.status(HttpStatus.CREATED).body(user);
}

/**
 * Authenticate a user and generate JWT token.
 * <p>
 *     Endpoint: {@code POST /users/authenticate}.
 *
 * @param user the user to authenticate
 */

/**
 * Retrive a user by ID (Admin and User themselves only).
 * <p>
 *     Endpoint: {@code GET /users/{id}}.
 *
 * @param id the ID of the user to retrieve
 */
@GetMapping("/{id}")
public ResponseEntity<User> getUserById(@PathVariable int id) {
    User user = userRepository.findById(id);
    if (user == null) {
        logger.error("User with ID {} not found", id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    logger.info("User with ID {} found", id);
    return ResponseEntity.ok(user);
}

/**
 * Retrieve users favorite courses (authenticated users).
 * <p>
 *     Endpoint: {@code GET /users/favorites}.
 *
 * @param id the ID of the user to retrieve
 */
@GetMapping("/{id}/favorites")
public ResponseEntity<Iterable<FavoriteCourse>> getUserFavorites(@PathVariable int id) {
    User user = userRepository.findById(id);
    if (user == null) {
        logger.error("User with ID {} not found", id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    logger.info("User with ID {} found", id);
    return ResponseEntity.ok(user.getFavorites());
}

/**
 * Add a course to favorites.
 * <p>
 *     Endpoint: {@code POST /users/favorites}.
 *
 * @param id the ID of the user to retrieve
 */
@PostMapping("/{id}/favorites")
public ResponseEntity<FavoriteCourse> addCourseToFavorites(@PathVariable int id, @RequestBody Course course) {
    User user = userRepository.findById(id);
    if (user == null) {
        logger.error("User with ID {} not found", id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    FavoriteCourse favoriteCourse = new FavoriteCourse(user, course);
    user.getFavorites().add(favoriteCourse);
    userRepository.save(user);
    logger.info("Course {} added to favorites for user {}", course.getTitle(), id);
    return ResponseEntity.status(HttpStatus.CREATED).body(favoriteCourse);
}

/**
 * Remove a course from favorites.
 * <p>
 *     Endpoint: {@code DELETE /users/favorites}.
 *
 * @param id the ID of the user to retrieve
 */
@DeleteMapping("/{id}/favorites")
public ResponseEntity<Void> removeCourseFromFavorites(@PathVariable int id, @RequestBody Course course) {
    User user = userRepository.findById(id);
    if (user == null) {
        logger.error("User with ID {} not found", id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    FavoriteCourse favoriteCourse = new FavoriteCourse(user, course);
    if (!user.getFavorites().remove(favoriteCourse)) {
        logger.error("Course {} not found in favorites for user {}", course.getTitle(), id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    userRepository.save(user);
    logger.info("Course {} removed from favorites for user {}", course.getTitle(), id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

}




}
