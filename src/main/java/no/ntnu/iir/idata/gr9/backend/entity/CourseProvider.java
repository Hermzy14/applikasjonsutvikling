package no.ntnu.iir.idata.gr9.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class CourseProvider {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;
  private double price;
  private double discount;
  private String currency;
  @ManyToOne
  @JsonIgnore
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
  public CourseProvider(int id, String name, double price, double discount, String currency, Course course) {
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
   * Sets the course provider's id. TODO: The id must be unique and a positive integer.
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
   * Sets the course provider's price. TODO: The price must be a positive number.
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
