package com.davinci.moneytransferservice.controller;

import com.davinci.moneytransferservice.model.Confirmation;
import com.davinci.moneytransferservice.model.Transfer;
import com.davinci.moneytransferservice.response_model.OperationIdResponse;
import com.davinci.moneytransferservice.service.TransferService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin("{cross.origin.github.io}")
@RestController
public class BasicTransferController implements TransferController {

    private final TransferService service;

    @PostMapping(value = "/transfer")
    public ResponseEntity<OperationIdResponse> transferMoney(@RequestBody @Valid Transfer transfer){
        return ResponseEntity.ok(new OperationIdResponse(service.doTransfer(transfer)));
    }

    @PostMapping(value = "/confirmOperation")
    public ResponseEntity<OperationIdResponse> confirmOperation(@RequestBody @Valid Confirmation confirmation){
        return ResponseEntity.ok(new OperationIdResponse(service.confirmOperation(confirmation)));
    }
}
