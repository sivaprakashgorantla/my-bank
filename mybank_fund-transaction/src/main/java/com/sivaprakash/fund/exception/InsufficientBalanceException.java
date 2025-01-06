package com.sivaprakash.fund.exception;

/**
 * Custom exception for handling insufficient balance scenarios.
 */
@SuppressWarnings("serial")
public class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException(String message) {
        super(message);
    }

    public InsufficientBalanceException(String message, Throwable cause) {
        super(message, cause);
    }
}
