package com.nexters.wiw.api.config;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("mqtt")
public class MqttProperties {
    private List<String> servers = new ArrayList<>();
    private String username;
    private String password;
    private Boolean cleanSession;
    private Boolean async;
    private int completionTimeout;
    private int keepAliveInterval;
    private String clientId;
    private int defaultQos; 

    public String[] getServers(){
        String[] result = new String[servers.size()];
        return servers.toArray(result);
    }
}