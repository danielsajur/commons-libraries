package com.common.exception;

import java.text.MessageFormat;

public class RequiredHeaderException extends HeaderException{

    private static final String MESSAGE = "Header {0} is required!";

    public RequiredHeaderException(String code, String message) {
        super(code, message);
    }

    public RequiredHeaderException(String field) {
        this("RH"+field.hashCode(), MessageFormat.format(MESSAGE, field));
    }
}
