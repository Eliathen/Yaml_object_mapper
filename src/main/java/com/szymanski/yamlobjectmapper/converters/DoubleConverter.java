package com.szymanski.yamlobjectmapper.converters;

public class DoubleConverter implements FieldConverter<Double> {
    @Override
    public Double convertToValue(String value, String pattern) {
            return Double.parseDouble(value);
    }

    @Override
    public String convertToString(Double value, String pattern) {
        return Double.toString(value);
    }
}
