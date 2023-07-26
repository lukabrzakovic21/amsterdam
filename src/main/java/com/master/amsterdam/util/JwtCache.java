package com.master.amsterdam.util;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Objects;

@Component
public class JwtCache {

    private static final int CACHE_MAXIMUM_SIZE = 1000;

    private final LoadingCache<String, Boolean> cache;
    private final RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(JwtCache.class);

    public JwtCache(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        cache = Caffeine.newBuilder()
                .maximumSize(CACHE_MAXIMUM_SIZE)
                .expireAfterWrite(Duration.ofMinutes(20))
                .build(this::checkForJwt);
    }

    private Boolean checkForJwt(String jwt) {

        try {
        System.out.println("Ovo treba da se desava jednom u 20 sekundi");
            var headers = new HttpHeaders();
            headers.setBearerAuth(jwt);
            HttpEntity<String> httpEntity = new HttpEntity<>("some body", headers);
            var exchange = restTemplate.exchange("http://alexandria:8080/jwt/valid", HttpMethod.GET, httpEntity, Boolean.class);
            return exchange.getBody();
        } catch (Exception exception) {
            logger.warn("Downstream service is down");
            throw new ResourceAccessException("Downstream service is down");
        }
    }

    public boolean jwtValid(String jwt) {

        if(Objects.isNull(jwt)) {
            return false;
        }

        try {
            return cache.get(jwt);
        } catch (Exception ex) {
            logger.warn("Downstream service is down");
            throw new ResourceAccessException("Downstream service is down");
        }
    }

    public void invalidateEntry(String jwt) {
        cache.invalidate(jwt);
    }



}
