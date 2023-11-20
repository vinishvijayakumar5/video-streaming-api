package com.xyzcorp.api.videostreaming.exception;

import org.springframework.http.HttpStatus;

public class InvalidRequestException extends ApiException {

    public InvalidRequestException(String message, String contract, HttpStatus status) {
        super(message, contract, status);
    }
}
