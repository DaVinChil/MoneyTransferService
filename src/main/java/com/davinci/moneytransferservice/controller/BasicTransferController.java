package com.davinci.moneytransferservice.controller;

import com.davinci.moneytransferservice.model.Confirmation;
import com.davinci.moneytransferservice.model.Transfer;
import com.davinci.moneytransferservice.service.TransferService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin("https://serp-ya.github.io")
@RestController
public class BasicTransferController implements TransferController {
    @NonNull
    private final TransferService service;

    @PostMapping(value = "/transfer")
    public ResponseEntity<String> transferMoney(@RequestBody @Valid Transfer transfer){
        return ResponseEntity.ok("{\"operationId\": " + service.doTransfer(transfer) + "}");
    }

    @PostMapping(value = "/confirmOperation")
    public ResponseEntity<String> confirmOperation(@RequestBody @Valid Confirmation confirmation){
        return ResponseEntity.ok("{\"operationId\": " + service.confirmOperation(confirmation) + "}");
    }
}