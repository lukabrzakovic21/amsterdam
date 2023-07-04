package com.master.amsterdam.configuration;

import com.master.amsterdam.model.URIBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class URIConfiguration {

    @Value("${uris.istanbul}")
    private String istanbulURI;

    @Value("${uris.alexandria}")
    private String alexandriaURI;

    @Bean
    public URIBean uriBean() {
        return new URIBean(istanbulURI, alexandriaURI);
    }

}
