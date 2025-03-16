package no.ntnu.iir.idata.gr9.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a course.
 * <p>A course includes one of the following categories:</p>
 * <ul>
 *   <li>Information Technologies</li>
 *   <li>Digital Marketing</li>
 *   <li>Business and Entrepreneurship</li>
 *   <li>Data Science and Analytics</li>
 * </ul>
 * <p>Each course has:</p>
 * <ul>
 *   <li>An id</li>
 *   <li>A title</li>
 *   <li>A description</li>
 *   <li>A category</li>
 *   <li>Keywords</li>
 *   <li>A level of difficulty</li>
 *   <li>A course session/duration</li>
 *   <li>ECTS credits</li>
 *   <li>Hours per week</li>
 *   <li>Related certifications</li>
 *   <li>Course providers who can have different pricing for the course</li>
 * </ul>
 */
@Entity
public class Course {
  @Id
  private int id;
    private String title;
    private String description;
    private String category;
    private String keywords;
    private String difficulty;

  public Course() {}

}
