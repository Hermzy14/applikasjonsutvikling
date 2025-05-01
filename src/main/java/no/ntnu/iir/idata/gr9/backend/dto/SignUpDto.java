package no.ntnu.iir.idata.gr9.backend.dto;

/**
 * Data transfer object for sign-up requests.
 */
public class SignUpDto {
  private String username;
  private String password;
  private String email;

  /**
   * Default constructor for SignUpDto.
   */
  public SignUpDto() {
  }

  /**
   * Creates a new SignUpDto with the given username, password, and email.
   *
   * @param username the username of the user
   * @param password the password of the user
   * @param email    the email of the user
   */
  public SignUpDto(String username, String password, String email) {
    this.username = username;
    this.password = password;
    this.email = email;
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

  /**
   * Gets the email of the user.
   *
   * @return the email of the user
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets the email of the user.
   *
   * @param email the email of the user
   */
  public void setEmail(String email) {
    this.email = email;
  }
}
