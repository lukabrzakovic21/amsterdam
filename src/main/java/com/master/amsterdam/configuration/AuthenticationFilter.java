package com.master.amsterdam.configuration;

import com.google.common.base.Strings;
import com.master.amsterdam.exception.UnauthorizedException;
import com.master.amsterdam.model.OpenEndpoints;
import com.master.amsterdam.model.PathPattern;
import com.master.amsterdam.util.JwtCache;
import com.master.amsterdam.util.JwtUtil;
import com.master.amsterdam.util.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

@Component
public class AuthenticationFilter implements GatewayFilter {

    private final JwtUtil jwtUtil;
    private final JwtCache jwtCache;
    private final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    public AuthenticationFilter(JwtUtil jwtUtil, JwtCache jwtCache) {
        this.jwtUtil = jwtUtil;
        this.jwtCache = jwtCache;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        var request = exchange.getRequest();
        var method = request.getPath();

        if(OpenEndpoints.LOGIN.equalsIgnoreCase(method.value())) {
            logger.info("Entering into open endpoint");
            return chain.filter(exchange);
        }

        var authorizationHeaderList  = request.getHeaders().get(HttpHeaders.AUTHORIZATION);
        if(authorizationHeaderList==null ||
           authorizationHeaderList.isEmpty() ||
           Strings.isNullOrEmpty(authorizationHeaderList.get(0)) ||
           !authorizationHeaderList.get(0).startsWith("Bearer ")
        ) {
            logger.error("Niste poslali token");
            throw new UnauthorizedException("User is not authorized");
        }
        var authorizationHeader = authorizationHeaderList.get(0);
        String token = authorizationHeader.replace("Bearer ", "");

        if(OpenEndpoints.LOGOUT.equalsIgnoreCase(method.value())) {
            jwtCache.invalidateEntry(token);
            return chain.filter(exchange);
        }

        var jwtValid = jwtCache.jwtValid(token);

        if(!jwtValid) {
            logger.error("Token is not valid");
            throw new UnauthorizedException("User is not authorized");
        }
        var jwtBody = jwtUtil.retrieveAllClaims(token);
        var tokenExpired = jwtUtil.isTokenExpired(jwtBody.getExpiration());
        if(tokenExpired) {
            logger.error("Error occurred while retrieving claims");
            throw new UnauthorizedException("Token expired.");
        }
        var role = jwtBody.get("role");
        if(!Role.check(role.toString())) {
            logger.error("Error occurred while retrieving claims");
            throw new UnauthorizedException("Provided role is not enough for this request");
        }
        var systemRole = Role.fromString(role.toString());

        var methodAllowed = new AtomicBoolean(false);
        var allMethodsForRole = PathPattern.allowedMethodsPerRole.get(systemRole);
        allMethodsForRole.forEach(methodPattern-> {
            var pattern = Pattern.compile(methodPattern);
            var matcher = pattern.matcher(method.value());
            if(matcher.find()) {
                methodAllowed.set(true);
            }
        });
        if(!methodAllowed.get()) {
            logger.error("Provided role is not enough for this request");
            throw new UnauthorizedException("Provided role is not enough for this request");
        }

        return chain.filter(exchange);
    }
}
