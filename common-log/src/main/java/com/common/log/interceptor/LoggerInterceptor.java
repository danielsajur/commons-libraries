package com.common.log.interceptor;

import com.common.log.Logger;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LoggerInterceptor extends HandlerInterceptorAdapter {

    private static final String X_CORRELATION_ID = "X-CorrelationId";

    @Autowired
    private Logger LOGGER;

    @Autowired
    private ThreadLocal<String> correlationId;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOGGER.addRequest(request);
        String header = request.getHeader(X_CORRELATION_ID);
        if(StringUtils.isBlank(header)){
            header = UUID.randomUUID().toString();
            request.setAttribute(X_CORRELATION_ID, header);
        }
        correlationId.set(header);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LOGGER.addResponse(response);
        LOGGER.save();
    }
}
