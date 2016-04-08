/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.auth;

import com.opentext.otag.sdk.util.Cookie;

import java.io.Serializable;
import java.util.Set;

public class AuthHandler implements Serializable {

    private String handler;

    private boolean decorator;

    private String otdsResourceId;

    private Set<Cookie> knownCookies;

    public AuthHandler() {
    }

    public AuthHandler(String handler, boolean isDecorator, Set<Cookie> knownCookies) {
        this.handler = handler;
        this.decorator = isDecorator;
        this.knownCookies = knownCookies;
    }

    public AuthHandler(String handler, boolean isDecorator,
                       String otdsResourceId, Set<Cookie> knownCookies) {
        this.handler = handler;
        this.decorator = isDecorator;
        this.otdsResourceId = otdsResourceId;
        this.knownCookies = knownCookies;
    }

    public String getHandler() {
        return handler;
    }

    public boolean isDecorator() {
        return decorator;
    }

    public String getOtdsResourceId() {
        return otdsResourceId;
    }

    public Set<Cookie> getKnownCookies() {
        return knownCookies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthHandler that = (AuthHandler) o;

        if (decorator != that.decorator) return false;
        if (handler != null ? !handler.equals(that.handler) : that.handler != null) return false;
        if (otdsResourceId != null ? !otdsResourceId.equals(that.otdsResourceId) : that.otdsResourceId != null)
            return false;
        return knownCookies != null ? knownCookies.equals(that.knownCookies) : that.knownCookies == null;
    }

    @Override
    public int hashCode() {
        int result = handler != null ? handler.hashCode() : 0;
        result = 31 * result + (decorator ? 1 : 0);
        result = 31 * result + (otdsResourceId != null ? otdsResourceId.hashCode() : 0);
        result = 31 * result + (knownCookies != null ? knownCookies.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AuthHandler{" +
                "handler='" + handler + '\'' +
                ", decorator=" + decorator +
                ", otdsResourceId='" + otdsResourceId + '\'' +
                ", knownCookies=" + knownCookies +
                '}';
    }

}
