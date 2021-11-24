package de.adesso.bookstore.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for receipt positions.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptPositionDto {

    /**
     * The ID of the receipt position.
     */
    private Long id;

    /**
     * The ID of the book corresponding to the receipt position.
     */
    @NotNull
    private Long bookId;

    /**
     * The title of the book corresponding to the receipt position.
     */
    @NotNull
    @Size(min = 2, max = 30, message = "Title must be at least 2 and at most 30 characters long.")
    private String title;

    /**
     * The author of the book corresponding to the receipt position.
     */
    @NotNull
    @Size(min = 2, max = 20, message = "Author must be at least 2 and at most 20 characters long.")
    private String author;

    /**
     * The price of the book corresponding to the receipt position.
     */
    @Min(value = 1, message = "Price must be at least 1.")
    private double price;

    /**
     * The amount of copies available of the book corresponding to the receipt position.
     */
    private double currentStorageVolume;

    /**
     * The discount percentage for the receipt position.
     */
    @NotNull
    @Min(value = 0, message = "Discount percentage must be at least 0.")
    @Max(value = 20, message = "Discount percentage must be at most 20.")
    private double discountPercentage;

    /**
     * The amount for the receipt position.
     */
    @NotNull
    @Min(value = 0, message = "Amount must be at least 0.")
    @Max(value = 5, message = "Amount must be at most 5.")
    private int amount;

    /**
     * The costs for the receipt position.
     */
    @NotNull
    private double cost;
}
