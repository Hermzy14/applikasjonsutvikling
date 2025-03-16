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
 * Represents a course.
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
 *   <li>If it is visible or not</li>
 *   <li>Course providers</li>
 * </ul>
 */
@Entity
public class Course {
  @Id
  private int id;
  private String title;
  private String description;
  private String keywords;
  private String difficulty;
  private LocalDate startDate;
  private LocalDate endDate;
  private Double ects;
  private int hoursPerWeek;
  private String relatedCertifications;
  private boolean isVisible;
  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;
  @OneToMany(mappedBy = "course")
  private Set<CourseProvider> providers = new HashSet<>();

  public Course() {
  }

  /**
   * Creates a new course.
   *
   * @param id                    the course's id
   *                              <p>Must be unique</p>
   *                              <p>Must be a positive integer</p>
   * @param title                 the course's title
   * @param description           the course's description
   * @param keywords              the course's keywords
   * @param difficulty            the course's difficulty
   * @param startDate             the course's start date
   * @param endDate               the course's end date
   * @param ects                  the course's ECTS credits
   *                              <p>Must be a positive number</p>
   * @param hoursPerWeek          the course's hours per week
   *                              <p>Must be a positive integer</p>
   * @param relatedCertifications the course's related certifications
   * @param isVisible             the course's visibility
   * @param category              the course's category
   */
  public Course(int id, String title, String description, String keywords, String difficulty,
                LocalDate startDate, LocalDate endDate, Double ects, int hoursPerWeek,
                String relatedCertifications, boolean isVisible, Category category) {
    setId(id);
    setTitle(title);
    setDescription(description);
    setKeywords(keywords);
    setDifficulty(difficulty);
    setStartDate(startDate);
    setEndDate(endDate);
    setEcts(ects);
    setHoursPerWeek(hoursPerWeek);
    setRelatedCertifications(relatedCertifications);
    setIsVisible(isVisible);
    setCategory(category);
  }

  /**
   * Gets the course's id.
   *
   * @return the course's id
   */
  public int getId() {
    return this.id;
  }

  /**
   * Sets the course's id. TODO: The id must be unique and a positive integer.
   *
   * @param id the course's id
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the course's title.
   *
   * @return the course's title
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * Sets the course's title.
   *
   * @param title the course's title
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Gets the course's description.
   *
   * @return the course's description
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Sets the course's description.
   *
   * @param description the course's description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Gets the course's keywords.
   *
   * @return the course's keywords
   */
  public String getKeywords() {
    return this.keywords;
  }

  /**
   * Sets the course's keywords.
   *
   * @param keywords the course's keywords
   */
  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }

  /**
   * Gets the course's difficulty.
   *
   * @return the course's difficulty
   */
  public String getDifficulty() {
    return this.difficulty;
  }

  /**
   * Sets the course's difficulty.
   *
   * @param difficulty the course's difficulty
   */
  public void setDifficulty(String difficulty) {
    this.difficulty = difficulty;
  }

  /**
   * Gets the course's start date.
   *
   * @return the course's start date
   */
  public LocalDate getStartDate() {
    return this.startDate;
  }

  /**
   * Sets the course's start date.
   *
   * @param startDate the course's start date
   */
  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  /**
   * Gets the course's end date.
   *
   * @return the course's end date
   */
  public LocalDate getEndDate() {
    return this.endDate;
  }

  /**
   * Sets the course's end date.
   *
   * @param endDate the course's end date
   */
  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  /**
   * Gets the course's ECTS credits.
   *
   * @return the course's ECTS credits
   */
  public Double getEcts() {
    return this.ects;
  }

  /**
   * Sets the course's ECTS credits. TODO: The ECTS credits must be a positive number.
   *
   * @param ects the course's ECTS credits
   */
  public void setEcts(Double ects) {
    this.ects = ects;
  }

  /**
   * Gets the course's hours per week.
   *
   * @return the course's hours per week
   */
  public int getHoursPerWeek() {
    return this.hoursPerWeek;
  }

  /**
   * Sets the course's hours per week. TODO: The hours per week must be a positive integer.
   *
   * @param hoursPerWeek the course's hours per week
   */
  public void setHoursPerWeek(int hoursPerWeek) {
    this.hoursPerWeek = hoursPerWeek;
  }

  /**
   * Gets the course's related certifications.
   *
   * @return the course's related certifications
   */
  public String getRelatedCertifications() {
    return this.relatedCertifications;
  }

  /**
   * Sets the course's related certifications.
   *
   * @param relatedCertifications the course's related certifications
   */
  public void setRelatedCertifications(String relatedCertifications) {
    this.relatedCertifications = relatedCertifications;
  }

  /**
   * Gets the course's visibility.
   *
   * @return the course's visibility
   */
  public boolean getIsVisible() {
    return this.isVisible;
  }

  /**
   * Sets the course's visibility.
   *
   * @param isVisible the course's visibility
   */
  public void setIsVisible(boolean isVisible) {
    this.isVisible = isVisible;
  }

  /**
   * Gets the course's category.
   *
   * @return the course's category
   */
  public Category getCategory() {
    return this.category;
  }

  /**
   * Sets the course's category.
   *
   * @param category the course's category
   */
  public void setCategory(Category category) {
    this.category = category;
  }

  /**
   * Gets the course's providers.
   *
   * @return the course's providers
   */
  public Set<CourseProvider> getProviders() {
    return this.providers;
  }

  /**
   * Adds a provider to the course.
   *
   * @param provider the provider to add
   */
  public void addProvider(CourseProvider provider) {
    this.providers.add(provider);
    // Update the other side of the relationship
    provider.setCourse(this);
  }

  /**
   * Removes a provider from the course.
   *
   * @param provider the provider to remove
   */
  public void removeProvider(CourseProvider provider) {
    this.providers.remove(provider);
    // Update the other side of the relationship
    provider.setCourse(null);
  }
}
