package com.master.amsterdam.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

import java.net.ConnectException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
public class ErrorResponseAttributes extends DefaultErrorAttributes {


    private final List<ExceptionRule> exceptionsRules = List.of(
            new ExceptionRule(UnauthorizedException.class, HttpStatus.UNAUTHORIZED),
            new ExceptionRule(ResourceAccessException.class, HttpStatus.SERVICE_UNAVAILABLE),
            new ExceptionRule(ConnectException.class, HttpStatus.SERVICE_UNAVAILABLE)
    );

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {


        var error = getError(request);

        final String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        Optional<ExceptionRule> exceptionRuleOptional = exceptionsRules.stream()
                .map(exceptionRule -> exceptionRule.exceptionClass().isInstance(error) ? exceptionRule : null)
                .filter(Objects::nonNull)
                .findFirst();
        return exceptionRuleOptional.<Map<String, Object>>map(exceptionRule -> Map.of("status", exceptionRule.status().value(), "message", error.getMessage(),  "timestamp", timestamp))
                .orElseGet(() -> Map.of("status", determineHttpStatus(error).value(),  "message", error.getMessage(), "timestamp", timestamp));

    }

    private HttpStatus determineHttpStatus(Throwable error) {
        return error instanceof ResponseStatusException err ? (HttpStatus) err.getStatusCode() : MergedAnnotations.from(error.getClass(), MergedAnnotations.SearchStrategy.TYPE_HIERARCHY).get(ResponseStatus.class).getValue("status", HttpStatus.class).orElse(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
