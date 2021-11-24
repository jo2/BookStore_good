package de.adesso.bookstore.mapper;

import de.adesso.bookstore.dto.ReceiptPositionDto;
import de.adesso.bookstore.entities.ReceiptPosition;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for receipt positions.
 */
public class ReceiptPositionMapper {

    /**
     * Map a receipt position to its data transfer object.
     *
     * @param receiptPosition The receipt position to map
     * @return The mapped data transfer object
     */
    public ReceiptPositionDto mapDomainObject(ReceiptPosition receiptPosition) {
        return new ReceiptPositionDto(receiptPosition.getId(),
                receiptPosition.getBookId(),
                receiptPosition.getTitle(),
                receiptPosition.getAuthor(),
                receiptPosition.getPrice(),
                receiptPosition.getCurrentStorageVolume(),
                receiptPosition.getDiscountPercentage(),
                receiptPosition.getAmount(),
                receiptPosition.getCost());
    }

    /**
     * Map a data transfer object to its receipt position.
     *
     * @param receiptPositionDto The data transfer object to map
     * @return The mapped receipt position
     */
    public ReceiptPosition mapDtoObject(ReceiptPositionDto receiptPositionDto) {
        return new ReceiptPosition(receiptPositionDto.getId(),
                receiptPositionDto.getBookId(),
                receiptPositionDto.getTitle(),
                receiptPositionDto.getAuthor(),
                receiptPositionDto.getPrice(),
                receiptPositionDto.getCurrentStorageVolume(),
                receiptPositionDto.getDiscountPercentage(),
                receiptPositionDto.getAmount(),
                receiptPositionDto.getCost());
    }

    /**
     * Map receipt positions to their data transfer objects.
     *
     * @param receiptPositions The receipt positions to map
     * @return The mapped data transfer objects
     */
    public List<ReceiptPositionDto> mapDomainObjects(List<ReceiptPosition> receiptPositions) {
        return receiptPositions.stream().map(this::mapDomainObject).collect(Collectors.toList());
    }

    /**
     * Map data transfer objects to their receipt positions.
     *
     * @param receiptPositionDtos The data transfer objects to map
     * @return The mapped receipt positions
     */
    public List<ReceiptPosition> mapDtoObjects(List<ReceiptPositionDto> receiptPositionDtos) {
        return receiptPositionDtos.stream().map(this::mapDtoObject).collect(Collectors.toList());
    }
}
