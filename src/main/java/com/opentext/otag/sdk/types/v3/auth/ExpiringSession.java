/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.opentext.otag.sdk.types.v3.SDKType;
import com.opentext.otag.sdk.types.v3.api.SDKCallInfo;

import java.io.Serializable;
import java.util.Date;

public class ExpiringSession extends SDKType implements Serializable {

    @JsonProperty("expires")
    protected Date expires;

    public ExpiringSession() {
    }

    public ExpiringSession(long maxAge) {
        touch(maxAge);
    }

    public ExpiringSession(Date expires) {
        this.expires = expires;
    }

    public ExpiringSession(SDKCallInfo sdkCallInfo, Date expires) {
        super(sdkCallInfo);
        this.expires = expires;
    }

    public void touch(long maxAge) {
        expires = new Date(System.currentTimeMillis() + maxAge);
    }

    public Date getExpires() {
        return expires;
    }

    @JsonIgnore
    public boolean isExpired() {
        return expires.getTime() < System.currentTimeMillis();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ExpiringSession that = (ExpiringSession) o;

        return expires != null ? expires.equals(that.expires) : that.expires == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (expires != null ? expires.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ExpiringSession{" +
                "expires=" + expires +
                '}';
    }

}
