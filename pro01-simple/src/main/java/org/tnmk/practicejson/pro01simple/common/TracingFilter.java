package org.tnmk.practicejson.pro01simple.common;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

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

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    if (request instanceof HttpServletRequest) {
      HttpServletRequest httpServletRequest = (HttpServletRequest) request;
      String requestUrl = String.format("(%s %s) %s",
          httpServletRequest.getProtocol(),
          httpServletRequest.getMethod(),
          httpServletRequest.getRequestURL().toString());
      MDC.put(TracingConstants.HTTP_REQUEST_URL, requestUrl);
    }
    try {
      chain.doFilter(request, response);
    } finally {
      MDC.remove(TracingConstants.HTTP_REQUEST_URL);
    }
  }
}
