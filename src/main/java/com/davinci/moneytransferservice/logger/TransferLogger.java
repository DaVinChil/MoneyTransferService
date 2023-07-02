package com.davinci.moneytransferservice.logger;

import com.davinci.moneytransferservice.model.Confirmation;
import com.davinci.moneytransferservice.model.Transfer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;

public class TransferLogger {
    private static TransferLogger INSTANCE;
    private static Logger logger;

    public static TransferLogger getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TransferLogger();
        }

        return INSTANCE;
    }

    private TransferLogger() {
        logger = LogManager.getLogger(TransferLogger.class);
    }

    public void logException(Exception e, HttpStatus status, int id) {
        logger.error(String.format("%s %-6s%s",id, status.value(), e.getMessage()));
    }

    public void logTransferSuccess(Transfer transfer) {
        logTransfer(transfer, true);
    }

    public void logTransfer(Transfer transfer, boolean success){
        logger.log(Level.forName("transfer", 1), String.format("%s %s %s %s %s %d %s %s",
                transfer.getOperationId(),
                transfer.getCardFromNumber(),
                transfer.getCardFromValidTill(),
                transfer.getCardFromCVV(),
                transfer.getCardToNumber(),
                transfer.getAmount().getValue(),
                transfer.getAmount().getCurrency(),
                success ? "SUCCESS" : "DENIED"));
    }

    public void logTransferDenied(Transfer transfer){
        logTransfer(transfer, false);
    }
}
