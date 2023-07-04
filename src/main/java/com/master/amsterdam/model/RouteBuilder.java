package com.master.amsterdam.model;

import com.master.amsterdam.configuration.AuthenticationFilter;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.http.HttpMethod;


public class RouteBuilder {


    public static void buildRoute(RouteLocatorBuilder.Builder builder, AuthenticationFilter authenticationFilter, String pathPattern, String uri, HttpMethod... methods) {

        builder.route(predicateSpec -> predicateSpec
                .method(methods)
                .and()
                .path(pathPattern)
                .filters(gatewayFilterSpec -> gatewayFilterSpec
                        .filter(authenticationFilter, 1))
                .uri(uri));
    }
}
