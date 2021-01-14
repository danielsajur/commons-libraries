package com.common.exception.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
@RequiredArgsConstructor
public class ErrorResponse {

    private LocalDateTime timestamp;

    private final String code;

    private final String description;
}
