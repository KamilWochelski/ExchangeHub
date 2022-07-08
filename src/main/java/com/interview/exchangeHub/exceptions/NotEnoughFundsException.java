package com.interview.exchangeHub.exceptions;

public class NotEnoughFundsException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Not enough funds";
    }
}
