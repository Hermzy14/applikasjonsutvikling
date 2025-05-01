package no.ntnu.iir.idata.gr9.backend.security;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import no.ntnu.iir.idata.gr9.backend.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Contains authentication information for the user.
 */
public class AccessUserDetails implements UserDetails {
  private final String username;
  private final String password;
  private final boolean isActive;
  private final List<GrantedAuthority> authorities = new LinkedList<>();

  /**
   * Create a new user details access object.
   *
   * @param user the user object to create the details from
   */
  public AccessUserDetails(User user) {
    this.username = user.getUsername();
    this.password = user.getPassword();
    this.isActive = user.isActive();
    this.convertRoles(user.getIsAdmin());
  }

  /**
   * Convert the roles of the user to authorities.
   */
  private void convertRoles(boolean isAdmin) {
    if (isAdmin) {
      this.authorities.add(new SimpleGrantedAuthority("ADMIN"));
    } else {
      this.authorities.add(new SimpleGrantedAuthority("USER"));
    }
  }

  /**
   * Returns the authorities of the user.
   *
   * @return the authorities of the user
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  /**
   * Returns the password of the user.
   *
   * @return the password of the user
   */
  @Override
  public String getPassword() {
    return this.password;
  }

  /**
   * Returns the username of the user.
   *
   * @return the username of the user
   */
  @Override
  public String getUsername() {
    return this.username;
  }

  /**
   * Returns if the user is account non expired.
   *
   * @return if the user is account non expired
   */
  @Override
  public boolean isAccountNonExpired() {
    return this.isActive;
  }

  /**
   * Returns if the user is account non-locked.
   *
   * @return if the user is account non-locked
   */
  @Override
  public boolean isAccountNonLocked() {
    return this.isActive;
  }

  /**
   * Returns if the user is credentials non expired.
   *
   * @return if the user is credentials non expired
   */
  @Override
  public boolean isCredentialsNonExpired() {
    return this.isActive;
  }

  /**
   * Returns if the user is enabled.
   *
   * @return if the user is enabled
   */
  @Override
  public boolean isEnabled() {
    return true;
  }
}
