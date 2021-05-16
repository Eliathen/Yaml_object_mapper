package com.szymanski.yamlobjectmapper.exceptions;

public class NotFoundConverterException extends RuntimeException {
    public NotFoundConverterException(String s) {
        super("Not found converter for '" + s + "' type");
    }
}
