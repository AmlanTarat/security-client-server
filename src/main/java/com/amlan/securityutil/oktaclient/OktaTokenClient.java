package com.amlan.securityutil.oktaclient;

import java.net.URI;
import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;

import feign.HeaderMap;
import feign.RequestLine;

@ConditionalOnProperty(name="okta.client.enable", havingValue = "true")
@FeignClient(name="OktaTokenClient",configuration=FeignClientConfig.class)
public interface OktaTokenClient {

    @RequestLine("POST")
    public TokenData getToken(URI uri, @HeaderMap Map<String,String> header);
}
