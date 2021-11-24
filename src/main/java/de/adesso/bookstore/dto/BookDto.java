package de.adesso.bookstore.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for books.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    /**
     * The ID of the book.
     */
    private Long id;

    /**
     * The title of the book.
     */
    @NotNull
    @Size(min = 2, max = 30, message = "Title must be at least 2 and at most 30 characters long.")
    private String title;

    /**
     * The author of the book.
     */
    @NotNull
    @Size(min = 2, max = 20, message = "Author must be at least 2 and at most 20 characters long.")
    private String author;

    /**
     * The price of the book.
     */
    @Min(value = 1, message = "Price must be at least 1.")
    private double price;

    /**
     * The release year of the book.
     */
    @Min(value = 1000, message = "Year must be after 1000 AC.")
    @Max(value = 2050, message = "Year can't be after 2050 AC.")
    private int year;

    /**
     * The amount of copies available of the book.
     */
    @Min(value = 0, message = "Amount must be at least 0.")
    private int amount;
}
