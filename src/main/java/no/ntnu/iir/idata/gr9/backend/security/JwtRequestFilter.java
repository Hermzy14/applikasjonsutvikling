package no.ntnu.iir.idata.gr9.backend.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * This class is responsible for filtering incoming requests and checking if they contain a valid JWT
 * token.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
  private static final Logger logger =
      LoggerFactory.getLogger(JwtRequestFilter.class.getSimpleName());

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private JwtUtil jwtUtil;

  /**
   * Does the filtering of the request.
   *
   * @param request     the request to filter
   * @param response    the response
   * @param filterChain the filter chain
   * @throws ServletException if an error occurs during the filtering
   * @throws IOException      if an error occurs during the filtering
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain) throws
      ServletException, IOException {
    String jwtToken = this.getJwtToken(request);
    String username = jwtToken != null ? this.getUsernameFrom(jwtToken) : null;

    if (username != null && notAuthenticatedYet()) {
      UserDetails userDetails = this.getUserDetailsFromDatabase(username);
      if (jwtUtil.validateToken(jwtToken, userDetails)) {
        registerUserAsAuthenticated(request, userDetails);
      }
    }

    filterChain.doFilter(request, response);
  }

  /**
   * Gets the JWT token from the request.
   *
   * @param request the request
   * @return the JWT token
   */
  private String getJwtToken(HttpServletRequest request) {
    final String authorizationHeader = request.getHeader("Authorization");
    String jwt = null;
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      jwt = stripBearerPrefixFrom(authorizationHeader);
    }
    return jwt;
  }

  /**
   * Strips the "Bearer " prefix from the authorization header.
   *
   * @param authorizationHeaderValue the authorization header
   * @return the JWT token without the "Bearer " prefix
   */
  private static String stripBearerPrefixFrom(String authorizationHeaderValue) {
    final int numberOfCharsToStrip = "Bearer ".length();
    return authorizationHeaderValue.substring(numberOfCharsToStrip);
  }

  /**
   * Gets the username from the JWT token.
   *
   * @param jwtToken the JWT token
   * @return the username
   */
  private String getUsernameFrom(String jwtToken) {
    String username = null;
    try {
      username = jwtUtil.extractUsername(jwtToken);
    } catch (MalformedJwtException e) {
      logger.warn("Malformed JWT: " + e.getMessage());
    } catch (JwtException e) {
      logger.warn("Error in the JWT token: " + e.getMessage());
    }
    return username;
  }

  /**
   * Checks if the user is not authenticated yet.
   *
   * @return true if the user is not authenticated yet, false otherwise
   */
  private static boolean notAuthenticatedYet() {
    return SecurityContextHolder.getContext().getAuthentication() == null;
  }

  /**
   * Gets the user details from the database.
   *
   * @param username the username
   * @return the user details
   */
  private UserDetails getUserDetailsFromDatabase(String username) {
    UserDetails userDetails = null;
    try {
      userDetails = userDetailsService.loadUserByUsername(username);
    } catch (UsernameNotFoundException e) {
      logger.warn("User " + username + " not found in the database");
    }
    return userDetails;
  }

  /**
   * Registers the user as authenticated.
   *
   * @param request     the request
   * @param userDetails the user details
   */
  private static void registerUserAsAuthenticated(HttpServletRequest request,
                                                  UserDetails userDetails) {
    final UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(
        userDetails, null, userDetails.getAuthorities());
    upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(upat);
  }
}
