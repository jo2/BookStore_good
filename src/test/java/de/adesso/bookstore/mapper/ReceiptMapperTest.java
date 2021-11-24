package de.adesso.bookstore.mapper;

import de.adesso.bookstore.dto.ReceiptDto;
import de.adesso.bookstore.dto.ReceiptPositionDto;
import de.adesso.bookstore.entities.Receipt;
import de.adesso.bookstore.entities.ReceiptPosition;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test for class ReceiptMapper.
 */
class ReceiptMapperTest {

    /**
     * Object under test.
     */
    private final ReceiptMapper receiptMapper = new ReceiptMapper();

    /**
     * Test mapping from domain object to data transfer object.
     */
    @Test
    void mapDomainObject() {
        Assertions.assertThat(receiptMapper.mapDomainObject(new Receipt(null,
                List.of(
                        new ReceiptPosition(null, 1L, "Book_1", "Author_1", 5.0, 10, 0.0, 3, 15.0),
                        new ReceiptPosition(null, 2L, "Book_2", "Author_1", 10.0, 10, 0.0, 2, 20.0),
                        new ReceiptPosition(null, 3L, "Book_3", "Author_1", 15.0, 10, 0.0, 1, 15.0)
                ),
                50.0,
                LocalDateTime.of(2021, 8, 20, 12, 0)
        )))
                .isEqualTo(new ReceiptDto(null,
                        List.of(
                                new ReceiptPositionDto(null, 1L, "Book_1", "Author_1", 5.0, 10, 0.0, 3, 15.0),
                                new ReceiptPositionDto(null, 2L, "Book_2", "Author_1", 10.0, 10, 0.0, 2, 20.0),
                                new ReceiptPositionDto(null, 3L, "Book_3", "Author_1", 15.0, 10, 0.0, 1, 15.0)
                        ),
                        50.0,
                        LocalDateTime.of(2021, 8, 20, 12, 0)
                ));
    }

    /**
     * Test mapping from data transfer object to domain object.
     */
    @Test
    void mapDtoObject() {
        Assertions.assertThat(receiptMapper.mapDtoObject(new ReceiptDto(null,
                List.of(
                        new ReceiptPositionDto(null, 1L, "Book_1", "Author_1", 5.0, 10, 0.0, 3, 15.0),
                        new ReceiptPositionDto(null, 2L, "Book_2", "Author_1", 10.0, 10, 0.0, 2, 20.0),
                        new ReceiptPositionDto(null, 3L, "Book_3", "Author_1", 15.0, 10, 0.0, 1, 15.0)
                ),
                50.0,
                LocalDateTime.of(2021, 8, 20, 12, 0)
        )))
                .isEqualTo(new Receipt(null,
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
     * Test mapping from list of domain object to list of data transfer object.
     */
    @Test
    void mapDomainObjects() {
        Assertions.assertThat(receiptMapper.mapDomainObjects(List.of(
                new Receipt(null,
                        List.of(
                                new ReceiptPosition(null, 1L, "Book_1", "Author_1", 5.0, 10, 0.0, 3, 15.0),
                                new ReceiptPosition(null, 2L, "Book_2", "Author_1", 10.0, 10, 0.0, 2, 20.0),
                                new ReceiptPosition(null, 3L, "Book_3", "Author_1", 15.0, 10, 0.0, 1, 15.0)
                        ),
                        50.0,
                        LocalDateTime.of(2021, 8, 20, 12, 0)),
                new Receipt(null,
                        List.of(
                                new ReceiptPosition(null, 4L, "Book_1", "Author_1", 5.0, 10, 0.0, 3, 15.0),
                                new ReceiptPosition(null, 5L, "Book_2", "Author_1", 10.0, 10, 0.0, 2, 20.0),
                                new ReceiptPosition(null, 6L, "Book_3", "Author_1", 15.0, 10, 0.0, 1, 15.0)
                        ),
                        50.0,
                        LocalDateTime.of(2021, 8, 20, 12, 0))
        )))
                .isEqualTo(List.of(
                        new ReceiptDto(null,
                                List.of(
                                        new ReceiptPositionDto(null, 1L, "Book_1", "Author_1", 5.0, 10, 0.0, 3, 15.0),
                                        new ReceiptPositionDto(null, 2L, "Book_2", "Author_1", 10.0, 10, 0.0, 2, 20.0),
                                        new ReceiptPositionDto(null, 3L, "Book_3", "Author_1", 15.0, 10, 0.0, 1, 15.0)
                                ),
                                50.0,
                                LocalDateTime.of(2021, 8, 20, 12, 0)),
                        new ReceiptDto(null,
                                List.of(
                                        new ReceiptPositionDto(null, 4L, "Book_1", "Author_1", 5.0, 10, 0.0, 3, 15.0),
                                        new ReceiptPositionDto(null, 5L, "Book_2", "Author_1", 10.0, 10, 0.0, 2, 20.0),
                                        new ReceiptPositionDto(null, 6L, "Book_3", "Author_1", 15.0, 10, 0.0, 1, 15.0)
                                ),
                                50.0,
                                LocalDateTime.of(2021, 8, 20, 12, 0))
                ));
    }

    /**
     * Test mapping from list of data transfer object to list of domain object.
     */
    @Test
    void mapDtoObjects() {
        Assertions.assertThat(receiptMapper.mapDtoObjects(List.of(
                new ReceiptDto(null,
                        List.of(
                                new ReceiptPositionDto(null, 1L, "Book_1", "Author_1", 5.0, 10, 0.0, 3, 15.0),
                                new ReceiptPositionDto(null, 2L, "Book_2", "Author_1", 10.0, 10, 0.0, 2, 20.0),
                                new ReceiptPositionDto(null, 3L, "Book_3", "Author_1", 15.0, 10, 0.0, 1, 15.0)
                        ),
                        50.0,
                        LocalDateTime.of(2021, 8, 20, 12, 0)),
                new ReceiptDto(null,
                        List.of(
                                new ReceiptPositionDto(null, 4L, "Book_1", "Author_1", 5.0, 10, 0.0, 3, 15.0),
                                new ReceiptPositionDto(null, 5L, "Book_2", "Author_1", 10.0, 10, 0.0, 2, 20.0),
                                new ReceiptPositionDto(null, 6L, "Book_3", "Author_1", 15.0, 10, 0.0, 1, 15.0)
                        ),
                        50.0,
                        LocalDateTime.of(2021, 8, 20, 12, 0))
        )))
                .isEqualTo(List.of(
                        new Receipt(null,
                                List.of(
                                        new ReceiptPosition(null, 1L, "Book_1", "Author_1", 5.0, 10, 0.0, 3, 15.0),
                                        new ReceiptPosition(null, 2L, "Book_2", "Author_1", 10.0, 10, 0.0, 2, 20.0),
                                        new ReceiptPosition(null, 3L, "Book_3", "Author_1", 15.0, 10, 0.0, 1, 15.0)
                                ),
                                50.0,
                                LocalDateTime.of(2021, 8, 20, 12, 0)),
                        new Receipt(null,
                                List.of(
                                        new ReceiptPosition(null, 4L, "Book_1", "Author_1", 5.0, 10, 0.0, 3, 15.0),
                                        new ReceiptPosition(null, 5L, "Book_2", "Author_1", 10.0, 10, 0.0, 2, 20.0),
                                        new ReceiptPosition(null, 6L, "Book_3", "Author_1", 15.0, 10, 0.0, 1, 15.0)
                                ),
                                50.0,
                                LocalDateTime.of(2021, 8, 20, 12, 0))
                ));
    }
}
