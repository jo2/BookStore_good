package de.adesso.bookstore.controller;

import de.adesso.bookstore.entities.Book;
import de.adesso.bookstore.entities.Receipt;
import de.adesso.bookstore.entities.ReceiptPosition;
import de.adesso.bookstore.repositories.BookRepository;
import de.adesso.bookstore.repositories.ReceiptRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Integration test vor class ViewController.
 */
@ExtendWith(SpringExtension.class)
@ActiveProfiles("dummy")
@SpringBootTest(classes = de.adesso.bookstore.TestApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ViewControllerIntegrationTest {

    /**
     * Represents the endpoint to call.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * The repository for the books.
     */
    @Autowired
    private BookRepository bookRepository;

    /**
     * The repository for the receipts.
     */
    @Autowired
    private ReceiptRepository receiptRepository;

    /**
     * Initialize test setup before each test.
     */
    @BeforeEach
    void setUp() {
        bookRepository.saveAll(List.of(
                new Book(1L, "Book_1", "Author_1", 5.0, 2018, 10),
                new Book(2L, "Book_2", "Author_1", 10.0, 2017, 10),
                new Book(3L, "Book_3", "Author_1", 15.0, 2018, 10),
                new Book(4L, "Book_4", "Author_1", 20.0, 2019, 10)
        ));
        
        receiptRepository.save(new Receipt(null,
                List.of(
                        new ReceiptPosition(null, 1L, "Book_1", "Author_1", 5.0, 10, 0.0, 3, 15.0),
                        new ReceiptPosition(null, 2L, "Book_2", "Author_1", 10.0, 10, 0.0, 2, 20.0),
                        new ReceiptPosition(null, 3L, "Book_3", "Author_1", 15.0, 10, 0.0, 1, 15.0)
                ),
                50.0,
                LocalDateTime.of(2021, 8, 20, 12, 0)
        ));
    }

    /**
     * Clean up test setup after each test.
     */
    @AfterEach
    void tearDown() {
        bookRepository.deleteAll();
        receiptRepository.deleteAll();
    }

    /**
     * Test that the index page is returned.
     *
     * @throws Exception The exception from calling REST endpoint
     */
    @Test
    void getIndex() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<h3>BookStore</h3>")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Book_1")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Book_2")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Book_3")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Book_4")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Author_1")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("5.00 €")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("10.00 €")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("15.00 €")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("20.00 €")));
    }

    /**
     * Test that the create book page is returned.
     *
     * @throws Exception The exception from calling REST endpoint
     */
    @Test
    void getCreateBook() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/books/create"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<h3>Create new Book</h3>")));
    }

    /**
     * Test that the book info page is returned.
     *
     * @throws Exception The exception from calling REST endpoint
     */
    @Test
    void getBookInfo() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/books/1/info"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<h3>Book: Book_1</h3>")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Author: Author_1")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Year: 2018")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Price: 5.00 €")));
    }

    /**
     * Test what happens if the book info page is accessed for a book that does not exist.
     *
     * @throws Exception The exception from calling REST endpoint
     */
    @Test
    void getBookInfoIdNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/books/6/info"))
                .andExpect(MockMvcResultMatchers.status().is(302));
    }

    /**
     * Test that the update book page is returned.
     *
     * @throws Exception The exception from calling REST endpoint
     */
    @Test
    void getBookEdit() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/books/1/edit"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(
                        "<h3>Edit Book_1</h3>")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(
                        "<input id='title' type='text' placeholder='Title' class='form-control input-md'"
                                + " required='' name=\"title\" value=\"Book_1\">")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(
                        "<input id='author' type='text' placeholder='Author' class='form-control input-md'"
                                + " required='' name=\"author\" value=\"Author_1\">")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(
                        "<input id='year' type='text' placeholder='Year' class='form-control input-md'"
                                + " required='' name=\"year\" value=\"2018\">")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(
                        "<input id='price' type='text' placeholder='Price' class='form-control input-md'"
                                + " required='' name=\"price\" value=\"5.0\">")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(
                        "<input id='amount' type='number' placeholder='Amount' class='form-control input-md'"
                                + " required='' name=\"amount\" value=\"10\">")));
    }

    /**
     * Test what happens if the update book page is accessed for a book that does not exist.
     *
     * @throws Exception The exception from calling REST endpoint
     */
    @Test
    void getBookEditIdNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/books/6/edit"))
                .andExpect(MockMvcResultMatchers.status().is(302));
    }

    /**
     * Test that the shopping cart page is returned.
     *
     * @throws Exception The exception from calling REST endpoint
     */
    @Test
    void getShoppingCart() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/shopping-cart?bookIds=1,2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<h3>Shopping cart</h3>")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Book_1 by Author_1")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("5.00 €")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Book_2 by Author_1")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("10.00 €")));
    }

    /**
     * Test that the accounting page is returned.
     *
     * @throws Exception The exception from calling REST endpoint
     */
    @Test
    void getAccounting() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/accounting"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<h3>Accounting</h3>")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Book_1 by Author_1")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("5.00 €")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Book_2 by Author_1")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("10.00 €")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Book_3 by Author_1")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("15.00 €")));
    }

    /**
     * Test the creation of a new book.
     *
     * @throws Exception The exception from calling REST endpoint
     */
    @Test
    void createBook() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/books/create")
                        .contentType("application/x-www-form-urlencoded")
                        .content("title=Book_5&author=Author_1&year=2020&price=25.0&amount=12"))
                .andExpect(MockMvcResultMatchers.status().is(302));

        Assertions.assertThat(bookRepository.count()).isEqualTo(5L);
        Assertions.assertThat(bookRepository.findById(5L)).isPresent();
        Assertions.assertThat(bookRepository.findById(5L).orElseThrow())
                .isEqualTo(new Book(5L, "Book_5", "Author_1", 25.0, 2020, 12));
    }

    /**
     * Test the creation of a new book with errors in binding result.
     *
     * @throws Exception The exception from calling REST endpoint
     */
    @Test
    void createBookBindingResultErrors() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/books/create")
                        .contentType("application/x-www-form-urlencoded")
                        .content("title=Book_5&author=Author_1&year=202&price=25.0&amount=12"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertThat(bookRepository.findById(5L)).isEmpty();
        Assertions.assertThat(bookRepository.count()).isEqualTo(4L);
    }

    /**
     * Test the creation of a new book with a combination of title and author that is not unique.
     *
     * @throws Exception The exception from calling REST endpoint
     */
    @Test
    void createBookAuthorTitleNotUnique() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/books/create")
                        .contentType("application/x-www-form-urlencoded")
                        .content("title=Book_4&author=Author_1&year=2020&price=25.0&amount=12"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertThat(bookRepository.findById(5L)).isEmpty();
        Assertions.assertThat(bookRepository.count()).isEqualTo(4L);
    }

    /**
     * Test the update of a book.
     *
     * @throws Exception The exception from calling REST endpoint
     */
    @Test
    void editBook() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/books/update/4")
                        .contentType("application/x-www-form-urlencoded")
                        .content("id=4&title=Book_4&author=Author_1&year=2020&price=25.0&amount=15"))
                .andExpect(MockMvcResultMatchers.status().is(302));

        Assertions.assertThat(bookRepository.findById(4L)).isPresent();
        Assertions.assertThat(bookRepository.findById(4L))
                .contains(new Book(4L, "Book_4", "Author_1", 25.0, 2020, 15));
    }

    /**
     * Test the update of a book with errors in binding result.
     *
     * @throws Exception The exception from calling REST endpoint
     */
    @Test
    void editBookBindingResultErrors() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/books/update/4")
                        .contentType("application/x-www-form-urlencoded")
                        .content("id=4&title=Book_4&author=Author_1&year=202&price=40.0&amount=10"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertThat(bookRepository.findById(4L)).isPresent();
        Assertions.assertThat(bookRepository.findById(4L))
                .contains(new Book(4L, "Book_4", "Author_1", 20.0, 2019, 10));
    }

    /**
     * Test the update of a book with an ID that does not match the books ID.
     *
     * @throws Exception The exception from calling REST endpoint
     */
    @Test
    void editBookNoBookWithId() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/books/update/5")
                        .contentType("application/x-www-form-urlencoded")
                        .content("id=4&title=Book_3&author=Author_1&year=2020&price=20.0&amount=10"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertThat(bookRepository.findById(4L)).isPresent();
        Assertions.assertThat(bookRepository.findById(4L))
                .contains(new Book(4L, "Book_4", "Author_1", 20.0, 2019, 10));
    }

    /**
     * Test the update of a book with a combination of title and author that is not unique.
     *
     * @throws Exception The exception from calling REST endpoint
     */
    @Test
    void editBookAuthorTitleNotUnique() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/books/update/4")
                        .contentType("application/x-www-form-urlencoded")
                        .content("id=4&title=Book_3&author=Author_1&year=2020&price=20.0&amount=10"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertThat(bookRepository.findById(4L)).isPresent();
        Assertions.assertThat(bookRepository.findById(4L))
                .contains(new Book(4L, "Book_4", "Author_1", 20.0, 2019, 10));
    }

    /**
     * Test the buying of books with errors in binding result.
     *
     * @throws Exception The exception from calling REST endpoint
     */
    @Test
    void buyBookBindingResultErrors() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/books/buy")
                        .contentType("application/x-www-form-urlencoded")
                        .content("receiptPositions%5B0%5D.bookId=1&"
                                        + "receiptPositions%5B0%5D.title=Book_1&"
                                        + "receiptPositions%5B0%5D.author=Author_1&"
                                        + "receiptPositions%5B0%5D.price=5.0&"
                                        + "receiptPositions%5B0%5D.cost=15.0&"
                                        + "receiptPositions%5B0%5D.amount=3&"
                                        + "receiptPositions%5B0%5D.discountPercentage=30.0&"
                                        + "receiptPositions%5B1%5D.bookId=2&"
                                        + "receiptPositions%5B1%5D.title=Book_2&"
                                        + "receiptPositions%5B1%5D.author=Author_1&"
                                        + "receiptPositions%5B1%5D.price=10.0&"
                                        + "receiptPositions%5B1%5D.cost=20.0&"
                                        + "receiptPositions%5B1%5D.amount=2&"
                                        + "receiptPositions%5B1%5D.discountPercentage=0.0&"
                                        + "receiptPositions%5B2%5D.bookId=3&"
                                        + "receiptPositions%5B2%5D.title=Book_3&"
                                        + "receiptPositions%5B2%5D.author=Author_1&"
                                        + "receiptPositions%5B2%5D.price=15.0&"
                                        + "receiptPositions%5B2%5D.cost=15.0&"
                                        + "receiptPositions%5B2%5D.amount=1&"
                                        + "receiptPositions%5B2%5D.discountPercentage=0.0&"
                                ))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        Assertions.assertThat(receiptRepository.count()).isEqualTo(1L);
        Assertions.assertThat(bookRepository.findAllById(List.of(1L, 2L, 3L))).isEqualTo(List.of(
                new Book(1L, "Book_1", "Author_1", 5.0, 2018, 10),
                new Book(2L, "Book_2", "Author_1", 10.0, 2017, 10),
                new Book(3L, "Book_3", "Author_1", 15.0, 2018, 10)
        ));
    }

    /**
     * Test the buying of books.
     *
     * @throws Exception The exception from calling REST endpoint
     */
    @Test
    void buyBook() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/books/buy")
                        .contentType("application/x-www-form-urlencoded")
                        .content("receiptPositions%5B0%5D.bookId=1&"
                                + "receiptPositions%5B0%5D.title=Book_1&"
                                + "receiptPositions%5B0%5D.author=Author_1&"
                                + "receiptPositions%5B0%5D.price=5.0&"
                                + "receiptPositions%5B0%5D.cost=15.0&"
                                + "receiptPositions%5B0%5D.amount=3&"
                                + "receiptPositions%5B0%5D.discountPercentage=0.0&"
                                + "receiptPositions%5B1%5D.bookId=2&"
                                + "receiptPositions%5B1%5D.title=Book_2&"
                                + "receiptPositions%5B1%5D.author=Author_1&"
                                + "receiptPositions%5B1%5D.price=10.0&"
                                + "receiptPositions%5B1%5D.cost=20.0&"
                                + "receiptPositions%5B1%5D.amount=2&"
                                + "receiptPositions%5B1%5D.discountPercentage=0.0&"
                                + "receiptPositions%5B2%5D.bookId=3&"
                                + "receiptPositions%5B2%5D.title=Book_3&"
                                + "receiptPositions%5B2%5D.author=Author_1&"
                                + "receiptPositions%5B2%5D.price=15.0&"
                                + "receiptPositions%5B2%5D.cost=15.0&"
                                + "receiptPositions%5B2%5D.amount=1&"
                                + "receiptPositions%5B2%5D.discountPercentage=0.0&"
                        ))
                .andExpect(MockMvcResultMatchers.status().is(302));

        Assertions.assertThat(receiptRepository.count()).isEqualTo(2L);
        Assertions.assertThat(bookRepository.findAllById(List.of(1L, 2L, 3L))).isEqualTo(List.of(
                new Book(1L, "Book_1", "Author_1", 5.0, 2018, 7),
                new Book(2L, "Book_2", "Author_1", 10.0, 2017, 8),
                new Book(3L, "Book_3", "Author_1", 15.0, 2018, 9)
        ));
    }

    /**
     * Test the deleting of a book.
     *
     * @throws Exception The exception from calling REST endpoint
     */
    @Test
    void deleteBook() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/books/delete/4"))
                .andExpect(MockMvcResultMatchers.status().is(302));

        Assertions.assertThat(bookRepository.count()).isEqualTo(3L);
        Assertions.assertThat(bookRepository.findById(4L)).isEmpty();
    }

    /**
     * Test the deleting of a book with an ID that does not exist.
     *
     * @throws Exception The exception from calling REST endpoint
     */
    @Test
    void deleteBookNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/books/delete/5"))
                .andExpect(MockMvcResultMatchers.status().is(302));

        Assertions.assertThat(bookRepository.count()).isEqualTo(4L);
        Assertions.assertThat(bookRepository.findById(4L)).isPresent();
        Assertions.assertThat(bookRepository.findById(4L))
                .contains(new Book(4L, "Book_4", "Author_1", 20.0, 2019, 10));
    }
}
