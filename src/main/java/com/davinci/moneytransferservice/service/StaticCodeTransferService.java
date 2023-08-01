package com.davinci.moneytransferservice.service;

import com.davinci.moneytransferservice.exception.InvalidData;
import com.davinci.moneytransferservice.exception.OperationFail;
import com.davinci.moneytransferservice.logger.TransferLogger;
import com.davinci.moneytransferservice.model.Confirmation;
import com.davinci.moneytransferservice.model.Transfer;
import com.davinci.moneytransferservice.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class StaticCodeTransferService implements TransferService{

    private final OperationRepository repository;
    private final TransferLogger logger;

    private final String confirmCode = "0000";

    public String doTransfer(Transfer transfer){
        return repository.saveTransfer(transfer).orElseThrow(() -> new OperationFail("Internal exception"));
    }

    public String confirmOperation(Confirmation conf){
        if(repository.containsTransfer(conf.operationId())) {
            Transfer transfer = repository.findTransfer(conf.operationId());
            if (conf.code().equals(confirmCode)){
                String opId = repository.saveConfirmation(conf).orElseThrow(() -> new OperationFail("Internal exception"));
                logger.logTransferSuccess(opId, transfer);
                return opId;
            } else {
                repository.deleteTransfer(conf.operationId());
                logger.logTransferDenied(conf.operationId(), transfer);
                throw new InvalidData("Invalid data");
            }
        } else {
            throw new InvalidData("Invalid data");
        }
    }
}
