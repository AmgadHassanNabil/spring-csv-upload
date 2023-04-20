package com.csvupload.csvupload.model;

public class CSVParseException extends RuntimeException {

    public CSVParseException(String message, Throwable e) {
        super(message, e);
    }

}
