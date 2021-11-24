package de.adesso.bookstore.entities;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class for receipts.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Receipt {

    /**
     * The ID of the receipt.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The receipt positions of the receipt.
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "receipt_id")
    private List<ReceiptPosition> receiptPositions;

    /**
     * The sum over all costs of the receipt positions of the receipt.
     */
    private double sum;

    /**
     * The timestamp at that the receipt was created.
     */
    private LocalDateTime timestamp;
}
