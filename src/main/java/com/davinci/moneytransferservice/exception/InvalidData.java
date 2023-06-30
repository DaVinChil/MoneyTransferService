package com.davinci.moneytransferservice.exception;

public class InvalidData extends RuntimeException{
    private int id;

    public InvalidData(String msg){
        super(msg);
    }

    public void setId(int id){
        this.id = id;
    }
}
