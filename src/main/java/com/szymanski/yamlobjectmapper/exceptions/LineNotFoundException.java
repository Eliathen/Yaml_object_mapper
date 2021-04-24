package com.szymanski.yamlobjectmapper.exceptions;

public class LineNotFoundException extends RuntimeException {

    public LineNotFoundException() {
        super("Line not found exception");
    }
}
