package com.davinci.moneytransferservice.service;

import com.davinci.moneytransferservice.model.Confirmation;
import com.davinci.moneytransferservice.model.Transfer;

public interface TransferService {
    String doTransfer(Transfer t);
    String confirmOperation(Confirmation c);
}
