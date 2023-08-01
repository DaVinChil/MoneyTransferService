package com.davinci.moneytransferservice.logger;

import com.davinci.moneytransferservice.model.Transfer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class TransferLogger {
    private final Logger logger = LogManager.getLogger(TransferLogger.class);
    private final double COMMISSION = 10000;
    private final double RUB_RATIO = 100;
    private final String SUCCESS = "SUCCESS";
    private final String DENIED = "DENIED";
    private final Level TRANSFER_LEVEL = Level.forName("transfer", 1);


    private static class TransferLoggerInstanceWrapper {
        private static final TransferLogger INSTANCE = new TransferLogger();
    }

    public static TransferLogger getInstance() {
        return TransferLoggerInstanceWrapper.INSTANCE;
    }

    private TransferLogger() {
    }

    public void logException(Exception e, HttpStatus status, long id) {
        logger.error(String.format("%d %-6s%s", id, status.value(), e.getMessage()));
    }

    public void logTransferSuccess(String id, Transfer transfer) {
        logTransfer(id, transfer, true);
    }

    public void logTransfer(String id, Transfer transfer, boolean success) {
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
