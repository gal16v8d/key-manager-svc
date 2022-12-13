package com.gsdd.keymanager.interceptor;

import com.gsdd.keymanager.exceptions.MissingHeaderException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class HeaderInterceptor implements HandlerInterceptor {

  private static final List<String> SAFE_LIST = List.of(
      "/actuator",
      "/health",
      "/auth",
      "/error",
      "/favicon.ico",
      "/swagger-ui/",
      "/swagger-resources/",
      "/v3/api-docs",
      "/v3/api-docs/",
      "/webjars/");
  @Value("${kmgr.http.apiKey}")
  private String apiKey;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    boolean containsSafeUri = SAFE_LIST.stream().anyMatch(request.getRequestURI()::contains);
    if (!containsSafeUri) {
      checkSecurityHeader(request);
    }
    return true;
  }

  void checkSecurityHeader(HttpServletRequest request) {
    if (StringUtils.isBlank(request.getHeader(HttpHeaders.AUTHORIZATION))) {
      throw new MissingHeaderException("Missing header: %s".formatted(HttpHeaders.AUTHORIZATION));
    }
  }

}
