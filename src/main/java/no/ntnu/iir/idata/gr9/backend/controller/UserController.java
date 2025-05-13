package no.ntnu.iir.idata.gr9.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.iir.idata.gr9.backend.entity.Course;
import no.ntnu.iir.idata.gr9.backend.entity.User;
import no.ntnu.iir.idata.gr9.backend.entity.FavoriteCourse;
import no.ntnu.iir.idata.gr9.backend.repository.UserRepository;
import no.ntnu.iir.idata.gr9.backend.dto.AuthenticationRequest;
import no.ntnu.iir.idata.gr9.backend.dto.AuthenticationResponse;
import no.ntnu.iir.idata.gr9.backend.controller.AuthenticationController;
import no.ntnu.iir.idata.gr9.backend.security.JwtUtil;
import no.ntnu.iir.idata.gr9.backend.service.AccessUserService;
import no.ntnu.iir.idata.gr9.backend.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;

/**
 * REST API controller for managing users.
 */
@RestController
@RequestMapping("/users")
@Tag(name = "User Management", description = "API endpoints for managing user accounts and favorites")
public class UserController {
  private final UserRepository userRepository;
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);
  private final AuthenticationManager authenticationManager;
  private final AccessUserService accessUserService;
  @Autowired
  private PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  /**
   * Constructor for UserController.
   *
   * @param userRepository the repository for managing users
   */
  @Autowired
  public UserController(
          UserRepository userRepository,
          AuthenticationManager authenticationManager,
          AccessUserService accessUserService,
          JwtUtil jwtUtil
  ) {
      this.userRepository = userRepository;
      this.authenticationManager = authenticationManager;
      this.accessUserService = accessUserService;
      this.jwtUtil = jwtUtil;
  }

  /**
   * Register a new user.
   * <p>
   * Endpoint: {@code POST /users/register}.
   *
   * @param user the user to register
   * @return the registered user or conflict status
   */
  @PostMapping("/register")
  @Operation(
      summary = "Register a new user",
      description = "Creates a new user account in the system. Checks for username and email uniqueness."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "201",
          description = "User registered successfully",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = User.class)
          )
      ),
      @ApiResponse(
          responseCode = "409",
          description = "Username or email already exists",
          content = @Content
      )
  })
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
    // Hash the password before saving
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
    logger.info("User {} registered successfully", user.getUsername());
    return ResponseEntity.status(HttpStatus.CREATED).body(user);
  }
  /**
   * authenticate a user and generate JWT token.
   * <p>
   * Endpoint: {@code POST /users/login}.
   *
   * @param authenticationRequest the authentication request containing username and password
   */
    @PostMapping("/login")
    @Operation(
        summary = "User login",
        description = "Authenticates a user and generates a JWT token for session management."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "User authenticated successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = String.class)
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Invalid username or password",
            content = @Content
        )
    })
    public ResponseEntity<?> loginUser(
            @RequestBody AuthenticationRequest authenticationRequest
    ) {
      System.out.println("loginUser method was called");
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = accessUserService.loadUserByUsername(
                    authenticationRequest.getUsername()
            );

            String jwt = jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok(new AuthenticationResponse(jwt));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

  /**
   * Retrieve a user by ID (Admin and User themselves only).
   * <p>
   * Endpoint: {@code GET /users/{id}}.
   *
   * @param id the ID of the user to retrieve
   * @return the user with the specified ID or not found status
   */
  @GetMapping("/{id}")
  @Operation(
      summary = "Get user by ID",
      description = "Retrieves a specific user using their unique identifier. " +
          "Restricted to admins and the user themselves."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "User found and returned",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = User.class)
          )
      ),
      @ApiResponse(
          responseCode = "404",
          description = "User not found",
          content = @Content
      )
  })
  public ResponseEntity<User> getUserById(
      @Parameter(description = "ID of the user to retrieve", required = true)
      @PathVariable int id) {
    User user = userRepository.findById(id);
    if (user == null) {
      logger.error("User with ID {} not found", id);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    logger.info("User with ID {} found", id);
    return ResponseEntity.ok(user);
  }

  /**
   * Retrieve user's favorite courses (authenticated users).
   * <p>
   * Endpoint: {@code GET /users/{id}/favorites}.
   *
   * @param id the ID of the user to retrieve favorites for
   * @return the user's favorite courses or not found status
   */
  @GetMapping("/{id}/favorites")
  @Operation(
      summary = "Get user's favorite courses",
      description = "Retrieves all favorite courses for a specific user. " +
          "Requires authentication as the specified user or an admin."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Favorite courses found and returned",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = FavoriteCourse.class)
          )
      ),
      @ApiResponse(
          responseCode = "404",
          description = "User not found",
          content = @Content
      )
  })
  public ResponseEntity<Iterable<FavoriteCourse>> getUserFavorites(
      @Parameter(description = "ID of the user to retrieve favorites for", required = true)
      @PathVariable int id) {
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
   * Endpoint: {@code POST /users/{id}/favorites}.
   *
   * @param id     the ID of the user to add favorite for
   * @param course the course to add to favorites
   * @return the created favorite course relationship or not found status
   */
  @PostMapping("/{id}/favorites")
  @Operation(
      summary = "Add course to favorites",
      description = "Adds a specified course to the user's favorites list. " +
          "Requires authentication as the specified user or an admin."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "201",
          description = "Course added to favorites successfully",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = FavoriteCourse.class)
          )
      ),
      @ApiResponse(
          responseCode = "404",
          description = "User not found",
          content = @Content
      )
  })
  public ResponseEntity<FavoriteCourse> addCourseToFavorites(
      @Parameter(description = "ID of the user to add favorite for", required = true)
      @PathVariable int id,
      @Parameter(description = "Course to add to favorites", required = true,
          schema = @Schema(implementation = Course.class))
      @RequestBody Course course) {
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
   * Endpoint: {@code DELETE /users/{id}/favorites}.
   *
   * @param id     the ID of the user to remove favorite for
   * @param course the course to remove from favorites
   * @return no content response or not found status
   */
  @DeleteMapping("/{id}/favorites")
  @Operation(
      summary = "Remove course from favorites",
      description = "Removes a specified course from the user's favorites list. " +
          "Requires authentication as the specified user or an admin."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "204",
          description = "Course removed from favorites successfully",
          content = @Content
      ),
      @ApiResponse(
          responseCode = "404",
          description = "User not found or course not in favorites",
          content = @Content
      )
  })
  public ResponseEntity<Void> removeCourseFromFavorites(
      @Parameter(description = "ID of the user to remove favorite for", required = true)
      @PathVariable int id,
      @Parameter(description = "Course to remove from favorites", required = true,
          schema = @Schema(implementation = Course.class))
      @RequestBody Course course) {
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