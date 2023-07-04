package com.davinci.moneytransferservice.repository;

import com.davinci.moneytransferservice.exception.InvalidData;
import com.davinci.moneytransferservice.exception.OperationFail;
import com.davinci.moneytransferservice.logger.TransferLogger;
import com.davinci.moneytransferservice.model.Confirmation;
import com.davinci.moneytransferservice.model.Transfer;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class OperationRepository {
    private static final AtomicInteger id = new AtomicInteger(0);
    private static final TransferLogger logger = TransferLogger.getInstance();
    private static final ConcurrentHashMap<String, Transfer> data = new ConcurrentHashMap<>();

    public Optional<String> transferMoney(Transfer transfer){
        transfer.setOperationId(String.valueOf(id.getAndIncrement()));
        data.put(transfer.getOperationId(), transfer);
        return Optional.ofNullable(transfer.getOperationId());
    }

    public Optional<String> confirmOperation(Confirmation confirmation){
        if(data.containsKey(confirmation.getOperationId())) {
            if(!confirmation.getCode().equals("0000")) {
                logger.logTransferDenied(data.get(confirmation.getOperationId()));
                data.remove(confirmation.getOperationId());
                throw new InvalidData("Wrong confirmation code");
            }
        } else {
            throw new InvalidData("No such operation");
        }

        logger.logTransferSuccess(data.get(confirmation.getOperationId()));
        data.remove(confirmation.getOperationId());
        return Optional.of(confirmation.getOperationId());
    }
}
