package ru.netology.moneytransferservice.exception;

import lombok.Data;

@Data
public class ErrorInputDataException extends RuntimeException {
    private int id;

    public ErrorInputDataException(String message, int id) {
        super(message);
        this.id = id;
    }
}
