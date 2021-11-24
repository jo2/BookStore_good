package de.adesso.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application class to start Spring Boot application.
 */
@SpringBootApplication
public class BookstoreApplication {

    /**
     * Main method to start application.
     *
     * @param args The Arguments to consider during startup
     */
    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }

}
