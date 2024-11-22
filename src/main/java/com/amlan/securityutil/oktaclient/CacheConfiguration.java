package com.amlan.securityutil.oktaclient;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@EnableCaching
@Configuration
@ConditionalOnExpression("okta.client.enable:false")
public class CacheConfiguration {
    @Value("${client.cache.mazsize}")
    private int maxSize;
    @Value("${client.cache.expireSecond}")
    private int expireSecond;


    @Bean
    @Qualifier("clientCacheManager")
    public CacheManager clientCacheManager(){
        final CaffeineCache oktaTokensCache = buildCache("oktaTokens",maxSize,expireSecond);
        final SimpleCacheManager manager = new SimpleCacheManager();
        manager.setCaches(Arrays.asList(oktaTokensCache));
        return manager;
    }

    private CaffeineCache buildCache(final String name, int maxSize, int expireSecond) {
        return new CaffeineCache(name, 
                    Caffeine.newBuilder().expireAfterWrite(expireSecond,TimeUnit.SECONDS)
                                            .maximumSize(maxSize)
                                            .build());
    }

}
