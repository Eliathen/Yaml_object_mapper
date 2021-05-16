package com.szymanski.yamlobjectmapper.exceptions;

public class InvalidValueForBoolean extends RuntimeException {

    public InvalidValueForBoolean() {
    }

    public InvalidValueForBoolean(String message) {
        super("Invalid value '" + message + "' for Boolean type");

    }
}
