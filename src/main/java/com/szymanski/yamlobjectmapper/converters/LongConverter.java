package com.szymanski.yamlobjectmapper.converters;

public class LongConverter implements FieldConverter<Long> {
    @Override
    public Long convertToValue(String value, String pattern) {

        return Long.parseLong(value);
    }

    @Override
    public String convertToString(Long value, String pattern) {
        return value.toString();
    }
}
