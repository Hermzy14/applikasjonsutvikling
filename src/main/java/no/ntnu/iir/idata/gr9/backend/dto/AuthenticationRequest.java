package no.ntnu.iir.idata.gr9.backend.dto;

/**
 * Data that the user will send in the login request.
 */
public class AuthenticationRequest {
  private String username;
  private String password;

  public AuthenticationRequest() {
  }

  /**
   * Creates an authentication request with the given username and password.
   *
   * @param username the username of the user
   * @param password the hashed password of the user
   */
  public AuthenticationRequest(String username, String password) {
    this.username = username;
    this.password = password;
  }

  /**
   * Gets the username of the user.
   *
   * @return the username of the user
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the username of the user.
   *
   * @param username the username of the user
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Gets the password of the user.
   *
   * @return the password of the user
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the password of the user.
   *
   * @param password the password of the user
   */
  public void setPassword(String password) {
    this.password = password;
  }
}
