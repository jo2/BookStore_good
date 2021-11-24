package de.adesso.bookstore.repositories;

import de.adesso.bookstore.entities.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for books.
 */
@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
}
