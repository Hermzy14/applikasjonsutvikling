package no.ntnu.iir.idata.gr9.backend.controller;

import no.ntnu.iir.idata.gr9.backend.entity.Order;
import no.ntnu.iir.idata.gr9.backend.repository.OrderRepository;
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
public class OrderController {
  private final OrderRepository orderRepository;
  private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

  /**
   * Constructor for OrderController.
   */
  public OrderController(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }
  /**
   * Get all orders for a specific user.
   * <p>
   * Endpoint: {@code GET /orders/user/{userId}}.
   *
   * @param userId the id of the user
   * @return list of orders for that user
   */

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable int userId) {
    logger.info("Getting orders for user with ID: {}", userId);
    List<Order> userOrders = orderRepository.findByUser_Id(userId);

    if (userOrders.isEmpty()) {
      logger.warn("No orders found for user with ID: {}", userId);
      return ResponseEntity.noContent().build(); // 204 No Content
    }

    return ResponseEntity.ok(userOrders); // 200 OK
  }

    /**
     * Get order by id.
     * <p>
     * Endpoint: {@code GET /orders/{id}}.
     *
     * @param id the id of the order
     * @return the order with the specified id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable int id) {
        logger.info("Getting order with id: {}", id);
        Order order = orderRepository.findById(id);
        if (order == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
        return ResponseEntity.ok(order);
    }

  /**
   * Create a new order.
   * <p>
   *   Endpoint: {@code POST /orders}.
   *
   *   @param order the order to create
   *   @return the created order
   */

  @PostMapping
  public ResponseEntity<String> createOrder(@RequestBody Order order) {
    logger.info("Creating new order with ID: {}", order.getId());

    if (orderRepository.existsById(order.getId())) {
      logger.error("Order with ID {} already exists", order.getId());
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Order already exists");
    }

    // Set the current date if not provided
    if (order.getOrderDate() == null) {
      order.setOrderDate(LocalDate.now());
    }

    orderRepository.save(order);
    logger.info("Order saved successfully");
    return ResponseEntity.status(HttpStatus.CREATED).body("Order created successfully");
  }

  /**
   * Delete an order by id.
   * <p>
   *   Endpoint: {@code DELETE /orders/{id}}.
   *
   *   @param id the id of the order to delete
   */

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrder(@PathVariable int id) {
    if (!orderRepository.existsById(id)) {
      logger.warn("Attempted to delete non-existent order with ID: {}", id);
      return ResponseEntity.notFound().build();
    }

    orderRepository.deleteById(id);
    logger.info("Deleted order with ID: {}", id);
    return ResponseEntity.noContent().build();
  }
  }
