package com.davinci.moneytransferservice.exception;

public class InvalidData extends RuntimeException{
    public InvalidData(String msg){
        super(msg);
    }
}