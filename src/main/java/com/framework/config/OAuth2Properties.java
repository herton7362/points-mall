package com.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="oauth2.framework")
public class OAuth2Properties {
    private String accessTokenUri;
    private String userAuthorizationUri;
    private String findMemberUrl;
    private String saveMemberUrl;

    public String getAccessTokenUri() {
        return accessTokenUri;
    }

    public void setAccessTokenUri(String accessTokenUri) {
        this.accessTokenUri = accessTokenUri;
    }

    public String getUserAuthorizationUri() {
        return userAuthorizationUri;
    }

    public void setUserAuthorizationUri(String userAuthorizationUri) {
        this.userAuthorizationUri = userAuthorizationUri;
    }

    public String getFindMemberUrl() {
        return findMemberUrl;
    }

    public void setFindMemberUrl(String findMemberUrl) {
        this.findMemberUrl = findMemberUrl;
    }

    public String getSaveMemberUrl() {
        return saveMemberUrl;
    }

    public void setSaveMemberUrl(String saveMemberUrl) {
        this.saveMemberUrl = saveMemberUrl;
    }
}
