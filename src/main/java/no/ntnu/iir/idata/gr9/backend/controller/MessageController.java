package no.ntnu.iir.idata.gr9.backend.controller;

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
  public ResponseEntity<?> sendMessage(@RequestBody Message message) {
    try {
      this.messageRepository.save(message);
      return ResponseEntity.ok("Message sent successfully");
    } catch (Exception e) {
      return ResponseEntity.status(500).body("Error sending message: " + e.getMessage());
    }
  }
}
