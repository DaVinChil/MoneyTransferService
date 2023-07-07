package com.davinci.moneytransferservice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


public record Transfer(
        @NotBlank
        @Pattern(regexp = "\\d{16}")
        String cardFromNumber,
        @NotBlank
        @Pattern(regexp = "^(0[7-9]|1[0-2])/(2[3-9]|[3-9][0-9])$")
        String cardFromValidTill,
        @NotBlank
        @Pattern(regexp = "\\d{3}")
        String cardFromCVV,
        @NotBlank
        @Pattern(regexp = "\\d{16}")
        String cardToNumber,
        @NotNull
        Amount amount) {}