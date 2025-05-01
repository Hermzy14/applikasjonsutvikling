package no.ntnu.iir.idata.gr9.backend.service;

import java.io.IOException;
import java.util.Optional;
import no.ntnu.iir.idata.gr9.backend.entity.User;
import no.ntnu.iir.idata.gr9.backend.repository.UserRepository;
import no.ntnu.iir.idata.gr9.backend.security.AccessUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

/**
 * Provides user details for authentication and authorization.
 */
@Service
public class AccessUserService implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;

  /**
   * Loads user details by username.
   *
   * @param username the username of the user
   * @return UserDetails object containing user information
   * @throws UsernameNotFoundException if the user is not found
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = this.userRepository.findByUsername(username);
    if (user.isPresent()) {
      return new AccessUserDetails(user.get());
    } else {
      throw new UsernameNotFoundException("User " + username + "not found");
    }
  }

  /**
   * Get the user which is authenticated for the current session.
   *
   * @return the authenticated user or {@code null} if no user is authenticated
   */
  public User getSessionUser() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    Authentication authentication = securityContext.getAuthentication();
    String username = authentication.getName();
    return userRepository.findByUsername(username).orElse(null);
  }

  /**
   * Check if user with given username exists in the database.
   *
   * @param username Username of the user to check, case-sensitive
   * @return True if user exists, false otherwise
   */
  private boolean userExists(String username) {
    try {
      loadUserByUsername(username);
      return true;
    } catch (UsernameNotFoundException ex) {
      return false;
    }
  }

  /**
   * Try to create a new user.
   *
   * @param username Username of the new user
   * @param password Plaintext password of the new user
   * @throws IOException when creation of the user failed
   */
  public void createNewUser(String username, String password) throws IOException {
    String errorMessage;
    if ("".equals(username)) {
      errorMessage = "Username can't be empty";
    } else if (userExists(username)) {
      errorMessage = "Username already taken";
    } else {
      errorMessage = this.checkPasswordRequirements(password);
      if (errorMessage == null) {
        this.createUser(username, password);
      }
    }
    if (errorMessage != null) {
      throw new IOException(errorMessage);
    }
  }

  /**
   * Check if the password meets the requirements.
   *
   * @param password Password to check
   * @return Error message if requirements are not met, null otherwise
   */
  private String checkPasswordRequirements(String password) {
    String errorMessage = null;
    if (password == null || password.length() == 0) {
      errorMessage = "Password can't be empty";
    }
    return errorMessage;
  }

  /**
   * Create a new user in the database.
   *
   * @param username Username of the new user
   * @param password Plaintext password of the new user
   */
  private void createUser(String username, String password) {
    User user = new User();
    user.setUsername(username);
    user.setPassword(this.createHash(password));
    this.userRepository.save(user);
  }

  /**
   * Create a secure hash of a password.
   *
   * @param password Plaintext password
   * @return BCrypt hash, with random salt
   */
  private String createHash(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }
}
