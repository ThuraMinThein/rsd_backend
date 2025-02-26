package com.rsd.yaycha.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GlobalResponseError {

    private int statusCode;
    private String message;
    private String error;
    private LocalDateTime timestamp;

}
