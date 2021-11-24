package de.adesso.bookstore.repositories;

import de.adesso.bookstore.entities.Receipt;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for receipts.
 */
@Repository
public interface ReceiptRepository extends CrudRepository<Receipt, Long> {
}
