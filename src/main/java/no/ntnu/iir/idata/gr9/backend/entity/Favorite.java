package no.ntnu.iir.idata.gr9.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * represents a favorite courses for the user.
 * <p>Each favorite has:</p>
 * <ul>
 *     <li>A user id</li>
 *     <li>A course id</li>
 * </ul>
 */

public class Favorite {

    @Id
    private int id;
    private int userId;
    private int courseId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public Favorite() {
    }

    /**
     * Creates a new favorite.
     *
     * @param id                    the favorite's id
     *                              <p>Must be unique</p>
     *                              <p>Must be a positive integer</p>
     * @param userId                the user's id
     *                              <p>Must be a positive integer</p>
     * @param courseId              the course's id
     *                              <p>Must be a positive integer</p>
     */
    public Favorite(int id, int userId, int courseId) {
        setId(id);
        setUserId(userId);
        setCourseId(courseId);
    }

    /**
     * Gets the favorite's id.
     *
     * @return the favorite's id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Sets the favorite's id.
     *
     * @param id the favorite's id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the user's id.
     *
     * @return the user's id
     */
    public int getUserId() {
        return this.userId;
    }

    /**
     * Sets the user's id.
     *
     * @param userId the user's id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the course's id.
     *
     * @return the course's id
     */
    public int getCourseId() {
        return this.courseId;
    }

    /**
     * Sets the course's id.
     *
     * @param courseId the course's id
     */
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

}
