package com.xyzcorp.api.videostreaming.dto;

public record GenericResponseDto(
        boolean success,
        String message,
        String code
) { }
