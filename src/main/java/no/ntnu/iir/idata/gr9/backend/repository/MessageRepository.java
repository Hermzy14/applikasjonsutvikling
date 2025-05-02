package no.ntnu.iir.idata.gr9.backend.repository;

import no.ntnu.iir.idata.gr9.backend.entity.Message;
import org.springframework.data.repository.CrudRepository;

/**
 * Represents a message repository for handling messages.
 */
public interface MessageRepository extends CrudRepository<Message, Integer> {
}
