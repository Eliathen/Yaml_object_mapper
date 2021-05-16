package com.szymanski.yamlobjectmapper.converters.field;

import com.szymanski.yamlobjectmapper.exceptions.NotFoundConverterException;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ConverterManager {

    Map<Class<?>, FieldConverter> fieldsConverter = new HashMap<>();

    public ConverterManager() {

        fieldsConverter.put(int.class, new IntegerConverter());
        fieldsConverter.put(Integer.class, new IntegerConverter());

        fieldsConverter.put(double.class, new DoubleConverter());
        fieldsConverter.put(Double.class, new DoubleConverter());


        fieldsConverter.put(float.class, new FloatConverter());
        fieldsConverter.put(Float.class, new FloatConverter());

        fieldsConverter.put(BigInteger.class, new BigIntegerConverter());
        fieldsConverter.put(Long.class, new LongConverter());

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
}
