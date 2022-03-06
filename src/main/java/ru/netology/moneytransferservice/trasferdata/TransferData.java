package ru.netology.moneytransferservice.trasferdata;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferData {
    private Amount amount;
    private String cardFromCVV;
    private String cardFromNumber;
    private String cardFromValidTill;
    private String cardToNumber;
}
