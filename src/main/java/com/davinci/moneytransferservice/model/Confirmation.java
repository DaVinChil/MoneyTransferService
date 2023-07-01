package com.davinci.moneytransferservice.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Confirmation {
    @NotBlank
    private String operationId;
    @NotBlank
    private String code;
}
