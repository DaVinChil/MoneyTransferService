package com.davinci.moneytransferservice.exception_handler;

import com.davinci.moneytransferservice.exception.InvalidData;
import com.davinci.moneytransferservice.exception.OperationFail;
import com.davinci.moneytransferservice.logger.TransferLogger;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

@RestControllerAdvice
public class TransferExceptionHandler {
    private static final AtomicInteger exceptionId = new AtomicInteger(0);
    private static final TransferLogger logger = TransferLogger.getInstance();

    @ExceptionHandler(InvalidData.class)
    public ResponseEntity<HashMap<String, Object>> invalidDataHandler(InvalidData ide){
        ide.setId(exceptionId.getAndIncrement());
        logger.logException(ide, HttpStatus.BAD_REQUEST, ide.getId());
        return new ResponseEntity<>(
                new HashMap<>(){{
                    put("message", ide.getMessage());
                    put("id", ide.getId());
                }},
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OperationFail.class)
    public ResponseEntity<HashMap<String, Object>> operationFailHandler(OperationFail ope){
        ope.setId(exceptionId.getAndIncrement());
        logger.logException(ope, HttpStatus.INTERNAL_SERVER_ERROR, ope.getId());
        return new ResponseEntity<>(
                new HashMap<>(){{
                    put("message", ope.getMessage());
                    put("id", ope.getId());
                }},
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
