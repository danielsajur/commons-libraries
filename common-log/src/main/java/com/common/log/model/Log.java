package com.common.log.model;

import lombok.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

@Data
@EqualsAndHashCode
@RequiredArgsConstructor
public class Log {

    private final String correlationId;

    private Level level = Level.INFO;

    private HttpServletRequest request;

    private HttpServletResponse response;

    private LocalDateTime start;

    private LocalDateTime end;

    private final List<Throwable> exceptions = new ArrayList<>();

}
