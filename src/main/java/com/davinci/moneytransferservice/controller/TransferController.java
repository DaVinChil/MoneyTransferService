package com.davinci.moneytransferservice.controller;

import com.davinci.moneytransferservice.model.Confirmation;
import com.davinci.moneytransferservice.model.Transfer;
import com.davinci.moneytransferservice.response_model.OperationIdResponse;
import org.springframework.http.ResponseEntity;

public interface TransferController {
    ResponseEntity<OperationIdResponse> transferMoney(Transfer transfer);
    ResponseEntity<OperationIdResponse> confirmOperation(Confirmation confirmation);
}
