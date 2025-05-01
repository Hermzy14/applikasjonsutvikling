package no.ntnu.iir.idata.gr9.backend.dto;

/**
 * Data that the server will send in the login response when authentication is successful.
 */
public class AuthenticationResponse {
  private final String jwt;

  /**
   * Creates a new authentication response.
   *
   * @param jwt the JWT token to be sent to the client
   */
  public AuthenticationResponse(String jwt) {
    this.jwt = jwt;
  }

  /**
   * Gets the JWT token.
   *
   * @return the JWT token
   */
  public String getJwt() {
    return this.jwt;
  }
}
