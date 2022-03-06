package ru.netology.moneytransferservice.exception;

public class ErrorTransferException extends RuntimeException {
    private int id;

    public ErrorTransferException(String message, int id) {
        super(message);
        this.id = id;
    }
}
