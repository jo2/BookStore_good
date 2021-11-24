package de.adesso.bookstore.services;

import de.adesso.bookstore.entities.Book;
import de.adesso.bookstore.entities.Receipt;
import de.adesso.bookstore.entities.ReceiptPosition;
import de.adesso.bookstore.repositories.BookRepository;
import de.adesso.bookstore.repositories.ReceiptRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Test for class BookstoreService.
 */
@ExtendWith(MockitoExtension.class)
class BookstoreServiceTest {

    /**
     * Mock of BookRepository.
     */
    @Mock
    private BookRepository bookRepository;

    /**
     * Mock of ReceiptRepository.
     */
    @Mock
    private ReceiptRepository receiptRepository;

    /**
     * Mock of PaymentService.
     */
    @Mock
    private PaymentService paymentService;

    /**
     * Object under test.
     */
    @InjectMocks
    private BookstoreService bookstoreService;

    /**
     * Test the adding of a new book.
     */
    @Test
    void testAddBook() {
        final Book beforeSave = new Book(null, "Title_1", "Author_1", 10.0, 2010, 10);
        final Book afterSave = new Book(1L, "Title_1", "Author_1", 10.0, 2010, 10);
        Mockito.when(bookRepository.save(beforeSave)).thenReturn(afterSave);

        final Book result = bookstoreService.addBook(beforeSave);

        Assertions.assertThat(result).isEqualTo(afterSave);
    }

    /**
     * Test the updating of a book.
     */
    @Test
    void testUpdateBook() {
        final Book beforeSave = new Book(null, "Title_1", "Author_1", 10.0, 2010, 10);
        final Book update = new Book(1L, "Title_1", "Author_1", 15.0, 2010, 20);
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(beforeSave));
        Mockito.when(bookRepository.save(beforeSave)).thenReturn(update);

        final Book result = bookstoreService.updateBook(1L, update);

        Assertions.assertThat(result).isEqualTo(update);
    }

    /**
     * Test the updating of a book that does not exist.
     */
    @Test
    void testUpdateBookNotFound() {
        final Book update = new Book(1L, "Title_1", "Author_1", 15.0, 2010, 20);
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        final Book result = bookstoreService.updateBook(1L, update);
        Assertions.assertThat(result).isNull();
    }

    /**
     * Test the buying of a book.
     */
    @Test
    void testBuyBooks() {
        final Receipt receipt = new Receipt(1L,
                List.of(
                        new ReceiptPosition(1L, 1L, "Title_1", "Author_1", 10.0, 10, 0.0, 1, 10.0),
                        new ReceiptPosition(2L, 2L, "Title_2", "Author_1", 10.0, 10, 0.0, 2, 20.0),
                        new ReceiptPosition(3L, 3L, "Title_3", "Author_1", 20.0, 10, 0.0, 1, 20.0)
                ),
                50.0,
                LocalDateTime.of(2021, 8, 20, 12, 0)
        );

        Mockito.when(bookRepository.findAllById(List.of(1L, 2L, 3L))).thenReturn(List.of(
                new Book(1L, "Title_1", "Author_1", 10.0, 10, 10),
                new Book(2L, "Title_2", "Author_1", 10.0, 10, 10),
                new Book(3L, "Title_3", "Author_1", 20.0, 10, 10)
        ));

        bookstoreService.buyBooks(receipt);

        Mockito.verify(receiptRepository).save(receipt);
        Mockito.verify(bookRepository).saveAll(List.of(
                new Book(1L, "Title_1", "Author_1", 10.0, 10, 9),
                new Book(2L, "Title_2", "Author_1", 10.0, 10, 8),
                new Book(3L, "Title_3", "Author_1", 20.0, 10, 9)
        ));
        Mockito.verify(paymentService).pay(50.0);
    }

    /**
     * Test getting all receipts.
     */
    @Test
    void testGetAllReceipts() {
        final List<Receipt> receipts = List.of(
                new Receipt(1L,
                        List.of(
                                new ReceiptPosition(1L, 1L, "Title_1", "Author_1", 10.0, 10, 0.0, 1, 10.0),
                                new ReceiptPosition(2L, 2L, "Title_2", "Author_1", 10.0, 10, 0.0, 2, 20.0),
                                new ReceiptPosition(3L, 3L, "Title_3", "Author_1", 20.0, 10, 0.0, 1, 20.0)
                        ),
                        50.0,
                        LocalDateTime.of(2021, 8, 20, 12, 0)
                ),
                new Receipt(2L,
                        List.of(
                                new ReceiptPosition(4L, 4L, "Title_1", "Author_2", 10.0, 10, 0.0, 1, 10.0),
                                new ReceiptPosition(5L, 5L, "Title_2", "Author_2", 10.0, 10, 0.0, 2, 20.0),
                                new ReceiptPosition(6L, 6L, "Title_3", "Author_2", 20.0, 10, 0.0, 1, 20.0)
                        ),
                        50.0,
                        LocalDateTime.of(2021, 8, 20, 12, 0)
                )
        );

        Mockito.when(receiptRepository.findAll()).thenReturn(receipts);

        Assertions.assertThat(bookstoreService.getAllReceipts()).isEqualTo(receipts);
    }

    /**
     * Test the generation of a new receipt.
     */
    @Test
    void testGenerateReceipt() {
        Mockito.when(bookRepository.findAllById(List.of(1L, 2L, 3L))).thenReturn(List.of(
                new Book(1L, "Title_1", "Author_1", 10.0, 10, 10),
                new Book(2L, "Title_2", "Author_1", 10.0, 10, 10),
                new Book(3L, "Title_3", "Author_1", 20.0, 10, 10)
        ));

        final Receipt result = bookstoreService.generateReceipt(List.of(1L, 2L, 3L));

        Assertions.assertThat(result).isEqualTo(new Receipt(null,
                List.of(
                        new ReceiptPosition(null, 1L, "Title_1", "Author_1", 10.0, 10, 0.0, 1, 10.0),
                        new ReceiptPosition(null, 2L, "Title_2", "Author_1", 10.0, 10, 0.0, 1, 10.0),
                        new ReceiptPosition(null, 3L, "Title_3", "Author_1", 20.0, 10, 0.0, 1, 20.0)
                ),
                40.0,
                null
        ));
    }

    /**
     * Test finding a book by its ID.
     */
    @Test
    void testFindById() {
        final Book book = new Book(1L, "Title_1", "Author_1", 10.0, 10, 10);

        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        final Book result = bookstoreService.findById(1L);
        Assertions.assertThat(result).isEqualTo(book);
    }

    /**
     * Test finding a book by its ID that does not exist.
     */
    @Test
    void testFindByIdNotFound() {
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        final Book result = bookstoreService.findById(1L);
        Assertions.assertThat(result).isNull();
    }

    /**
     * Test removing a book by its ID.
     */
    @Test
    void testRemoveBook() {
        bookstoreService.removeBook(1L);
        Mockito.verify(bookRepository).deleteById(1L);
    }

    /**
     * Test getting all books.
     */
    @Test
    void testGetAllBooks() {
        final List<Book> books = List.of(
                new Book(1L, "Title_1", "Author_1", 10.0, 10, 10),
                new Book(2L, "Title_2", "Author_1", 10.0, 10, 10),
                new Book(3L, "Title_3", "Author_1", 20.0, 10, 10)
        );

        Mockito.when(bookRepository.findAll()).thenReturn(books);

        final List<Book> result = bookstoreService.getAllBooks();
        Assertions.assertThat(result).isEqualTo(books);
    }
}
