package com.davinci.moneytransferservice.handler;

import com.davinci.moneytransferservice.exception.InvalidData;
import com.davinci.moneytransferservice.exception.OperationFail;
import com.davinci.moneytransferservice.response_model.ResponseExceptionContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.concurrent.atomic.AtomicLong;

@RestControllerAdvice
public class TransferExceptionHandler {
    private static final AtomicLong exceptionId = new AtomicLong(0);
    private final Logger logger = LogManager.getLogger(TransferExceptionHandler.class);

    @ExceptionHandler(InvalidData.class)
    public ResponseEntity<ResponseExceptionContainer> invalidDataHandler(InvalidData ide){
        long id = exceptionId.getAndIncrement();
        var exCont = new ResponseExceptionContainer(ide.getMessage(), id);
        logException(ide, HttpStatus.BAD_REQUEST, id);
        return new ResponseEntity<>(
                exCont,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OperationFail.class)
    public ResponseEntity<ResponseExceptionContainer> operationFailHandler(OperationFail ope){
        long id = exceptionId.getAndIncrement();
        var exCont = new ResponseExceptionContainer(ope.getMessage(), id);
        logException(ope, HttpStatus.INTERNAL_SERVER_ERROR, id);
        return new ResponseEntity<>(
                exCont,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public void logException(Exception e, HttpStatus status, long id) {
        logger.error(String.format("%d %-6s%s", id, status.value(), e.getMessage()));
    }
}
