package no.ntnu.iir.idata.gr9.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Represents an order.
 * <p>Each order has:</p>
 * <ul>
 * <li>A order id</li>
 * <li>A user</li>
 * <li>A course</li>
 * <li>A order date</li>
 * <li>A price from course and a discount if available</li>
 * </ul>
 */

@Entity
@Table(name = "orders") // avoids reserved keyword issue
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private LocalDate orderDate;
  private double price;
  private double discount;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
  @ManyToOne
  @JoinColumn(name = "course_id")
  private Course course;

  public Order() {
  }

  /**
   * Creates a new order.
   *
   * @param id        the order's id
   * <p>Must be unique</p>
   * <p>Must be a positive integer</p>
   * @param user      the user
   * <p>Must not be null</p>
   * @param course    the course
   * <p>Must not be null</p>
   * @param orderDate the order's date
   * <p>Must be a date</p>
   * @param price     the order's price
   * <p>Must be a positive number</p>
   * @param discount  the order's discount
   * <p>Must be a positive number</p>
   */
  public Order(int id, User user, Course course, LocalDate orderDate, double price, double discount) {
    setId(id);
    setUser(user);
    setCourse(course);
    setOrderDate(orderDate);
    setPrice(price);
    setDiscount(discount);
  }

  /**
   * Gets the order's id.
   *
   * @return the order's id
   */
  public int getId() {
    return this.id;
  }

  /**
   * Sets the order's id.
   *
   * @param id the order's id
   */
  public void setId(int id) {
    if (id < 0) {
      throw new IllegalArgumentException("The id must be a positive integer");
    }
    this.id = id;
  }

  /**
   * Gets the user.
   *
   * @return the user
   */
  public User getUser() {
    return this.user;
  }

  /**
   * Sets the user.
   *
   * @param user the user
   */
  public void setUser(User user) {
    if (user == null) {
      throw new IllegalArgumentException("The user must not be null");
    }
    this.user = user;
  }

  /**
   * Gets the course.
   *
   * @return the course
   */
  public Course getCourse() {
    return this.course;
  }

  /**
   * Sets the course.
   *
   * @param course the course
   */
  public void setCourse(Course course) {
    if (course == null) {
      throw new IllegalArgumentException("The course must not be null");
    }
    this.course = course;
  }

  /**
   * Gets the order's date.
   *
   * @return the order's date
   */
  public LocalDate getOrderDate() {
    return this.orderDate;
  }

  /**
   * Sets the order's date.
   *
   * @param orderDate the order's date
   */
  public void setOrderDate(LocalDate orderDate) {
    if (orderDate == null) {
      throw new IllegalArgumentException("The order date must not be null");
    }
    this.orderDate = orderDate;
  }

  /**
   * Gets the order's price.
   *
   * @return the order's price
   */
  public double getPrice() {
    return this.price;
  }

  /**
   * Sets the order's price.
   *
   * @param price the order's price TODO: kanskje endre på dette?
   */
  public void setPrice(double price) {
    if (price < 0) {
      throw new IllegalArgumentException("The price must be a positive number");
    }
    this.price = price;
  }

  /**
   * Gets the order's discount.
   *
   * @return the order's discount
   */
  public double getDiscount() {
    return this.discount;
  }

  /**
   * Sets the order's discount.
   *
   * @param discount the order's discount TODO: kanskje endre på dette?
   */
  public void setDiscount(double discount) {
    if (discount < 0) {
      throw new IllegalArgumentException("The discount must be a positive number");
    }
    this.discount = discount;
  }

  /**
   * Gets the user's id.
   *
   * @return the user's id
   */
  public int getUserId() {
    return this.user != null ? this.user.getId() : null;
  }

  /**
   * Gets the course's id.
   *
   * @return the course's id
   */
  public int getCourseId() {
    return this.course != null ? this.course.getId() : null;
  }
}