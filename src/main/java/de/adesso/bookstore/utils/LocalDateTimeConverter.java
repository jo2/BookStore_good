package de.adesso.bookstore.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Converter for conversion between LocalDateTime and Timestamp.
 */
@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    /**
     * Convert a LocalDateTime to a Timestamp.
     *
     * @param locDate The LocalDateTime to convert
     * @return The converted Timestamp
     */
    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime locDate) {
        return locDate == null ? null : Timestamp.valueOf(locDate);
    }

    /**
     * Convert a Timestamp to a LocalDateTime.
     *
     * @param sqlDate The Timestamp to convert
     * @return The converted LocalDateTime
     */
    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp sqlDate) {
        return sqlDate == null ? null : sqlDate.toLocalDateTime();
    }
}
