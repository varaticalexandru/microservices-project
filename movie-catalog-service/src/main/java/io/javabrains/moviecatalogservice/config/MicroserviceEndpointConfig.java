package io.javabrains.moviecatalogservice.config;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "microservice-endpoints")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MicroserviceEndpointConfig {
    String movieRatingServiceUrl;
    String movieInfoServiceUrl;
}
