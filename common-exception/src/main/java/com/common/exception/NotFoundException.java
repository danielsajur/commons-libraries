package com.common.exception;

public class NotFoundException extends BusinessException{

    private static final String MESSAGE = "Register not founded!";

    public NotFoundException() {
        super("" + MESSAGE.hashCode(), MESSAGE);
    }

    public NotFoundException(String code, String message) {
        super(code, message);
    }
}
