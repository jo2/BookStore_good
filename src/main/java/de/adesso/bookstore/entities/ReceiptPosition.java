package de.adesso.bookstore.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class for receipt positions.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class ReceiptPosition {

    /**
     * The ID of the receipt position.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The ID of the book corresponding to the receipt position.
     */
    private Long bookId;

    /**
     * The title of the book corresponding to the receipt position.
     */
    private String title;

    /**
     * The author of the book corresponding to the receipt position.
     */
    private String author;

    /**
     * The price of the book corresponding to the receipt position.
     */
    private double price;

    /**
     * The amount of copies available of the book corresponding to the receipt position.
     */
    private double currentStorageVolume;

    /**
     * The discount percentage for the receipt position.
     */
    private double discountPercentage;

    /**
     * The amount for the receipt position.
     */
    private int amount;

    /**
     * The costs for the receipt position.
     */
    private double cost;
}
