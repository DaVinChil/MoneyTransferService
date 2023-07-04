package com.davinci.moneytransferservice.controller;

import com.davinci.moneytransferservice.model.Confirmation;
import com.davinci.moneytransferservice.model.Transfer;
import com.davinci.moneytransferservice.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@CrossOrigin
@RestController
public class Controller {
    private TransferService service;

    @Autowired
    public Controller(TransferService service){
        this.service = service;
    }

    @PostMapping(value = "/transfer", produces = "application/json")
    @ResponseBody
    public HashMap<String, String> transferMoney(@RequestBody @Valid Transfer transfer){
        return new HashMap<>(){{put("operationId", service.doTransfer(transfer));}};
    }

    @PostMapping(value = "/confirmOperation", produces = "application/json")
    @ResponseBody
    public HashMap<String, String> confirmOperation(@RequestBody @Valid Confirmation confirmation){
        return new HashMap<>(){{put("operationId", service.confirmOperation(confirmation));}};
    }
}
