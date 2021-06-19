package com.szymanski.yamlobjectmapper.converters;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeConverter implements FieldConverter<LocalDateTime> {
    @Override
    public LocalDateTime convertToValue(String value, String pattern) {
        return LocalDateTime.parse(value, DateTimeFormatter.ofPattern(pattern));
    }

    @Override
    public String convertToString(LocalDateTime value, String pattern) {
        return value.format(DateTimeFormatter.ofPattern(pattern));
    }

}
