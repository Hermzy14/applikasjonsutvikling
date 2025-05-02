package no.ntnu.iir.idata.gr9.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.iir.idata.gr9.backend.entity.Message;
import no.ntnu.iir.idata.gr9.backend.repository.MessageRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsible for handling messages from contact form.
 */
@CrossOrigin
@RestController
@Tag(name = "Message Management", description = "API endpoints for managing messages")
public class MessageController {
  private final MessageRepository messageRepository;

  /**
   * Creates a new MessageController.
   *
   * @param messageRepository the repository for managing messages
   */
  public MessageController(MessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }

  /**
   * HTTP POST method to send a message.
   *
   * @param message The message to be sent
   * @return HTTP 200 OK or error code with error message
   */
  @PostMapping("/messages")
  @Operation(
      summary = "Send a message",
      description = "Saves a message from the contact form to the database."
  )
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "Message sent successfully"
          ),
          @ApiResponse(
              responseCode = "500",
              description = "Error sending message"
          )
      }
  )
  public ResponseEntity<?> sendMessage(@RequestBody Message message) {
    try {
      this.messageRepository.save(message);
      return ResponseEntity.ok("Message sent successfully");
    } catch (Exception e) {
      return ResponseEntity.status(500).body("Error sending message: " + e.getMessage());
    }
  }
}
