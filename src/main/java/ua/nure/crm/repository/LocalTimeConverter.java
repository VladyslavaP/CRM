package ua.nure.crm.repository;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Time;
import java.time.LocalTime;

import static java.util.Optional.ofNullable;

@Converter(autoApply = true)
public class LocalTimeConverter implements AttributeConverter<LocalTime, Time> {

    @Override
    public Time convertToDatabaseColumn(LocalTime localTime) {
        return ofNullable(localTime).map(Time::valueOf).orElse(null);
    }

    @Override
    public LocalTime convertToEntityAttribute(Time time) {
        return ofNullable(time).map(Time::toLocalTime).orElse(null);
    }
}
