package no.ntnu.iir.idata.gr9.backend.dto;

import no.ntnu.iir.idata.gr9.backend.entity.FavoriteCourse;

/**
 * Data Transfer Object for Favorite Course to send all necessary data over to frontend.
 */
public class FavoriteCourseDTO {
  private int id;
  private int courseId;
  private String courseTitle;

  /**
   * Creates a new data transfer object for Favorite Course.
   *
   * @param favoriteCourse the favorite course to create a DTO from
   */
  public FavoriteCourseDTO(FavoriteCourse favoriteCourse) {
    this.id = favoriteCourse.getId();
    this.courseId = favoriteCourse.getCourse().getId();
    this.courseTitle = favoriteCourse.getCourse().getTitle();
  }

  /**
   * Returns the id of the favorite course.
   *
   * @return the id of the favorite course
   */
  public int getId() {
    return id;
  }

  /**
   * Returns the id of the course.
   *
   * @return the id of the course
   */
  public int getCourseId() {
    return courseId;
  }

  /**
   * Returns the title of the course.
   *
   * @return the title of the course
   */
  public String getCourseTitle() {
    return courseTitle;
  }
}
