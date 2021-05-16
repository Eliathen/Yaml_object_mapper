package com.szymanski.yamlobjectmapper.converters.field;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateConverter implements FieldConverter<LocalDate> {
    @Override
    public LocalDate convertToValue(String value, String pattern) {
        return LocalDate.parse(value.substring(1, value.length() - 2), DateTimeFormatter.ofPattern(pattern));
    }

    @Override
    public String convertToString(LocalDate value, String pattern) {
        return "\"" + value.format(DateTimeFormatter.ofPattern(pattern)) + "\"";
    }
}
