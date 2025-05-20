package no.ntnu.iir.idata.gr9.backend.config;

import no.ntnu.iir.idata.gr9.backend.security.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Creates AuthenticationManager - set up authentication type.
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {
  @Autowired
  private UserDetailsService userDetailsService;
  @Autowired
  private JwtRequestFilter jwtRequestFilter;

  /**
   * This method will be called automatically by the framework to find the authentication to use.
   * Here we tell that we want to load users from a database
   *
   * @param auth Authentication builder
   * @throws Exception When user service is not found
   */
  @Autowired
  protected void configureAuthentication(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(this.userDetailsService);
  }

  /**
   * This method will be called automatically by the framework to find the authentication to use.
   *
   * @param http HttpSecurity setting builder
   * @throws Exception When security configuration fails
   */
  @Bean
  public SecurityFilterChain configureAuthorizationFilterChain(HttpSecurity http) throws Exception {
    // Set up the authorization requests, starting from most restrictive at the top,
    // to least restrictive on the bottom
    http
        // Disable CSRF and CORS checks. Without this it will be hard to make automated tests.
        .csrf(AbstractHttpConfigurer::disable)
        .cors(AbstractHttpConfigurer::disable)
        // Authentication and signup is accessible for everyone
        .authorizeHttpRequests((auth) -> auth.requestMatchers("/users/register").permitAll())
        .authorizeHttpRequests((auth) -> auth.requestMatchers("/users/login").permitAll())
        // Products are also available to everyone
        .authorizeHttpRequests((auth) -> auth.requestMatchers("/courses").permitAll())
        .authorizeHttpRequests((auth) -> auth.requestMatchers("/courses/*").permitAll())
        .authorizeHttpRequests((auth) -> auth.requestMatchers("/courses/category/*").permitAll())
        // Sending message from contact form is also available to everyone
        .authorizeHttpRequests((auth) -> auth.requestMatchers("/messages").permitAll())
        // Allow Swagger UI and API docs
        .authorizeHttpRequests((auth) -> auth.requestMatchers("/swagger-ui/**").permitAll())
        .authorizeHttpRequests((auth) -> auth.requestMatchers("/v3/api-docs/**").permitAll())
        .authorizeHttpRequests((auth) -> auth.requestMatchers("/swagger-resources/**").permitAll())
        .authorizeHttpRequests((auth) -> auth.requestMatchers("/webjars/**").permitAll())
        // Allow HTTP OPTIONS requests - CORS pre-flight requests
        .authorizeHttpRequests((auth) -> auth.requestMatchers(HttpMethod.OPTIONS).permitAll())
        // Any other request will be authenticated with a stateless policy
        .authorizeHttpRequests((auth) -> auth.anyRequest().authenticated())
        // Enable stateless session policy
        .sessionManagement((session) ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // Enable our JWT authentication filter
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  /**
   * This method is called to create the authentication manager.
   *
   * @return Authentication manager
   * @throws Exception On authentication config error
   */
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }

  /**
   * This method is called to decide what encryption to use for password checking.
   *
   * @return The password encryptor
   */
  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
