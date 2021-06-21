package com.szymanski.yamlobjectmapper.converters;

import com.szymanski.yamlobjectmapper.exceptions.NotFoundConverterException;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

public class ConverterManager {

    HashMap<Class<?>, FieldConverter> fieldsConverter = new HashMap<>();

    public ConverterManager() {

        fieldsConverter.put(int.class, new IntegerConverter());
        fieldsConverter.put(Integer.class, new IntegerConverter());

        fieldsConverter.put(double.class, new DoubleConverter());
        fieldsConverter.put(Double.class, new DoubleConverter());


        fieldsConverter.put(float.class, new FloatConverter());
        fieldsConverter.put(Float.class, new FloatConverter());

        fieldsConverter.put(BigInteger.class, new BigIntegerConverter());


        fieldsConverter.put(Long.class, new LongConverter());
        fieldsConverter.put(long.class, new LongConverter());

        fieldsConverter.put(Boolean.class, new BooleanConverter());
        fieldsConverter.put(boolean.class, new BooleanConverter());

        fieldsConverter.put(LocalDate.class, new DateConverter());
        fieldsConverter.put(LocalDateTime.class, new DateTimeConverter());

        fieldsConverter.put(String.class, new StringConverter());
    }

    public String convertToString(Class<?> clazz, Object value, String pattern) {
        if (!fieldsConverter.containsKey(clazz)) {
            throw new NotFoundConverterException(clazz.toString());
        }
        return fieldsConverter.get(clazz).convertToString(value, pattern);
    }

    public Object convertToValue(Class<?> clazz, String value, String pattern){
        if (!fieldsConverter.containsKey(clazz)) {
            throw new NotFoundConverterException(clazz.toString());
        }
        return fieldsConverter.get(clazz).convertToValue(value, pattern);
    }
}
