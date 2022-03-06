package ru.netology.moneytransferservice.exception;

public class ErrorConfirmationException extends RuntimeException{
    private int id;

    public ErrorConfirmationException(String message, int id) {
        super(message);
        this.id = id;
    }
}
