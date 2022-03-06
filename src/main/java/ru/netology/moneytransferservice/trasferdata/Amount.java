package ru.netology.moneytransferservice.trasferdata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Amount {
    private String currency;
    private int value;
}
