package no.ntnu.iir.idata.gr9.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * Represents a course provider.
 * <p>A course provider has:</p>
 * <ul>
 *   <li>An id</li>
 *   <li>A name</li>
 *   <li>A price</li>
 *   <li>A discount percentage</li>
 *   <li>Currency</li>
 *   <li>The course</li>
 * </ul>
 */
@Entity
@Schema(description = "Represents a course provider.")
public class CourseProvider {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "The course provider's unique id.", example = "1")
  private int id;
  @Schema(description = "The course provider's name.", example = "Coursera")
  private String name;
  @Schema(description = "The course provider's price.", example = "100.0")
  private double price;
  @Schema(description = "The course provider's discount percentage.", example = "10.0")
  private double discount;
  @Schema(description = "The course provider's currency.", example = "USD")
  private String currency;
  @ManyToOne
  @JsonIgnore
  @Schema(description = "The course provider's course.")
  private Course course;

  public CourseProvider() {
  }

  /**
   * Creates a new course provider.
   *
   * @param id       the course provider's id
   *                 <p>Must be unique</p>
   *                 <p>Must be a positive integer</p>
   * @param name     the course provider's name
   * @param price    the course provider's price
   *                 <p>Must be a positive number</p>
   * @param discount the course provider's discount percentage
   * @param currency the course provider's currency
   *                 <p>Must be a string</p>
   * @param course   the course provider's course
   */
  public CourseProvider(int id, String name, double price, double discount, String currency,
                        Course course) {
    setId(id);
    setName(name);
    setPrice(price);
    setDiscount(discount);
    setCurrency(currency);
    setCourse(course);
  }

  /**
   * Gets the course provider's id.
   *
   * @return the course provider's id
   */
  public int getId() {
    return this.id;
  }

  /**
   * Sets the course provider's id.
   *
   * @param id the course provider's id
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the course provider's name.
   *
   * @return the course provider's name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Sets the course provider's name.
   *
   * @param name the course provider's name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the course provider's price.
   *
   * @return the course provider's price
   */
  public double getPrice() {
    return this.price;
  }

  /**
   * Sets the course provider's price.
   *
   * @param price the course provider's price
   */
  public void setPrice(double price) {
    this.price = price;
  }

  /**
   * Gets the course provider's discount percentage.
   *
   * @return the course provider's discount percentage
   */
  public double getDiscount() {
    return this.discount;
  }

  /**
   * Sets the course provider's discount percentage.
   *
   * @param discount the course provider's discount percentage
   */
  public void setDiscount(double discount) {
    this.discount = discount;
  }

  /**
   * Gets the course provider's currency.
   *
   * @return the course provider's currency
   */
  public String getCurrency() {
    return this.currency;
  }

  /**
   * Sets the course provider's currency.
   *
   * @param currency the course provider's currency
   */
  public void setCurrency(String currency) {
    this.currency = currency;
  }

  /**
   * Gets the course provider's course.
   *
   * @return the course provider's course
   */
  public Course getCourse() {
    return this.course;
  }

  /**
   * Sets the course provider's course.
   *
   * @param course the course provider's course
   */
  public void setCourse(Course course) {
    this.course = course;
  }
}
