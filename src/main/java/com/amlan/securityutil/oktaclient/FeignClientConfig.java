package com.amlan.securityutil.oktaclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Feign;
import feign.Target;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

@Configuration
public class FeignClientConfig {
    
    @Bean 
    public OktaTokenClient oktaTokenClient(){
        return Feign.builder()
                    .encoder(new JacksonEncoder())
                    .decoder(new JacksonDecoder())
                    .target(Target.EmptyTarget.create(OktaTokenClient.class));
    }
}
