package de.adesso.bookstore.services;

import de.adesso.bookstore.entities.Book;
import de.adesso.bookstore.entities.Receipt;
import de.adesso.bookstore.entities.ReceiptPosition;
import de.adesso.bookstore.repositories.BookRepository;
import de.adesso.bookstore.repositories.ReceiptRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * The service for management functions of the bookstore.
 */
@Service
@RequiredArgsConstructor
public class BookstoreService {

    /**
     * The repository for the books.
     */
    private final BookRepository bookRepository;

    /**
     * The repository for the receipts.
     */
    private final ReceiptRepository receiptRepository;

    /**
     * The service for the payments.
     */
    private final PaymentService paymentService;

    /**
     * Checks whether a book with a specific ID does exist.
     *
     * @param id The ID to check
     * @return Whether the book exists or not
     */
    public boolean existsById(Long id) {
        return bookRepository.existsById(id);
    }

    /**
     * Add a new book.
     *
     * @param book The new book to add
     * @return The added book
     */
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    /**
     * Update a book by its id.
     *
     * @param id The id of the book to update
     * @param book The new data for the book
     * @return The updated book
     */
    public Book updateBook(Long id, Book book) {
        final Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            final var bookFromDb = optionalBook.get();
            bookFromDb.setTitle(book.getTitle());
            bookFromDb.setAuthor(book.getAuthor());
            bookFromDb.setPrice(book.getPrice());
            bookFromDb.setYear(book.getYear());
            bookFromDb.setAmount(book.getAmount());
            return bookRepository.save(bookFromDb);
        }
        return null;
    }

    /**
     * Buy a list of books. Each Book stored in a receipt position with an amount and a discount that is subtracted
     * from the price for all exemplars of this book.
     *
     * @param receipt The receipt containing receipt positions and books
     */
    public void buyBooks(Receipt receipt) {
        final List<Book> books = StreamSupport
                .stream(bookRepository.findAllById(receipt.getReceiptPositions().stream()
                        .map(ReceiptPosition::getBookId).collect(Collectors.toList())).spliterator(), false)
                .collect(Collectors.toList());

        receipt.getReceiptPositions().forEach(pos -> {
            books.stream().filter(book -> pos.getBookId().equals(book.getId()))
                    .forEach(book -> book.setAmount(book.getAmount() - pos.getAmount()));

            pos.setCost((pos.getPrice() * (1 - pos.getDiscountPercentage() / 100)) * pos.getAmount());
        });

        receipt.setSum(receipt.getReceiptPositions().stream()
                .map(ReceiptPosition::getCost)
                .reduce(Double::sum)
                .orElse(0.0));

        receipt.setTimestamp(LocalDateTime.now());

        receiptRepository.save(receipt);
        bookRepository.saveAll(books);
        paymentService.pay(receipt.getSum());
    }

    /**
     * Get a list of all receipts.
     *
     * @return The list of all receipts
     */
    public List<Receipt> getAllReceipts() {
        return StreamSupport.stream(receiptRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    /**
     * Generate a receipt from a list of book ids.
     *
     * @param bookIds The ids of the books sold in this receipt
     * @return The generated receipt
     */
    public Receipt generateReceipt(List<Long> bookIds) {
        final List<ReceiptPosition> receiptPositions = StreamSupport
                .stream(bookRepository.findAllById(bookIds).spliterator(), false)
                .map(book -> new ReceiptPosition(null, book.getId(), book.getTitle(), book.getAuthor(),
                        book.getPrice(), book.getAmount(), 0, 1, book.getPrice()))
                .collect(Collectors.toList());

        return new Receipt(null,
                receiptPositions,
                receiptPositions.stream().map(ReceiptPosition::getCost).reduce(Double::sum).orElse(0.0),
                null);
    }

    /**
     * Get a book by its id.
     *
     * @param id The id of the book
     * @return The found book
     */
    public Book findById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    /**
     * Delete a book by its id.
     *
     * @param id The id of the book
     */
    public void removeBook(Long id) {
        bookRepository.deleteById(id);
    }

    /**
     * Get a list of all books available.
     *
     * @return The list of all books
     */
    public List<Book> getAllBooks() {
        return (List<Book>) bookRepository.findAll();
    }
}
