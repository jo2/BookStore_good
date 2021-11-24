package de.adesso.bookstore.mapper;

import de.adesso.bookstore.dto.BookDto;
import de.adesso.bookstore.entities.Book;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test for class BookMapper.
 */
class BookMapperTest {

    /**
     * Object under test.
     */
    private final BookMapper bookMapper = new BookMapper();

    /**
     * Test mapping from data transfer object to domain object.
     */
    @Test
    void mapDtoObject() {
        Assertions.assertThat(bookMapper.mapDtoObject(new BookDto(1L, "Book_1", "Author_1", 5.0, 2018, 10)))
                .isEqualTo(new Book(1L, "Book_1", "Author_1", 5.0, 2018, 10));
    }

    /**
     * Test mapping from domain object to data transfer object.
     */
    @Test
    void mapDomainObject() {
        Assertions.assertThat(bookMapper.mapDomainObject(new Book(1L, "Book_1", "Author_1", 5.0, 2018, 10)))
                .isEqualTo(new BookDto(1L, "Book_1", "Author_1", 5.0, 2018, 10));
    }

    /**
     * Test mapping from list of data transfer object to list of domain object.
     */
    @Test
    void mapDtoObjects() {
        Assertions.assertThat(bookMapper.mapDtoObjects(List.of(
                new BookDto(1L, "Book_1", "Author_1", 5.0, 2018, 10),
                new BookDto(2L, "Book_2", "Author_1", 5.0, 2018, 10)
        )))
                .isEqualTo(List.of(
                        new Book(1L, "Book_1", "Author_1", 5.0, 2018, 10),
                        new Book(2L, "Book_2", "Author_1", 5.0, 2018, 10)
                ));
    }

    /**
     * Test mapping from list of domain object to list of data transfer object.
     */
    @Test
    void mapDomainObjects() {
        Assertions.assertThat(bookMapper.mapDomainObjects(List.of(
                new Book(1L, "Book_1", "Author_1", 5.0, 2018, 10),
                new Book(2L, "Book_2", "Author_1", 5.0, 2018, 10)
        )))
                .isEqualTo(List.of(
                        new BookDto(1L, "Book_1", "Author_1", 5.0, 2018, 10),
                        new BookDto(2L, "Book_2", "Author_1", 5.0, 2018, 10)
                ));
    }
}
