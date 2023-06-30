package com.davinci.moneytransferservice.controller;

import com.davinci.moneytransferservice.model.Confirmation;
import com.davinci.moneytransferservice.model.Transfer;
import com.davinci.moneytransferservice.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private TransferService service;

    @Autowired
    public Controller(TransferService service){
        this.service = service;
    }

    @PostMapping("/transfer")
    public String transferMoney(@RequestBody Transfer transfer){
        return service.doTransfer(transfer);
    }

    @PostMapping("/confirmOperation")
    public String confirmOperation(@RequestBody Confirmation confirmation){
        return service.confirmOperation(confirmation);
    }
}
