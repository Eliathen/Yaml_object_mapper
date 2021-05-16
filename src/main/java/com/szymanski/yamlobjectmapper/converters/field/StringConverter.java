package com.szymanski.yamlobjectmapper.converters.field;

public class StringConverter implements FieldConverter<String> {
    @Override
    public String convertToValue(String value, String pattern) {
        return value.substring(1, value.length() - 2); //TO DO test
    }

    @Override
    public String convertToString(String value, String pattern) {
        return "\"" + value + "\"";
    }
}
