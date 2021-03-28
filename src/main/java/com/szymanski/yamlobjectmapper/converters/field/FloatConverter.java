package com.szymanski.yamlobjectmapper.converters.field;

public class FloatConverter implements FieldConverter<Float> {

    @Override
    public Float convertToValue(String value, String pattern) {
            return Float.parseFloat(value);
    }

    @Override
    public String convertToString(Float value, String pattern) {
        return Float.toString(value);
    }

}
