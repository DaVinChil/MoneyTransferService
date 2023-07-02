package com.davinci.moneytransferservice.exception_handler;

import com.davinci.moneytransferservice.exception.InvalidData;
import com.davinci.moneytransferservice.exception.OperationFail;
import com.davinci.moneytransferservice.logger.TransferLogger;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

@RestControllerAdvice
public class TransferExceptionHandler {
    private static final AtomicInteger exceptionId = new AtomicInteger(0);
    private static final TransferLogger logger = TransferLogger.getInstance();

    @ExceptionHandler(InvalidData.class)
    public ResponseEntity<ResponseExceptionContainer> invalidDataHandler(InvalidData ide){
        int id = exceptionId.getAndIncrement();
        var exCont = new ResponseExceptionContainer(ide.getMessage(), id);
        logger.logException(ide, HttpStatus.BAD_REQUEST, id);
        return new ResponseEntity<>(
                exCont,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OperationFail.class)
    public ResponseEntity<ResponseExceptionContainer> operationFailHandler(OperationFail ope){
        int id = exceptionId.getAndIncrement();
        var exCont = new ResponseExceptionContainer(ope.getMessage(), id);
        logger.logException(ope, HttpStatus.INTERNAL_SERVER_ERROR, id);
        return new ResponseEntity<>(
                exCont,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ConstraintViolationException.class, BindException.class})
    public ResponseEntity<ResponseExceptionContainer> constraintViolationException(Exception cve){
        int id = exceptionId.getAndIncrement();
        var exCont = new ResponseExceptionContainer(cve.getMessage(), id);
        logger.logException(cve, HttpStatus.BAD_REQUEST, id);
        return new ResponseEntity<>(
                exCont,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Getter
    @AllArgsConstructor
    private static class ResponseExceptionContainer {
        private String message;
        private int id;
    }
}
