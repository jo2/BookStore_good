package de.adesso.bookstore.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class for books.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"title", "author"}))
public class Book {

    /**
     * The ID of the book.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The title of the book.
     */
    private String title;

    /**
     * The author of the book.
     */
    private String author;

    /**
     * The price of the book.
     */
    private double price;

    /**
     * The release year of the book.
     */
    private int year;

    /**
     * The amount of copies available of the book.
     */
    private int amount;
}
