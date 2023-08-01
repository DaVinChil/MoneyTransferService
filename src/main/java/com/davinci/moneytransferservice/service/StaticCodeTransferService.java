package com.davinci.moneytransferservice.service;

import com.davinci.moneytransferservice.exception.InvalidData;
import com.davinci.moneytransferservice.exception.OperationFail;
import com.davinci.moneytransferservice.model.Confirmation;
import com.davinci.moneytransferservice.model.Transfer;
import com.davinci.moneytransferservice.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class StaticCodeTransferService implements TransferService{
    private final double COMMISSION = 10000;
    private final double RUB_RATIO = 100;
    private final String SUCCESS = "SUCCESS";
    private final String DENIED = "DENIED";
    private final Level TRANSFER_LEVEL = Level.forName("transfer", 1);

    private final OperationRepository repository;
    private final Logger logger = LogManager.getLogger(StaticCodeTransferService.class);

    private final String confirmCode = "0000";

    public String doTransfer(Transfer transfer){
        return repository.saveTransfer(transfer).orElseThrow(() -> new OperationFail("Internal exception"));
    }

    public String confirmOperation(Confirmation conf){
        if(repository.containsTransfer(conf.operationId())) {
            Transfer transfer = repository.findTransfer(conf.operationId());
            if (conf.code().equals(confirmCode)){
                String opId = repository.saveConfirmation(conf).orElseThrow(() -> new OperationFail("Internal exception"));
                logTransferSuccess(opId, transfer);
                return opId;
            } else {
                repository.deleteTransfer(conf.operationId());
                logTransferDenied(conf.operationId(), transfer);
                throw new InvalidData("Invalid data");
            }
        } else {
            throw new InvalidData("Invalid data");
        }
    }

    public void logTransferSuccess(String id, Transfer transfer) {
        logTransfer(id, transfer, true);
    }

    public void logTransfer(String id, Transfer transfer, boolean success){
        logger.log(TRANSFER_LEVEL, String.format("%5s %s %s %13.2f %13.2f %-5s %s",
                id,
                transfer.cardFromNumber(),
                transfer.cardToNumber(),
                transfer.amount().value() / RUB_RATIO,
                transfer.amount().value() / COMMISSION,
                transfer.amount().currency(),
                success ? SUCCESS : DENIED));
    }

    public void logTransferDenied(String id, Transfer transfer) {
        logTransfer(id, transfer, false);
    }
}
