package com.amlan.securityutil.oktaclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.amlan.securityutil.oktaserver.OktaServerProperties.ApiDetails;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@ConfigurationProperties
public class OktaClientProperties {

    private Map<String, Credential> oktaClientProperties = new HashMap<>();

    public Map<String, Credential> getOktaClientProperties(){
        return oktaClientProperties;
    }

    @Data
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Credential{
        private String clientId;
        private String clientSecret;
        private String issuerUri;
        private List<String> scope = new ArrayList<>();
    }
}
