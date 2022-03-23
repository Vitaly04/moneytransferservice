package ru.netology.moneytransferservice.operation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.netology.moneytransferservice.trasferdata.TransferData;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Operation {
    private Integer cardToTransferIndex;
    private Integer cardFromTransferIndex;
    private boolean transferIsAllowed;
    private int amountTransfer;
    private TransferData transferData;
}