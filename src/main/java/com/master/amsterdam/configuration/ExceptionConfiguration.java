package com.master.amsterdam.configuration;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ExceptionConfiguration {

    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }
}
