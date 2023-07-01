package com.davinci.moneytransferservice.service;

import com.davinci.moneytransferservice.exception.InvalidData;
import com.davinci.moneytransferservice.exception.OperationFail;
import com.davinci.moneytransferservice.logger.Logger;
import com.davinci.moneytransferservice.model.Confirmation;
import com.davinci.moneytransferservice.model.Transfer;
import com.davinci.moneytransferservice.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferService {

    private OperationRepository repository;

    @Autowired
    public TransferService(OperationRepository repo){
        this.repository = repo;
    }

    public String doTransfer(Transfer transfer){
        return repository.transferMoney(transfer).orElseThrow(() -> new OperationFail("Internal exception"));
    }

    public String confirmOperation(Confirmation conf){
        return repository.confirmOperation(conf).orElseThrow(() -> new InvalidData("No such operation"));
    }
}
