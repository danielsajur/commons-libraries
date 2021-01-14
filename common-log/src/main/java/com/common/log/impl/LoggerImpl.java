package com.common.log.impl;

import com.common.log.Logger;
import com.common.log.model.Log;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public final class LoggerImpl implements Logger {

    private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(Logger.class);

    @Autowired
    private ThreadLocal<String> correlationId;

    private final Map<String, Log> logs = new LinkedHashMap<>();

    public final <T> void log(final T content){
        Log log = getBy(correlationId.get());
    }

    private Log getBy(final String correlationId) {
        Log log = logs.get(correlationId);
        if(log == null){
            log = new Log(correlationId);
            logs.put(correlationId, log);
        }
        return log;
    }

    public final void save(){
        Log log = getBy(correlationId.get());
        try {
            String logAsString = new ObjectMapper().writeValueAsString(log);
            LOG.log(log.getLevel(), logAsString);
        } catch (JsonProcessingException e) {
            LOG.error(e);
        }finally {
            logs.remove(correlationId.get());
            correlationId.remove();
        }
    }

    public final <E extends Throwable> void exception(final E exception){
        Log log = getBy(correlationId.get());
        log.getExceptions().add(exception);
    }

    public final void addRequest(HttpServletRequest request) {
        Log log = getBy(correlationId.get());
        log.setRequest(request);
    }

    public final void addResponse(HttpServletResponse response) {
        Log log = getBy(correlationId.get());
        log.setResponse(response);
    }

}
