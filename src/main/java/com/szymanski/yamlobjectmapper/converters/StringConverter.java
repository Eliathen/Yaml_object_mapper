package com.szymanski.yamlobjectmapper.converters;

public class StringConverter implements FieldConverter<String> {
    @Override
    public String convertToValue(String value, String pattern) {
        return value.substring(1, value.length() - 1); //TO DO test
    }

    @Override
    public String convertToString(String value, String pattern) {
        if(value == null) return null;
        return "\"" + value + "\"";
    }
}
