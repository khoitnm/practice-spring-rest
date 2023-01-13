package org.tnmk.practicejson.pro05globalerrorhandler.common.tracing;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.UUID;

/**
 * This filter is basically just put request information into MDC log so that
 * in all subsequence log messages, we can know which requests triggered that logic.
 * <p/>
 * We want to put the log in the {@link Filter} instead of Interceptor because Filter is running
 * before Interceptor,
 * and we want to trace the requests log no matter what happens with Interceptors or logic after
 * that.
 */
@Slf4j
@Component
public class TracingFilter extends GenericFilterBean {

    public static final String HTTP_HEADER_CORRELATION_ID = "correlationId";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String requestUrl = String.format("(%s %s) %s",
                httpServletRequest.getProtocol(),
                httpServletRequest.getMethod(),
                httpServletRequest.getRequestURL().toString());
            String correlationId = httpServletRequest.getHeader(HTTP_HEADER_CORRELATION_ID);
            if (!StringUtils.hasText(correlationId)) {
                correlationId = UUID.randomUUID().toString();
            }

            MDC.put(TracingConstants.CORRELATION_ID, correlationId);
            MDC.put(TracingConstants.HTTP_REQUEST_URL, requestUrl);
        }
        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove(TracingConstants.CORRELATION_ID);
            MDC.remove(TracingConstants.HTTP_REQUEST_URL);
        }
    }
}
