package com.gsdd.keymanager.config;

import com.gsdd.keymanager.interceptor.HeaderInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

  private final HeaderInterceptor headerInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(headerInterceptor);
  }

}
