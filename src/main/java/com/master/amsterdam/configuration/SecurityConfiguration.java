package com.master.amsterdam.configuration;

import com.master.amsterdam.util.JwtCache;
import com.master.amsterdam.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfiguration {

    private final JwtUtil jwtUtil;
    private final JwtCache jwtCache;

    public SecurityConfiguration(JwtUtil jwtUtil, JwtCache jwtCache) {
        this.jwtUtil = jwtUtil;
        this.jwtCache = jwtCache;
    }

    @Bean
    public AuthenticationFilter getAuthenticationFilter() {
        return new AuthenticationFilter(jwtUtil, jwtCache);
    }

}
