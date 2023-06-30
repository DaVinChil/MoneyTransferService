package com.davinci.moneytransferservice.exception_handler;

import com.davinci.moneytransferservice.exception.InvalidData;
import com.davinci.moneytransferservice.exception.OperationFail;
import com.davinci.moneytransferservice.logger.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.concurrent.atomic.AtomicInteger;

@RestControllerAdvice
public class TransferExceptionHandler {
    private static final AtomicInteger exceptionId = new AtomicInteger(0);
    private final Logger logger = Logger.getInstance();

    @ExceptionHandler(InvalidData.class)
    public ResponseEntity<InvalidData> invalidDataHandler(InvalidData ide){
        ide.setId(exceptionId.getAndIncrement());
        logger.logException(ide, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(ide, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OperationFail.class)
    public ResponseEntity<OperationFail> operationFailHandler(OperationFail ope){
        ope.setId(exceptionId.getAndIncrement());
        return new ResponseEntity<>(ope, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
