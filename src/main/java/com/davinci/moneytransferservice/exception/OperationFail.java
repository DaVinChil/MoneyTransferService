package com.davinci.moneytransferservice.exception;

public class OperationFail extends RuntimeException{
    private int id;
    public OperationFail(String msg){
        super(msg);
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }
}
