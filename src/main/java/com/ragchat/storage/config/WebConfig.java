package com.ragchat.storage.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<OncePerRequestFilter> requestIdFilterRegistration() {
        FilterRegistrationBean<OncePerRequestFilter> reg = new FilterRegistrationBean<>(new RequestIdFilter());
        reg.addUrlPatterns("/*");
        reg.setOrder(Ordered.HIGHEST_PRECEDENCE - 1);
        return reg;
    }

    @Bean
    public FilterRegistrationBean<OncePerRequestFilter> apiKeyFilterRegistration(OncePerRequestFilter apiKeyFilter) {
        FilterRegistrationBean<OncePerRequestFilter> reg = new FilterRegistrationBean<>(apiKeyFilter);
        reg.addUrlPatterns("/api/*");
        reg.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return reg;
    }

    @Bean
    public FilterRegistrationBean<OncePerRequestFilter> rateLimitFilterRegistration(OncePerRequestFilter rateLimitFilter) {
        FilterRegistrationBean<OncePerRequestFilter> reg = new FilterRegistrationBean<>(rateLimitFilter);
        reg.addUrlPatterns("/api/*");
        reg.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        return reg;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistration(CorsConfigurationSource corsConfigurationSource) {
        FilterRegistrationBean<CorsFilter> reg = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource));
        reg.addUrlPatterns("/*");
        reg.setOrder(Ordered.HIGHEST_PRECEDENCE + 2);
        return reg;
    }
}
