package com.szymanski.yamlobjectmapper.converters;

import java.math.BigInteger;

public class BigIntegerConverter implements FieldConverter<BigInteger> {

    @Override
    public BigInteger convertToValue(String value, String pattern) {
        return BigInteger.valueOf(Long.parseLong(value));
    }

    @Override
    public String convertToString(BigInteger value, String pattern) {
        return String.valueOf(value);
    }
}
