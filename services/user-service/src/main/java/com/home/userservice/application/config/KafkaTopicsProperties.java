package com.home.userservice.application.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "app.kafka.topics.user")
public class KafkaTopicsProperties {
    private String created;
    private String updated;
    private String deleted;
}


