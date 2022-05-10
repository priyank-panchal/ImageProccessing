package com.image.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrorMessage {
    private String error;
    private HttpStatus httpStatus;

}
