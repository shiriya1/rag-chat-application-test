package com.ragchat.storage.security;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

public class RateLimitFilter extends OncePerRequestFilter {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();
    private final int capacity;
    private final int refillTokens;
    private final long refillDurationSeconds;

    public RateLimitFilter(int capacity, int refillTokens, long refillDurationSeconds) {
        this.capacity = capacity;
        this.refillTokens = refillTokens;
        this.refillDurationSeconds = refillDurationSeconds;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        String key = request.getHeader("X-API-Key");
        if (key == null || key.isBlank()) {
            key = request.getRemoteAddr();
        }
        Bucket bucket = buckets.computeIfAbsent(key, k -> createBucket());
        if (!bucket.tryConsume(1)) {
            response.setStatus(429);
            response.setHeader("Retry-After", String.valueOf(refillDurationSeconds));
            response.getWriter().write("Rate limit exceeded");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private Bucket createBucket() {
        Bandwidth limit = Bandwidth.classic(
                capacity,
                Refill.greedy(refillTokens, Duration.ofSeconds(refillDurationSeconds)));
        return Bucket.builder().addLimit(limit).build();
    }
}
