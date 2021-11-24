package de.adesso.bookstore.controller;

import de.adesso.bookstore.dto.BookDto;
import de.adesso.bookstore.dto.ReceiptDto;
import de.adesso.bookstore.entities.Book;
import de.adesso.bookstore.mapper.BookMapper;
import de.adesso.bookstore.mapper.ReceiptMapper;
import de.adesso.bookstore.services.BookstoreService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller to deliver thymeleaf pages.
 */
@Controller
@RequiredArgsConstructor
public class ViewController {

    /**
     * Redirect endpoint.
     */
    private static final String REDIRECT = "redirect:/";

    /**
     * Create book page endpoint.
     */
    private static final String CREATE_BOOK = "createBook";

    /**
     * Update book page endpoint.
     */
    private static final String UPDATE_BOOK = "updateBook";

    /**
     * Service for bookstore management functions.
     */
    private final BookstoreService bookstoreService;

    /**
     * Mapper for books.
     */
    private final BookMapper bookMapper = new BookMapper();

    /**
     * Mapper for receipts.
     */
    private final ReceiptMapper receiptMapper = new ReceiptMapper();

    /**
     * Get index page.
     *
     * @param model The data model
     * @return The page
     */
    @GetMapping({"/", "/index", "/books"})
    public String getIndex(Model model) {
        model.addAttribute("books", bookMapper.mapDomainObjects(bookstoreService.getAllBooks()));
        return "index";
    }

    /**
     * Get create book page.
     *
     * @param model The data model
     * @return The page
     */
    @GetMapping("/books/create")
    public String getCreateBook(Model model) {
        model.addAttribute("bookDto", bookMapper.mapDomainObject(new Book()));
        return CREATE_BOOK;
    }

    /**
     * Get book info page.
     *
     * @param id The ID of the book
     * @param model The data model
     * @return The page
     */
    @GetMapping("/books/{id}/info")
    public String getBookInfo(@PathVariable(name = "id") Long id, Model model) {
        if (!bookstoreService.existsById(id)) {
            return REDIRECT;
        }

        model.addAttribute("book", bookMapper.mapDomainObject(bookstoreService.findById(id)));
        return "bookInfo";
    }

    /**
     * Get update book page.
     *
     * @param id The ID of the book
     * @param model The data model
     * @return The page
     */
    @GetMapping("/books/{id}/edit")
    public String getBookEdit(@PathVariable(name = "id") Long id, Model model) {
        if (!bookstoreService.existsById(id)) {
            return REDIRECT;
        }

        model.addAttribute("bookDto", bookMapper.mapDomainObject(bookstoreService.findById(id)));
        return UPDATE_BOOK;
    }

    /**
     * Get shopping cart page.
     *
     * @param bookIds The IDs of the books in the shopping cart
     * @param model The data model
     * @return The page
     */
    @GetMapping("/shopping-cart")
    public String getShoppingCart(@RequestParam List<Long> bookIds, Model model) {
        model.addAttribute("receipt", receiptMapper.mapDomainObject(bookstoreService.generateReceipt(bookIds)));
        return "shoppingCart";
    }

    /**
     * Get accounting page.
     *
     * @param model The data model
     * @return The page
     */
    @GetMapping("/accounting")
    public String getAccounting(Model model) {
        model.addAttribute("receipts", receiptMapper.mapDomainObjects(bookstoreService.getAllReceipts()));
        return "accounting";
    }

    /**
     * Create a new book.
     *
     * @param bookDto The book to create
     * @param bindingResult The corresponding binding result
     * @return The new page
     */
    @PostMapping("/books/create")
    public String createBook(@Valid BookDto bookDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return CREATE_BOOK;
        }
        try {
            bookstoreService.addBook(bookMapper.mapDtoObject(bookDto));
        } catch (DataIntegrityViolationException ex) {
            bindingResult.addError(new ObjectError("title", "Combination of title and author must be unique."));
            return CREATE_BOOK;
        }
        return REDIRECT;
    }

    /**
     * Update a book.
     *
     * @param id The ID of the book to update
     * @param bookDto The book to update
     * @param bindingResult The corresponding binding result
     * @return The new page
     */
    @PostMapping("/books/update/{id}")
    public String editBook(@PathVariable(name = "id") Long id, @Valid BookDto bookDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return UPDATE_BOOK;
        }
        if (!bookstoreService.existsById(id)) {
            bindingResult.addError(new ObjectError("id", "No book with this id exists."));
            return UPDATE_BOOK;
        }
        try {
            bookstoreService.updateBook(id, bookMapper.mapDtoObject(bookDto));
        } catch (DataIntegrityViolationException ex) {
            bindingResult.addError(new ObjectError("title", "Combination of title and author must be unique."));
            return UPDATE_BOOK;
        }
        return REDIRECT;
    }

    /**
     * Buy a list of books contained in a receipt.
     *
     * @param receiptDto The receipt containing the books to buy
     * @return The new page
     */
    @PostMapping("/books/buy")
    public String buyBook(@Valid ReceiptDto receiptDto) {
        bookstoreService.buyBooks(receiptMapper.mapDtoObject(receiptDto));
        return REDIRECT;
    }

    /**
     * Delete a book by its ID.
     *
     * @param id The ID of the book to delete
     * @return The new page
     */
    @PostMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable(name = "id") Long id) {
        if (!bookstoreService.existsById(id)) {
            return REDIRECT;
        }

        bookstoreService.removeBook(id);
        return REDIRECT;
    }
}
