package com.davinci.moneytransferservice.model;

import jakarta.validation.constraints.NotBlank;

public record Confirmation(@NotBlank String operationId, @NotBlank String code){}
