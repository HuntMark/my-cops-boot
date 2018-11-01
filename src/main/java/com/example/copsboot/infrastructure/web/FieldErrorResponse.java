package com.example.copsboot.infrastructure.web;

import lombok.Value;

@Value
public class FieldErrorResponse {
    private String fieldName;
    private String errorMessage;
}
