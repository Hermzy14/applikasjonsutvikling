package no.ntnu.iir.idata.gr9.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Represents a favorite course for a user.
 */
@Entity
public class FavoriteCourse {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "course_id")
  private Course course;

  public FavoriteCourse() {
  }

  /**
   * Creates a new favorite course.
   */
  public FavoriteCourse(User user, Course course) {
    setUser(user);
    setCourse(course);
  }

  /**
   * Returns the id.
   *
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the id.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Returns the user.
   *
   * @return the user
   */
  public User getUser() {
    return user;
  }

  /**
   * Sets the user.
   */
  public void setUser(User user) {
    this.user = user;
  }

  /**
   * Returns the course.
   *
   * @return the course.
   */
  public Course getCourse() {
    return course;
  }

  /**
   * Sets the course.
   */
  public void setCourse(Course course) {
    this.course = course;
  }
}
