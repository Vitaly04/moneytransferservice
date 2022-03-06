package ru.netology.moneytransferservice.card;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    private String cardNumber;
    private String cardValidTill;
    private String cardCVV;
    private int money;
}
