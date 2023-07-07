package com.davinci.moneytransferservice.controller;

import com.davinci.moneytransferservice.model.Confirmation;
import com.davinci.moneytransferservice.model.Transfer;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface TransferController {
    ResponseEntity<String> transferMoney(Transfer transfer);
    ResponseEntity<String> confirmOperation(Confirmation confirmation);
}
