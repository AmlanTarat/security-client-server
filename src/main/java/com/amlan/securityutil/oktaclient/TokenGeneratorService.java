package com.amlan.securityutil.oktaclient;



import java.net.URI;
import java.util.Arrays;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.amlan.securityutil.oktaclient.OktaClientProperties.Credential;


import lombok.extern.slf4j.Slf4j;

@Service
@ConditionalOnProperty(name="okta.client.enable",havingValue = "true")
@CacheConfig(cacheNames={"oktaTokens"}, cacheManager="clientCacheManager")
@Slf4j
public class TokenGeneratorService {

    private OktaClientProperties oktaClientProperties;
    private OktaTokenClient oktaTokenClient;

    public TokenGeneratorService(OktaClientProperties oktaClientProperties, OktaTokenClient oktaTokenClient){
        this.oktaClientProperties=oktaClientProperties;
        this.oktaTokenClient=oktaTokenClient;
    }

    @Cacheable(value="oktaTokens", key="#resourceServer")
    public String getToken(String resourceServer){
        Credential credential = oktaClientProperties.getOktaClientProperties().get(resourceServer);
        String tokenUri = getTokenUri(credential,resourceServer);
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(credential.getClientId(),credential.getClientSecret());
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        TokenData response = null;
        try{
            URI uri = new URI(tokenUri);
            response = oktaTokenClient.getToken(uri, headers.toSingleValueMap());
        }catch(Exception e){
            log.error("{}", e.getMessage());
        }
        
        
        if(response ==null || response.getAccessToken()==null){
            throw new RuntimeException("error generating token");
        }else{
            return response.getAccessToken();
        }
    }

    private String getTokenUri(Credential credential, String resourceServer) {
        
        String scopes = credential.getScope().stream().reduce((x,y)->x+"+"+y)
                            .orElseThrow(()-> new RuntimeException());
        String queryParam ="/v1/token?grant_type=client_credentials&response_type=token&scope="+scopes;
        return new StringBuilder(credential.getIssuerUri()).append(queryParam).toString();
    }
}
