package ru.netology.moneytransferservice.repository;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import ru.netology.moneytransferservice.card.Card;
import ru.netology.moneytransferservice.exception.ErrorConfirmationException;
import ru.netology.moneytransferservice.exception.ErrorInputDataException;
import ru.netology.moneytransferservice.exception.ErrorTransferException;
import ru.netology.moneytransferservice.trasferdata.ConfirmData;
import ru.netology.moneytransferservice.trasferdata.TransferData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@Slf4j
@Repository
public class CardsRepository {
    private final List<Card> cardsList = new ArrayList<>(List.of(new Card("1111111111111111", "11/22", "123", 2000),
            new Card("2222222222222222", "12/22", "456", 2000)));
    private AtomicInteger operationId = new AtomicInteger();
    private final String verificationCode = "0000";
    private Integer cardToTransferIndex;
    private Integer cardFromTransferIndex;
    private TransferData transferDataAllowed;
    private int amountTransfer;

    public String getTransferOperationId(@RequestBody TransferData transferData) {
        if (isEmpty(transferData.getCardFromNumber()) || isEmpty(transferData.getCardFromCVV()) || isEmpty(transferData.getCardFromValidTill())) {
            throw new ErrorInputDataException("Error input data", operationId.incrementAndGet());
        }
        transferDataAllowed = null;
        cardToTransferIndex = null;
        cardFromTransferIndex = null;
        amountTransfer = 0;
        cardsList.forEach((card) -> { if (card.getCardNumber().equals(transferData.getCardToNumber()))
            cardToTransferIndex = cardsList.indexOf(card);});
        cardsList.forEach((card) -> { if (card.getCardNumber().equals(transferData.getCardFromNumber())
                && card.getCardCVV().equals(transferData.getCardFromCVV()) && card.getCardValidTill().equals(transferData.getCardFromValidTill()))
            cardFromTransferIndex = cardsList.indexOf(card);});

       if (cardFromTransferIndex != null && cardToTransferIndex != null) {
           amountTransfer = transferData.getAmount().getValue()/100 + transferData.getAmount().getValue()/100/100;
           if (cardsList.get(cardFromTransferIndex).getMoney() - amountTransfer < 0)  {
               throw new ErrorTransferException("Error transfer", operationId.incrementAndGet());
           }
           transferDataAllowed = transferData;
       } else throw new ErrorTransferException("Error transfer", operationId.incrementAndGet());
       return String.valueOf(operationId.incrementAndGet());
    }

    public String confirmOperation(@RequestBody ConfirmData confirmData) {
        if (isEmpty(confirmData.getCode())) throw new ErrorInputDataException("Error input data", operationId.incrementAndGet());
        if (verificationCode.equals(confirmData.getCode()) && transferDataAllowed != null) {
            cardsList.get(cardFromTransferIndex).setMoney(cardsList.get(cardFromTransferIndex).getMoney() - amountTransfer);
            cardsList.get(cardToTransferIndex).setMoney(cardsList.get(cardToTransferIndex).getMoney() + transferDataAllowed.getAmount().getValue()/100);

            log.info("The transfer was completed successfully, debit card- "  + cardsList.get(cardFromTransferIndex).getCardNumber() +
                    " , card for crediting- " + cardsList.get(cardToTransferIndex).getCardNumber() +
                    " , amount- " + transferDataAllowed.getAmount().getValue()/100 + " , commission- " + transferDataAllowed.getAmount().getValue()/100/100);

            return String.valueOf(operationId.get());
        } else throw new ErrorConfirmationException("Error confirmation", operationId.incrementAndGet());
    }

    private boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
