package com.szymanski.yamlobjectmapper.converters.field;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataConverter implements FieldConverter<LocalDate>{
    @Override
    public LocalDate convertToValue(String value, String pattern) {
        return LocalDate.parse(value, DateTimeFormatter.ofPattern(pattern));
    }

    @Override
    public String convertToString(LocalDate value, String pattern) {
        return value.format(DateTimeFormatter.ofPattern(pattern));
    }
}
