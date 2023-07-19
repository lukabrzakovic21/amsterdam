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

    @Value("${uris.milano}")
    private String milanoURI;

    @Bean
    public URIBean uriBean() {
        return new URIBean(istanbulURI, alexandriaURI, milanoURI);
    }

}
