package no.ntnu.iir.idata.gr9.backend.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * Represents a message entity which handles messages sent via contact form.
 * Should include fields for sender's name, email, and message body.
 */
@CrossOrigin
@Entity
@Schema(description = "Represents a message entity which handles messages sent via contact form.")
public class Message {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "The message's unique id.", example = "1")
  private int id;
  @Schema(description = "The message senders name.", example = "John Doe")
  private String name;
  @Schema(description = "The message senders email.", example = "john@doe.com")
  private String email;
  @Schema(description = "The message body.", example = "Hello, I have a question about your service.")
  private String message;

  /**
   * Default constructor for JPA.
   */
  public Message() {
  }

  /**
   * Creates a new message.
   */
  public Message(String name, String email, String message) {
    this.name = name;
    this.email = email;
    this.message = message;
  }

  /**
   * Gets the message's id.
   *
   * @return the message's id
   */
  public int getId() {
    return this.id;
  }

  /**
   * Sets the message's id.
   *
   * @param id the message's id
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the message's name.
   *
   * @return the message's name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Sets the message's name.
   *
   * @param name the message's name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the message's email.
   *
   * @return the message's email
   */
  public String getEmail() {
    return this.email;
  }

  /**
   * Sets the message's email.
   *
   * @param email the message's email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Gets the message's body.
   *
   * @return the message's body
   */
  public String getMessage() {
    return this.message;
  }

  /**
   * Sets the message's body.
   *
   * @param message the message's body
   */
  public void setMessage(String message) {
    this.message = message;
  }
}
