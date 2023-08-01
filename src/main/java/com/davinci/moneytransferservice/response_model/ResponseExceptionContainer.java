package com.davinci.moneytransferservice.response_model;


import lombok.AllArgsConstructor;
import lombok.Getter;


public record ResponseExceptionContainer(String message, long id) {}