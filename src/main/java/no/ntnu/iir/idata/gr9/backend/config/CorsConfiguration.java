package no.ntnu.iir.idata.gr9.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for CORS settings.
 */
@Configuration
public class CorsConfiguration implements WebMvcConfigurer {
  /**
   * Configures CORS settings for the application.
   *
   * @param registry the CORS registry to configure.
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("*")  // In production, specify actual origins
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        .allowedHeaders("*")
        .allowCredentials(false)
        .maxAge(3600);
  }
}