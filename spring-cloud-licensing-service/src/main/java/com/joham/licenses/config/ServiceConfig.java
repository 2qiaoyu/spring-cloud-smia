package com.joham.licenses.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author joham
 */
@Component
@RefreshScope
public class ServiceConfig {

    @Value("${example.property}")
    private String exampleProperty;

    public String getExampleProperty() {
        return exampleProperty;
    }
}
