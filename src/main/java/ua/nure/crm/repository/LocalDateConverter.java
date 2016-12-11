package ua.nure.crm.repository;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.*;

import static java.util.Optional.ofNullable;

@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate date) {
        return ofNullable(date).map(Date::valueOf).orElse(null);
    }

    private ZonedDateTime atStartOfDayInDefaultZone(LocalDate date) {
        return date.atStartOfDay().atZone(ZoneId.systemDefault());
    }

    @Override
    public LocalDate convertToEntityAttribute(Date value) {
        return ofNullable(value).map(Date::toLocalDate)
                .orElse(null);
    }
}
