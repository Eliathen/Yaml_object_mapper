package com.szymanski.yamlobjectmapper.converters.field;

public interface FieldConverter <T>{

    T convertToValue(String value, String pattern);

    String convertToString(T value, String pattern);

}
