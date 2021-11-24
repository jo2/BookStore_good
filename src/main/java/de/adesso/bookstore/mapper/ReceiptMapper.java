package de.adesso.bookstore.mapper;

import de.adesso.bookstore.dto.ReceiptDto;
import de.adesso.bookstore.entities.Receipt;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for receipts.
 */
public class ReceiptMapper {

    /**
     * The mapper for the receipt positions.
     */
    private final ReceiptPositionMapper receiptPositionMapper = new ReceiptPositionMapper();

    /**
     * Map a receipt to its data transfer object.
     *
     * @param receipt The receipt to map
     * @return The mapped data transfer object
     */
    public ReceiptDto mapDomainObject(Receipt receipt) {
        return new ReceiptDto(receipt.getId(),
                receiptPositionMapper.mapDomainObjects(receipt.getReceiptPositions()),
                receipt.getSum(),
                receipt.getTimestamp());
    }

    /**
     * Map a data transfer object to its receipt.
     *
     * @param receiptDto The data transfer object to map
     * @return The mapped receipt
     */
    public Receipt mapDtoObject(ReceiptDto receiptDto) {
        return new Receipt(receiptDto.getId(),
                receiptPositionMapper.mapDtoObjects(receiptDto.getReceiptPositions()),
                receiptDto.getSum(),
                receiptDto.getTimestamp());
    }

    /**
     * Map receipts to their data transfer objects.
     *
     * @param receipts The receipts to map
     * @return The mapped data transfer objects
     */
    public List<ReceiptDto> mapDomainObjects(List<Receipt> receipts) {
        return receipts.stream().map(this::mapDomainObject).collect(Collectors.toList());
    }

    /**
     * Map data transfer objects to their receipts.
     *
     * @param receiptDtos The data transfer objects to map
     * @return The mapped receipts
     */
    public List<Receipt> mapDtoObjects(List<ReceiptDto> receiptDtos) {
        return receiptDtos.stream().map(this::mapDtoObject).collect(Collectors.toList());
    }
}
