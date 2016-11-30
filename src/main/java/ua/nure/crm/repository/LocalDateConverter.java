package ua.nure.crm.repository;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

import static java.util.Optional.ofNullable;

@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate date) {
        return ofNullable(date).map(Instant::from)
                .map(Date::from).orElse(null);
    }

    @Override
    public LocalDate convertToEntityAttribute(Date value) {
        return ofNullable(value).map(Date::toInstant)
                .map(LocalDate::from).orElse(null);
    }
}
