package com.example.demo.config;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "firebase")
public class FirebaseProperties {
    private String projectId;
    private String keyPath;

    public String getProjectId() {
        return projectId;
    }

    public String getKeyPath() {
        return keyPath;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void setKeyPath(String keyPath) {
        this.keyPath = keyPath;
    }
}
