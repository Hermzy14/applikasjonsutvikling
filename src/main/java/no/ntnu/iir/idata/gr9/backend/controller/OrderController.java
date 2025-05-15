package no.ntnu.iir.idata.gr9.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
import no.ntnu.iir.idata.gr9.backend.entity.Order;
import no.ntnu.iir.idata.gr9.backend.entity.User;
import no.ntnu.iir.idata.gr9.backend.repository.OrderRepository;
import no.ntnu.iir.idata.gr9.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

/**
 * REST API controller for managing orders.
 */
@RestController
@RequestMapping("/orders")
@Tag(name = "Order Management", description = "API endpoints for managing customer orders")
public class OrderController {
  private final OrderRepository orderRepository;
  private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
  private final UserRepository userRepository;

  /**
   * Constructor for OrderController.
   *
   * @param orderRepository the repository for managing orders
   */
  public OrderController(OrderRepository orderRepository, UserRepository userRepository) {
    this.orderRepository = orderRepository;
    this.userRepository = userRepository;
  }

  /**
   * Get all orders for a specific user.
   * <p>
   * Endpoint: {@code GET /orders/{username}}.
   *
   * @param username the username of the user
   * @return list of orders for that user
   */
  @GetMapping("/{username}")
  @Operation(
      summary = "Get orders by user ID",
      description = "Retrieves all orders associated with a specific user."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Orders found and returned",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = Order.class)
          )
      ),
      @ApiResponse(
          responseCode = "204",
          description = "No orders found for the specified user",
          content = @Content
      ),
        @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(type = "object", example = "{\"status\":404,\"message\":\"User not found\"}")
            )
        )
  })
  public ResponseEntity<List<Order>> getOrdersByUser(
      @Parameter(description = "Username of the user to retrieve orders for", required = true)
      @PathVariable String username) {
    logger.info("Getting orders for user: {}", username);
    Optional<User> userOptional = userRepository.findByUsername(username);
    if (userOptional.isEmpty()) {
      logger.error("User with username {} not found", username);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }
    User user = userOptional.get();
    List<Order> orders = orderRepository.findByUser_Id(user.getId());
    if (orders.isEmpty()) {
      logger.warn("No orders found for user: {}", username);
      return ResponseEntity.noContent().build();
    }
    logger.info("Found {} orders for user: {}", orders.size(), username);
    return ResponseEntity.ok(orders);
  }

  /**
   * Create a new order.
   * <p>
   * Endpoint: {@code POST /orders/{username}}.
   *
   * @param order the order to create
   * @return the created order
   */
  @PostMapping("/{username}")
  @Operation(
      summary = "Create a new order",
      description = "Creates a new order in the system. Sets the current date if not provided."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "201",
          description = "Order created successfully",
          content = @Content(
              mediaType = "text/plain",
              schema = @Schema(type = "string", example = "Order created successfully")
          )
      ),
      @ApiResponse(
          responseCode = "404",
          description = "User not found",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(type = "object", example = "{\"status\":404,\"message\":\"User not found\"}")
          )
      ),
      @ApiResponse(
          responseCode = "409",
          description = "Order already exists",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(type = "object", example = "{\"status\":409,\"message\":\"Order already exists\"}")
          )
      )
  })
  public ResponseEntity<String> createOrder(
      @Parameter(description = "ID of the user placing the order", required = true)
      @PathVariable String username,
      @Parameter(description = "Order object to be created", required = true,
          schema = @Schema(implementation = Order.class))
      @RequestBody Order order) {
    logger.info("Creating new order with ID: {}", order.getId());

    Optional<User> userOptional = userRepository.findByUsername(username);
    if (userOptional.isEmpty()) {
      logger.error("User with username {} not found", username);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }
    User user = userOptional.get();
    order.setUser(user);

    if (orderRepository.existsById(order.getId())) {
      logger.error("Order with ID {} already exists", order.getId());
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Order already exists");
    }

    // Set the current date if not provided
    if (order.getOrderDate() == null) {
      order.setOrderDate(LocalDate.now());
    }

    orderRepository.save(order);
    logger.info("Order saved successfully for user {}", user.getId());
    return ResponseEntity.status(HttpStatus.CREATED).body("Order created successfully");
  }

  /**
   * Delete an order by id.
   * <p>
   * Endpoint: {@code DELETE /orders/{id}}.
   *
   * @param id the id of the order to delete
   */
  @DeleteMapping("/{id}")
  @Operation(
      summary = "Delete an order",
      description = "Deletes an order from the system by its ID."
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "204",
          description = "Order deleted successfully",
          content = @Content
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Order not found",
          content = @Content
      )
  })
  public ResponseEntity<Void> deleteOrder(
      @Parameter(description = "ID of the order to delete", required = true)
      @PathVariable int id) {
    if (!orderRepository.existsById(id)) {
      logger.warn("Attempted to delete non-existent order with ID: {}", id);
      return ResponseEntity.notFound().build();
    }

    orderRepository.deleteById(id);
    logger.info("Deleted order with ID: {}", id);
    return ResponseEntity.noContent().build();
  }
}