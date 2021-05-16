package com.szymanski.yamlobjectmapper.converters.field;

import com.szymanski.yamlobjectmapper.exceptions.InvalidValueForBoolean;

import java.util.Arrays;
import java.util.List;

public class BooleanConverter implements FieldConverter<Boolean>{
    List<String> possibleTrueValues = Arrays.asList("true", "yes");
    List<String> possibleFalseValues = Arrays.asList("false", "no");

    @Override
    public Boolean convertToValue(String value, String pattern) {
        if(possibleFalseValues.contains(value)) return false;
        else if(possibleTrueValues.contains(value)) return true;
        else throw new InvalidValueForBoolean("value");
    }

    @Override
    public String convertToString(Boolean value, String pattern) {
        if(value) return "true";
        else return "false";
    }
}
