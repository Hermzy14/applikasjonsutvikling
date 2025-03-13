package no.ntnu.iir.idata.gr9.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Represents a user of the system.
 * <p>The user can be either an admin or a "regular" logged in user.</p>
 */
@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String username;
  private String email;
  private String password;
  private boolean isAdmin;
  // TODO: Add a field to the users favorite courses.

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
   * Sets the user's id. TODO: The id must be unique and a positive integer.
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
}
