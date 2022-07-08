package com.interview.exchangeHub.exceptions;

public class PeselException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Too young to have an account or invalid pesel";
    }
}
