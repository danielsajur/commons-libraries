package com.common.log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Logger {

    void save();

    <T> void log(T content);

    <E extends Throwable> void exception(E exception);

    void addRequest(HttpServletRequest request);

    void addResponse(HttpServletResponse response);
}
