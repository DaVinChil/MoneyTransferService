package com.davinci.moneytransferservice.repository;

import com.davinci.moneytransferservice.logger.TransferLogger;
import com.davinci.moneytransferservice.model.Confirmation;
import com.davinci.moneytransferservice.model.Transfer;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class BasicOperationRepository implements OperationRepository {
    private final AtomicInteger id = new AtomicInteger(0);
    private final TransferLogger logger = TransferLogger.getInstance();
    private final ConcurrentHashMap<String, Transfer> data = new ConcurrentHashMap<>();

    public Optional<String> transferMoney(Transfer transfer){
        String transferId = String.valueOf(id.getAndIncrement());
        data.put(transferId, transfer);
        return Optional.ofNullable(transferId);
    }

    public Optional<String> confirmOperation(Confirmation confirmation){
        data.remove(confirmation.operationId());
        return Optional.of(confirmation.operationId());
    }

    public boolean containsTransfer(String id){
        return data.containsKey(id);
    }

    public Transfer getTransfer(String id){
        return data.get(id);
    }

    public Transfer removeTransfer(String id){
        return data.remove(id);
    }
}
