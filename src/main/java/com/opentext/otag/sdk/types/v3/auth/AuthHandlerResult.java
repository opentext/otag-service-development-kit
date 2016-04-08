/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.*;
import com.opentext.otag.sdk.util.Cookie;

import java.util.HashMap;
import java.util.Map;

/**
 * AppWorks Service auth handler response. Provides access to an {@link ObjectMapper} to
 * aid in the creation of additional auth response body properties via {@link JsonNode}.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.0.0
 *
 * @see ObjectMapper#createObjectNode()
 * @see ObjectMapper#createArrayNode()
 */
public class AuthHandlerResult {

    /**
     * Shared mapper, used to manage additional body properties.
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Outcome of request.
     */
    protected boolean success;

    /**
     * Admin level user authed?
     */
    protected boolean admin;

    /**
     * Failure message.
     */
    protected String errorMessage;

    /**
     * The name used by the auth service for the user being authed.
     */
    protected String username;

    protected UserProfile userProfile;

    /**
     * Return cookies.
     */
    protected Map<String, Cookie> cookies = new HashMap<>();

    /**
     * Content to add to the body of the eventual auth response.
     */
    protected JsonNode addtlRespFields;

    public AuthHandlerResult() {
        addtlRespFields = MAPPER.createObjectNode();
    }

    public AuthHandlerResult(boolean success) {
        this.success = success;
    }

    // failed auth ctor
    public AuthHandlerResult(boolean success, String errorMessage) {
        this();
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isAdmin() {
        return admin;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getUsername() {
        return username;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public Map<String, Cookie> getCookies() {
        return cookies;
    }

    public JsonNode getAddtlRespFields() {
        return addtlRespFields;
    }

    public void addAdditionalProperty(String key, String data) {
        ((ObjectNode) addtlRespFields).set(key, TextNode.valueOf(data));
    }

    public void addAdditionalProperty(String key, long data) {
        ((ObjectNode) addtlRespFields).set(key, LongNode.valueOf(data));
    }

    public void addAdditionalProperty(String key, boolean bool) {
        ((ObjectNode) addtlRespFields).set(key, BooleanNode.valueOf(bool));
    }

    @JsonIgnore
    public ObjectMapper getMapper() {
        return MAPPER;
    }

    /**
     * Convenience method to grab an array node you can pass back as an
     * additional property.
     *
     * @return a JSON array node
     */
    @JsonIgnore
    public ArrayNode getArrayNode() {
        return MAPPER.createArrayNode();
    }

    /**
     * Add a JsonNode under a specific key. We have the full gamut of JsonNode
     * subclasses to choose from, TextNode, NumericNode (and its specific sub types)
     * as well as the ArrayNode.
     *
     * @param key  the key to store the value under
     * @param data the JSON node to add
     * @see #getMapper()
     * @see #getArrayNode()
     */
    public void addAdditionalProperty(String key, JsonNode data) {
        ((ObjectNode) addtlRespFields).set(key, data);
    }

    public void addRootCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookies.put(name, cookie);
    }

    public void addHttpOnlyRootCookie(String name, String value, String domain) {
        Cookie cookie = new Cookie(name, value);
        if (domain != null && !domain.trim().isEmpty()) {
            cookie.setDomain(domain);
        }
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookies.put(name, cookie);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthHandlerResult that = (AuthHandlerResult) o;

        return success == that.success && admin == that.admin &&
                !(errorMessage != null ? !errorMessage.equals(that.errorMessage) : that.errorMessage != null) &&
                !(username != null ? !username.equals(that.username) : that.username != null) &&
                !(userProfile != null ? !userProfile.equals(that.userProfile) : that.userProfile != null) &&
                !(cookies != null ? !cookies.equals(that.cookies) : that.cookies != null) &&
                !(addtlRespFields != null ? !addtlRespFields.equals(that.addtlRespFields) : that.addtlRespFields != null);
    }

    @Override
    public int hashCode() {
        int result = (success ? 1 : 0);
        result = 31 * result + (admin ? 1 : 0);
        result = 31 * result + (errorMessage != null ? errorMessage.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (userProfile != null ? userProfile.hashCode() : 0);
        result = 31 * result + (cookies != null ? cookies.hashCode() : 0);
        result = 31 * result + (addtlRespFields != null ? addtlRespFields.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AuthHandlerResult{" +
                "success=" + success +
                ", admin=" + admin +
                ", errorMessage='" + errorMessage + '\'' +
                ", username='" + username + '\'' +
                ", userProfile=" + userProfile +
                ", cookies=" + cookies +
                ", addtlRespFields=" + addtlRespFields +
                '}';
    }

}
