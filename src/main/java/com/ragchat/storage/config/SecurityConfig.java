package com.ragchat.storage.config;

import com.ragchat.storage.security.ApiKeyFilter;
import com.ragchat.storage.security.RateLimitFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Value("${app.api-key}")
    private String apiKey;

    @Value("${app.rate-limit.capacity:100}")
    private int rateLimitCapacity;

    @Value("${app.rate-limit.refill-tokens:100}")
    private int rateLimitRefillTokens;

    @Value("${app.rate-limit.refill-duration-seconds:60}")
    private long rateLimitRefillDurationSeconds;

    @Bean
    public OncePerRequestFilter apiKeyFilter() {
        return new ApiKeyFilter(apiKey);
    }

    @Bean
    public OncePerRequestFilter rateLimitFilter() {
        return new RateLimitFilter(rateLimitCapacity, rateLimitRefillTokens, rateLimitRefillDurationSeconds);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("*"));
        config.setAllowedMethods(List.of(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PATCH.name(), HttpMethod.DELETE.name()));
        config.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
