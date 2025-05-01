package no.ntnu.iir.idata.gr9.backend.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a user of the system.
 * <p>The user can be either an admin or a "regular" logged in user.</p>
 */
@Entity
@Schema(description = "Represents a user of the system.")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "The user's unique id.", example = "1")
  private int id;
  @Schema(description = "The user's username.", example = "ola")
  private String username;
  @Schema(description = "The user's email", example = "ola@ntnu.no")
  private String email;
  @Schema(description = "The user's password", example = "hashedpassword")
  private String password;
  @Schema(description = "The user's role, true if admin, false if not", example = "false")
  private boolean isAdmin;
  @OneToMany(mappedBy = "user")
  @Schema(description = "The user's favorite courses.")
  private Set<FavoriteCourse> favorites = new HashSet<>();
  @Schema(description = "The user's active status", example = "true")
  private boolean active = true;

  public User() {
  }

  /**
   * Creates a new user.
   *
   * @param id       the user's id
   *                 <p>Must be unique</p>
   *                 <p>Must be a positive integer</p>
   * @param username the user's username
   * @param email    the user's email
   *                 <p>Must be formatted like an email 'ola@normann.no'</p>
   * @param password the user's password
   *                 <p>Must be a string</p>
   *                 <p>Must be hashed</p>
   * @param isAdmin  the user's role
   *                 <p>{@code true} if amdin, {@code false} if not.</p>
   */
  public User(int id, String username, String email, String password, boolean isAdmin) {
    setId(id);
    setUsername(username);
    setEmail(email);
    setPassword(password);
    setIsAdmin(isAdmin);
  }

  /**
   * Gets the user's id.
   *
   * @return the user's id
   */
  public int getId() {
    return this.id;
  }

  /**
   * Sets the user's id.
   *
   * @param id the user's id
   */
  public void setId(int id) {
    if (id < 0) {
      throw new IllegalArgumentException("The id must be a positive integer");
    }
    this.id = id;
  }

  /**
   * Gets the user's username.
   *
   * @return the user's username
   */
  public String getUsername() {
    return this.username;
  }

  /**
   * Sets the user's username.
   *
   * @param username the user's username
   */
  public void setUsername(String username) {
    if (username == null) {
      throw new IllegalArgumentException("The username must not be null");
    }
    if (username.isEmpty()) {
      throw new IllegalArgumentException("The username must not be empty");
    }
    this.username = username;
  }

  /**
   * Gets the user's email.
   *
   * @return the user's email
   */
  public String getEmail() {
    return this.email;
  }

  /**
   * Sets the user's email. TODO: The email must be formatted like an email
   *
   * @param email the user's email
   */
  public void setEmail(String email) {
    if (email == null) {
      throw new IllegalArgumentException("The email must not be null");
    }
    if (email.isEmpty()) {
      throw new IllegalArgumentException("The email must not be empty");
    }
    if (!email.contains("@")) {
      throw new IllegalArgumentException("The email must be formatted like an email");
    }
    this.email = email;
  }

  /**
   * Gets the user's password.
   *
   * @return the user's password
   */
  public String getPassword() {
    return this.password;
  }

  /**
   * Sets the user's password. TODO: The password must be hashed
   *
   * @param password the user's password
   */
  public void setPassword(String password) {
    if (password == null) {
      throw new IllegalArgumentException("The password must not be null");
    }
    if (password.isEmpty()) {
      throw new IllegalArgumentException("The password must not be empty");
    }
    this.password = password;
  }

  /**
   * Gets the user's role.
   *
   * @return {@code true} if amdin, {@code false} if not
   */
  public boolean getIsAdmin() {
    return this.isAdmin;
  }

  /**
   * Sets the user's role.
   * <p>{@code true} if amdin, {@code false} if not.</p>
   *
   * @param isAdmin the user's role
   */
  public void setIsAdmin(boolean isAdmin) {
    this.isAdmin = isAdmin;
  }

  /**
   * Gets the user's favorite courses.
   */
  public Set<FavoriteCourse> getFavorites() {
    return this.favorites;
  }

  /**
   * Adds a course to the user's favorite courses.
   *
   * @param course the course to add
   */
  public void addFavoriteCourse(Course course) {
    FavoriteCourse favoriteCourse = new FavoriteCourse(this, course);
    this.favorites.add(favoriteCourse);
  }

  /**
   * Returns the user's active status.
   *
   * @return {@code true} if the user is active, {@code false} otherwise
   */
  public boolean isActive() {
    return this.active;
  }
}
