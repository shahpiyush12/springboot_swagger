package com.pge.config;

import com.pge.controller.BookController;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import java.net.UnknownHostException;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * {@code JerseyConfiguration} configures Glassfish Jersey server with Spring
 * Boot.
 * <p/>
 *
 * @author Piyush
 */
@Configuration
public class JerseyConfiguration extends ResourceConfig {

    @Autowired
    public JerseyConfiguration() throws UnknownHostException {
        register(BookController.class);
        configureSwagger();
        property(ServletProperties.FILTER_FORWARD_ON_404, true);
    }

    public void configureSwagger() {
        this.register(ApiListingResource.class);
        this.register(SwaggerSerializers.class);
        BeanConfig config = new BeanConfig();
        config.setConfigId("springboot-jersey-swagger");
        config.setTitle("Spring, Jersey, and Swagger Example");
        config.setVersion("1.0.0");
        config.setBasePath("/");
        config.setResourcePackage("com.pge");
        config.setScan(true);
    }
}
