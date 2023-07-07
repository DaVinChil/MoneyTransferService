package com.davinci.moneytransferservice.exception;

public class OperationFail extends RuntimeException{
    public OperationFail(String msg){
        super(msg);
    }
}
