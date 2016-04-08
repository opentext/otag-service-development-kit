/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.http.HttpMessage;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * ForwardHeaders allows headers from incoming requests to be forwarded by
 * subsequent requests.
 * <p>
 * This also provides validation of headers such as 'x-forwarded-for' which
 * will be set based on the IP of the incoming request.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.0.0
 */
public class ForwardHeaders implements Serializable {

    public static final String IF_MODIFIED_SINCE = "If-Modified-Since";
    public static final String LANGUAGE_HEADER_KEY_NAME = "accept-language";
    public static final String USER_AGENT_HEADER_NAME = "user-agent";
    public final static String FORWARDED_FOR_HEADER_KEY_NAME = "X-Forwarded-For";

    private String remoteAddr;
    private String forwardHeader;
    private String userAgent;
    private String lang;
    private String ifModifiedSince;

    private String uri;
    private Map<String, String> otherHeaders = new HashMap<>();

    public ForwardHeaders() {
    }

    @JsonCreator
    public ForwardHeaders(@JsonProperty("remoteAddr") String remoteAddr,
                          @JsonProperty("forwardHeader") String forwardHeader,
                          @JsonProperty("userAgent") String userAgent,
                          @JsonProperty("lang") String lang,
                          @JsonProperty("ifModifiedSince") String ifModifiedSince,
                          @JsonProperty("uri") String uri,
                          @JsonProperty("otherHeaders") Map<String, String> otherHeaders) {
        this.remoteAddr = remoteAddr;
        this.forwardHeader = forwardHeader;
        this.userAgent = userAgent;
        this.lang = lang;
        this.ifModifiedSince = ifModifiedSince;
        this.uri = uri;
        this.otherHeaders = otherHeaders;
    }

    public ForwardHeaders(final HttpServletRequest request) {

        this.remoteAddr = request.getRemoteAddr();
        this.lang = request.getHeader(LANGUAGE_HEADER_KEY_NAME);
        this.ifModifiedSince = request.getHeader(IF_MODIFIED_SINCE);
        this.uri = request.getRequestURI();

		/*
         * Append the incoming REMOTE_ADDR to the list of X-Forwarded-For values
		 */
        String forward = request.getHeader(FORWARDED_FOR_HEADER_KEY_NAME);

        if (forward == null || forward.isEmpty()) {
            forward = this.remoteAddr;
        } else {
            forward = forward.concat(",");
            forward = forward.concat(this.remoteAddr);
        }

        this.forwardHeader = forward;

        // We duplicate the user agent as CS sometimes alters its response based on the browser; in particular,
        // download filenames appear incorrectly in some browsers without this
        String declaredUserAgent = request.getHeader(USER_AGENT_HEADER_NAME);

        // if the user-agent is Chrome, forward depending on config setting to work around CS bug

        if (declaredUserAgent != null && declaredUserAgent.contains("Chrome/")) {
            this.userAgent = null;
        } else {
            this.userAgent = declaredUserAgent;
        }
    }

    public String getForwardHeader() {
        return this.forwardHeader;
    }

    public String getRemoteAddr() {
        return this.remoteAddr;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getLang() {
        return lang;
    }

    public String getIfModifiedSince() {
        return ifModifiedSince;
    }

    public String getUri() {
        return uri;
    }

    public Map<String, String> getOtherHeaders() {
        return otherHeaders;
    }

    @JsonIgnore
    public String getOriginalAddr() {
        int commaIndex = forwardHeader.indexOf(',');
        return forwardHeader.substring(0, commaIndex >= 0 ? commaIndex : forwardHeader.length());
    }

    @JsonIgnore
    public void addTo(final HttpMessage request) {
        if (userAgent != null)
            request.addHeader(USER_AGENT_HEADER_NAME, this.userAgent);

        if (lang != null)
            request.addHeader(LANGUAGE_HEADER_KEY_NAME, lang);

        if (ifModifiedSince != null)
            request.addHeader(IF_MODIFIED_SINCE, ifModifiedSince);

        // need to set the forwarded-for header to preserve communication chain
        request.addHeader(FORWARDED_FOR_HEADER_KEY_NAME, this.forwardHeader);

        for (Map.Entry<String, String> entry : otherHeaders.entrySet()) {
            request.addHeader(entry.getKey(), entry.getValue());
        }
    }

    public void addHeader(String name, String value) {
        otherHeaders.put(name, value);
    }

}
