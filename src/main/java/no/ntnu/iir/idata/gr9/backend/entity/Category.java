package no.ntnu.iir.idata.gr9.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Set;

/**
 * Represents a category.
 * <p>A category can be one of the following:</p>
 * <ul>
 *   <li>Information Technologies</li>
 *   <li>Digital Marketing</li>
 *   <li>Business and Entrepreneurship</li>
 *   <li>Data Science and Analytics</li>
 * </ul>
 * <p>Each category has:</p>
 * <ul>
 *   <li>An id</li>
 *   <li>A name</li>
 *   <li>Courses in this category</li>
 * </ul>
 */
@Entity
@Schema(description = "Represents a category.")
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The category's unique id.", example = "1")
  private int id;
    @Schema(description = "The category's name.", example = "Information Technologies")
  private String name;
  @OneToMany(mappedBy = "category")
  @JsonIgnore
    @Schema(description = "The category's courses.")
  private Set<Course> courses;

  public Category() {
  }

  /**
   * Creates a new category.
   *
   * @param name the category's name
   */
  public Category(String name) {
    setName(name);
  }

  /**
   * Gets the category's id.
   *
   * @return the category's id
   */
  public int getId() {
    return this.id;
  }

  /**
   * Sets the category's id.
   *
   * @param id the category's id
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the category's name.
   *
   * @return the category's name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Sets the category's name.
   *
   * @param name the category's name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the category's courses.
   *
   * @return the category's courses
   */
  public Set<Course> getCourses() {
    return this.courses;
  }

  /**
   * Sets the category's courses.
   *
   * @param courses the category's courses
   */
  public void setCourses(Set<Course> courses) {
    this.courses = courses;
  }
}
