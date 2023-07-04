package com.master.amsterdam.configuration;

import com.master.amsterdam.model.PathPattern;
import com.master.amsterdam.model.RouteBuilder;
import com.master.amsterdam.model.URIBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;


@Configuration
public class GatewayConfiguration {

    private final URIBean uriBean;
    private final AuthenticationFilter authenticationFilter;

    @Autowired
    public GatewayConfiguration(URIBean uriBean, AuthenticationFilter authenticationFilter) {
        this.uriBean = uriBean;
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public RouteLocator gateway(RouteLocatorBuilder builder) {

        RouteLocatorBuilder.Builder routeBuilder = builder.routes();

        RouteBuilder.buildRoute(routeBuilder, authenticationFilter,  PathPattern.REGISTER, uriBean.getIstanbulURI(),
                 HttpMethod.GET, HttpMethod.OPTIONS);
        RouteBuilder.buildRoute(routeBuilder, authenticationFilter,  PathPattern.REGISTER_PATH_VARAIBLE, uriBean.getIstanbulURI(),
                 HttpMethod.GET, HttpMethod.OPTIONS);
        RouteBuilder.buildRoute(routeBuilder, authenticationFilter,  PathPattern.AUTH, uriBean.getAlexandriaURI(),
                 HttpMethod.POST, HttpMethod.OPTIONS);
        RouteBuilder.buildRoute(routeBuilder, authenticationFilter,  PathPattern.LOGOUT, uriBean.getAlexandriaURI(),
                 HttpMethod.DELETE, HttpMethod.OPTIONS);


        return routeBuilder.build();


    }
}