package no.ntnu.iir.idata.gr9.backend.controller;

import java.time.Clock;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import no.ntnu.iir.idata.gr9.backend.entity.Category;
import no.ntnu.iir.idata.gr9.backend.entity.Course;
import no.ntnu.iir.idata.gr9.backend.repository.CategoryRepository;
import no.ntnu.iir.idata.gr9.backend.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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



}
