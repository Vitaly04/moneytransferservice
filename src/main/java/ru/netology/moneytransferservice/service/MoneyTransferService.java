package ru.netology.moneytransferservice.service;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import ru.netology.moneytransferservice.repository.CardsRepository;
import ru.netology.moneytransferservice.trasferdata.ConfirmData;
import ru.netology.moneytransferservice.trasferdata.TransferData;

@AllArgsConstructor
public class MoneyTransferService {
    private CardsRepository cardsRepository;

    public String getTransferOperationId(@RequestBody TransferData transferData) {
        return cardsRepository.getTransferOperationId(transferData);
    }

    public String confirmOperation(@RequestBody ConfirmData confirmData) {
        return cardsRepository.confirmOperation(confirmData);
    }
}
