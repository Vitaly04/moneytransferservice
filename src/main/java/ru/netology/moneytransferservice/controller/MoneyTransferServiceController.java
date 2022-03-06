package ru.netology.moneytransferservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.netology.moneytransferservice.exception.ErrorInputDataException;
import ru.netology.moneytransferservice.exception.ErrorTransferException;
import ru.netology.moneytransferservice.service.MoneyTransferService;
import ru.netology.moneytransferservice.trasferdata.ConfirmData;
import ru.netology.moneytransferservice.trasferdata.TransferData;


@RestController
@AllArgsConstructor
@CrossOrigin(origins="https://serp-ya.github.io")
public class MoneyTransferServiceController {
    private MoneyTransferService service;

    @PostMapping(value = "/transfer", consumes = "application/json",produces="application/json")
    public String getOperationId(@RequestBody TransferData transferData) {
        return service.getTransferOperationId(transferData);
    }

    @PostMapping(value = "/confirmOperation", consumes = "application/json",produces="application/json")
    public String confirmOperation(@RequestBody ConfirmData confirmData) {
        return service.confirmOperation(confirmData);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ErrorInputDataException.class)
    public String errorInputDataExceptionHandle(ErrorInputDataException e) {
        return e.getMessage() + e.getId();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ErrorTransferException.class)
    public String errorTransferExceptionHandle(ErrorInputDataException e) {
        return e.getMessage() + e.getId();
    }
}
