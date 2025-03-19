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
 * Represents an order.
 * <p>Each order has:</p>
 * <ul>
 *   <li>A order id</li>
 *   <li>A user id</li>
 *   <li>A course id</li>
 *   <li>A order date</li>
 *   <li>A price from course and a discount if available</li>
 * </ul>
 */

@Entity
public class Order {
    @Id
    private int id;
    private int userId;
    private int courseId;
    private LocalDate orderDate;
    private double price; //double? or float? kan endres etter hva vi har gjort andre steder
    private double discount; //double? or float? kan endres etter hva vi har gjort andre steder
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
     * @param id                    the order's id
     *                              <p>Must be unique</p>
     *                              <p>Must be a positive integer</p>
     * @param userId                the user's id
     *                              <p>Must be a positive integer</p>
     * @param courseId              the course's id
     *                              <p>Must be a positive integer</p>
     * @param orderDate             the order's date
     *                              <p>Must be a date</p>
     * @param price                 the order's price
     *                              <p>Must be a positive number</p>
     * @param discount              the order's discount
     *                              <p>Must be a positive number</p>
     */
    public Order(int id, int userId, int courseId, LocalDate orderDate, double price, double discount) {
        setId(id);
        setUserId(userId);
        setCourseId(courseId);
        setOrderDate(orderDate);
        setPrice(price);
        setDiscount(discount);
    }

    /**
     * Gets the order's id.
     *
     * @return the order's id
     */
    public int getId() { return this.id;}

    /**
     * Sets the order's id.
     *
     * @param id the order's id
     */
    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("The id must be a positive integer");
        }
        this.id = id;}

    /**
     * Gets the user's id.
     *
     * @return the user's id
     */
    public int getUserId() { return this.userId;}

    /**
     * Sets the user's id.
     *
     * @param userId the user's id
     */
    public void setUserId(int userId) {
        if (userId < 0) {
            throw new IllegalArgumentException("The user id must be a positive integer");
        }
        this.userId = userId;}

    /**
     * Gets the course's id.
     *
     * @return the course's id
     */
    public int getCourseId() { return this.courseId;}

    /**
     * Sets the course's id.
     *
     * @param courseId the course's id
     */
    public void setCourseId(int courseId) {
        if (courseId < 0) {
            throw new IllegalArgumentException("The course id must be a positive integer");
        }
        this.courseId = courseId;}

    /**
     * Gets the order's date.
     *
     * @return the order's date
     */
    public LocalDate getOrderDate() { return this.orderDate;}

    /**
     * Sets the order's date.
     *
     * @param orderDate the order's date
     */
    public void setOrderDate(LocalDate orderDate) {
        if (orderDate == null) {
            throw new IllegalArgumentException("The order date must not be null");
        }
        this.orderDate = orderDate;}

    /**
     * Gets the order's price.
     *
     * @return the order's price
     */
    public double getPrice() { return this.price;}

    /**
     * Sets the order's price.
     *
     * @param price the order's price TODO: kanskje endre på dette?
     */
    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("The price must be a positive number");
        }
        this.price = price;}

    /**
     * Gets the order's discount.
     *
     * @return the order's discount
     */
    public double getDiscount() { return this.discount;}

    /**
     * Sets the order's discount.
     *
     * @param discount the order's discount TODO: kanskje endre på dette?
     */
    public void setDiscount(double discount) {
        if (discount < 0) {
            throw new IllegalArgumentException("The discount must be a positive number");
        }
        this.discount = discount;}

}
