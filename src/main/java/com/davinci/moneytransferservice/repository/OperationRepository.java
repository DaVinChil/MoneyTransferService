package com.davinci.moneytransferservice.repository;

import com.davinci.moneytransferservice.exception.OperationFail;
import com.davinci.moneytransferservice.logger.Logger;
import com.davinci.moneytransferservice.model.Confirmation;
import com.davinci.moneytransferservice.model.Transfer;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class OperationRepository {
    private static final AtomicInteger id = new AtomicInteger(0);
    private static final Logger logger = Logger.getInstance();

    public Optional<String> transferMoney(Transfer transfer){
        transfer.setOperationId(String.valueOf(id.getAndIncrement()));
        logger.logTransfer(transfer);
        return Optional.ofNullable(transfer.getOperationId());
    }

    public Optional<String> confirmOperation(Confirmation confirmation){
        if(!findOperationAndConfirm(confirmation.getOperationId())) {
            return Optional.empty();
        }
        return Optional.of(confirmation.getOperationId());
    }

    private boolean findOperationAndConfirm(String id){
        try(RandomAccessFile raf = new RandomAccessFile(logger.getOperationPath(), "rw")){
            String line;
            long bytesPassed = 0;
            while((line = raf.readLine()) != null){
                bytesPassed += line.length() + 1;
                String opId = line.split(" ")[2];
                if(opId.equals(id)) {
                    raf.seek(bytesPassed-10);
                    raf.writeBytes("CONFIRMED");
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            throw new OperationFail("Internal exception");
        }
    }
}
