package ru.netology.moneytransferservice.repository;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import ru.netology.moneytransferservice.card.Card;
import ru.netology.moneytransferservice.exception.ErrorConfirmationException;
import ru.netology.moneytransferservice.exception.ErrorInputDataException;
import ru.netology.moneytransferservice.exception.ErrorTransferException;
import ru.netology.moneytransferservice.operation.Operation;
import ru.netology.moneytransferservice.trasferdata.ConfirmData;
import ru.netology.moneytransferservice.trasferdata.TransferData;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@Slf4j
@Repository
public class CardsRepository {
    private final List<Card> cardsList = new CopyOnWriteArrayList<>(List.of(new Card("1111111111111111", "11/22", "123", 2000),
            new Card("2222222222222222", "12/22", "456", 2000)));
    private Map<Integer, Operation> operationMap = new ConcurrentHashMap<>();
    private AtomicInteger operationId = new AtomicInteger();
    private final String verificationCode = "0000";
    private Operation operation;

    public String getTransferOperationId(@RequestBody TransferData transferData) {
        if (isEmpty(transferData.getCardFromNumber()) || isEmpty(transferData.getCardFromCVV()) || isEmpty(transferData.getCardFromValidTill())) {
            throw new ErrorInputDataException("Error input data", operationId.incrementAndGet());
        }
        operation = new Operation();
        cardsList.forEach((card) -> { if (card.getCardNumber().equals(transferData.getCardToNumber()))
            operation.setCardToTransferIndex(cardsList.indexOf(card));});
        cardsList.forEach((card) -> { if (card.getCardNumber().equals(transferData.getCardFromNumber())
                && card.getCardCVV().equals(transferData.getCardFromCVV()) && card.getCardValidTill().equals(transferData.getCardFromValidTill()))
            operation.setCardFromTransferIndex(cardsList.indexOf(card));});

       if (operation.getCardFromTransferIndex() != null && operation.getCardToTransferIndex() != null) {
           operation.setAmountTransfer(transferData.getAmount().getValue()/100 + transferData.getAmount().getValue()/100/100);
           if (cardsList.get(operation.getCardFromTransferIndex()).getMoney() - operation.getAmountTransfer() < 0)  {
               throw new ErrorTransferException("Error transfer", operationId.incrementAndGet());
           }
           operation.setTransferData(transferData);
           operation.setTransferIsAllowed(true);
           operationMap.put(operationId.incrementAndGet(), operation);
       } else throw new ErrorTransferException("Error transfer", operationId.incrementAndGet());
       return String.valueOf(operationId.get());
    }

    public String confirmOperation(@RequestBody ConfirmData confirmData) {
        if (isEmpty(confirmData.getCode())) throw new ErrorInputDataException("Error input data", operationId.incrementAndGet());
        if (verificationCode.equals(confirmData.getCode()) && operation.isTransferIsAllowed()) {
            cardsList.get(operation.getCardFromTransferIndex()).setMoney(cardsList.get(operation.getCardFromTransferIndex()).getMoney() - operation.getAmountTransfer());
            cardsList.get(operation.getCardToTransferIndex()).setMoney(cardsList.get(operation.getCardToTransferIndex()).getMoney() +
                    operationMap.get(operationId.get()).getTransferData().getAmount().getValue()/100);

            log.info("The transfer was completed successfully, debit card- "  + cardsList.get(operation.getCardFromTransferIndex()).getCardNumber() +
                    " , card for crediting- " + cardsList.get(operation.getCardToTransferIndex()).getCardNumber() +
                    " , amount- " + operationMap.get(operationId.get()).getTransferData().getAmount().getValue()/100 +
                    " , commission- " + operationMap.get(operationId.get()).getTransferData().getAmount().getValue()/100/100);
            return String.valueOf(operationId.get());
        } else throw new ErrorConfirmationException("Error confirmation", operationId.incrementAndGet());
    }

    private boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
