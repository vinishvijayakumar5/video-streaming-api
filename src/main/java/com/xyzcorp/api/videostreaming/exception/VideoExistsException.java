package com.xyzcorp.api.videostreaming.exception;

import org.springframework.http.HttpStatus;

public class VideoExistsException extends ApiException {

    public VideoExistsException(String message, String contract, HttpStatus status) {
        super(message, contract, status);
    }
}
