package de.adesso.bookstore.dto;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for receipts.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptDto {

    /**
     * The ID of the receipt.
     */
    private Long id;

    /**
     * The receipt positions of the receipt.
     */
    @Valid
    private List<ReceiptPositionDto> receiptPositions;

    /**
     * The sum over all costs of the receipt positions of the receipt.
     */
    private double sum;

    /**
     * The timestamp at that the receipt was created.
     */
    private LocalDateTime timestamp;
}
