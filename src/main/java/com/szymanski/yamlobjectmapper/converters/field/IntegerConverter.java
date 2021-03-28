package com.szymanski.yamlobjectmapper.converters.field;

public class IntegerConverter implements FieldConverter<Integer> {
    @Override
    public Integer convertToValue(String value, String pattern) {

        return Integer.parseInt(value);
    }

    @Override
    public String convertToString(Integer value, String pattern) {
        return value.toString();
    }
}
