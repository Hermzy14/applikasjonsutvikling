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
        .allowedOrigins(
            "http://localhost:3000",  // Assuming React default port
            "http://localhost:8080",  // Another common dev port
            "http://localhost:5173",  // Vite default port
            "http://127.0.0.1:3000",   // Sometimes needed for Windows
            "https://webteknologi9.netlify.app" // Production URL
        )
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        .allowedHeaders("*")
        .allowCredentials(true)
        .maxAge(3600);
  }
}