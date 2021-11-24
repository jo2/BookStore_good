package de.adesso.bookstore.mapper;

import de.adesso.bookstore.dto.BookDto;
import de.adesso.bookstore.entities.Book;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Mapper for books.
 */
public class BookMapper {

    /**
     * Map a book to its data transfer object.
     *
     * @param book The book to map
     * @return The mapped data transfer object
     */
    public BookDto mapDomainObject(Book book) {
        return new BookDto(book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPrice(),
                book.getYear(),
                book.getAmount());
    }

    /**
     * Map a data transfer object to its book.
     *
     * @param bookDto The data transfer object to map
     * @return The mapped book
     */
    public Book mapDtoObject(BookDto bookDto) {
        return new Book(bookDto.getId(),
                bookDto.getTitle(),
                bookDto.getAuthor(),
                bookDto.getPrice(),
                bookDto.getYear(),
                bookDto.getAmount());
    }

    /**
     * Map books to their data transfer objects.
     *
     * @param books The books to map
     * @return The mapped data transfer objects
     */
    public List<BookDto> mapDomainObjects(List<Book> books) {
        return books.stream().map(this::mapDomainObject).collect(Collectors.toList());
    }

    /**
     * Map data transfer objects to their books.
     *
     * @param bookDtos The data transfer objects to map
     * @return The mapped books
     */
    public List<Book> mapDtoObjects(List<BookDto> bookDtos) {
        return bookDtos.stream().map(this::mapDtoObject).collect(Collectors.toList());
    }
}
