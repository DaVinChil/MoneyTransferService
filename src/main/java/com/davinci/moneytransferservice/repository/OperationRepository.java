package com.davinci.moneytransferservice.repository;

import com.davinci.moneytransferservice.exception.OperationFail;
import com.davinci.moneytransferservice.logger.Logger;
import com.davinci.moneytransferservice.model.Amount;
import com.davinci.moneytransferservice.model.Confirmation;
import com.davinci.moneytransferservice.model.Operation;
import com.davinci.moneytransferservice.model.Transfer;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

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

        if(!findOperationAndConfirm(confirmation.getId())) {
            return Optional.empty();
        }

        return Optional.of(confirmation.getId());
    }

    public Operation findOperation(String id){
        try(BufferedReader br = new BufferedReader(new FileReader(logger.getOperationPath()))){
            String strOperation = br.lines().filter(line -> line.contains(id)).findAny().orElse(null);
            if(strOperation == null){
                return null;
            }

            Operation operation = new Operation();

            String[] props = strOperation.split(" ");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[dd-MM-yyyy HH:mm:ss]");
            Transfer transfer = new Transfer();
            operation.setDate(LocalDate.parse(props[0] + " " + props[1], formatter));
            transfer.setOperationId(props[2]);
            transfer.setCardFromNumber(props[3].substring(1, props[3].length()-1));
            transfer.setCardFromValidTill(props[4]);
            transfer.setCardFromCVV(props[5]);
            transfer.setCardToNumber(props[6].substring(1, props[6].length()-1));
            transfer.setAmount(new Amount(Integer.parseInt(props[7]), props[8]));
            operation.setTransfer(transfer);

            return operation;
        } catch (IOException e) {
            throw new OperationFail("Internal exception");
        }
    }

    public boolean findOperationAndConfirm(String id){
        try(RandomAccessFile raf = new RandomAccessFile(logger.getOperationPath(), "rw")){
            String line;
            long bytesPassed = 0;
            while((line = raf.readLine()) != null){
                bytesPassed += line.length() + 1;
                if(line.contains(id)) {
                    raf.seek(bytesPassed-9);
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
