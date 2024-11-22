package com.amlan.securityutil.oktaserver;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class OktaServerProperties {

    private List<ApiDetails> oktaServerSecurity = new ArrayList<>();

    public List<ApiDetails> getOktaServerSecurity(){
        return oktaServerSecurity;
    }

    public static class ApiDetails{
        private List<String> paths;
        private List<String> scopes;

        public List<String> getPaths(){
            return paths;
        }
        public void setPaths(List<String> paths){
            this.paths=paths;
        }
        public List<String> getScopes(){
            return paths;
        }
        public void setScopes(List<String> scopes){
            this.scopes=scopes;
        }
    }
}
