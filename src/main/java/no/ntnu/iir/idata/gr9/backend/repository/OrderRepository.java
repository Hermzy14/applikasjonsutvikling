package no.ntnu.iir.idata.gr9.backend.repository;

import no.ntnu.iir.idata.gr9.backend.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents a order repository.
 */

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
  /**
   * Finds specific order by its id.
   */
  Order findById(int id);

  /**
   * Finds all orders placed by specific user
   */
  List<Order> findByUser_Id(int userId);

  /**
   * Finds all orders placed for course.
   */
  List<Order> findByCourse_Id(int courseId);

  /**
   * Finds a order by its recent order date.
   */
  List<Order> findByOrderDateAfter(LocalDate date);

  /**
   * Finds a order by filtering price.
   */
  List<Order> findByPriceBetween(double minPrice, double maxPrice);

  /**
   * Finds a order by its discount.
   */
  List<Order> findByDiscount(double discount);

}