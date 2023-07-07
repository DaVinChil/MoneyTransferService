package com.davinci.moneytransferservice.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record Amount(@Min(0) Integer value, @NotBlank String currency){}