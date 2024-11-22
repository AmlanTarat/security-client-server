package com.amlan.securityutil.oktaserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@EnableWebSecurity
@Configuration
@Order(100)
public class OktaSecurityConfig {

    @Autowired
    private OktaServerProperties oktaServerProperties;

    @Bean
    @ConditionalOnProperty(name="okta.server.enable", havingValue="true")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        RequestMatcher requestMatcher = new RequestHeaderRequestMatcher("Authorization");
        http.securityMatcher(requestMatcher)
            .authorizeHttpRequests(t-> {
                    oktaServerProperties.getOktaServerSecurity()
                        .forEach(api->t.requestMatchers("/actuator/**").permitAll().anyRequest().authenticated());
            })
            .oauth2ResourceServer(oauth2->oauth2.jwt(Customizer.withDefaults()));

            return http.build();
    }

}
