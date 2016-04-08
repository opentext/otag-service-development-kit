/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.util;

import java.util.Locale;

public class UrlPathUtil {

    public static final String HTTP = "http://";
    public static final String HTTPS = "https://";

    /**
     * Extract the base URL (minus path) from the supplied full URL.
     *
     * @param fullUrl URL String
     * @return base path
     */
    public static String getBaseUrl(String fullUrl) {
        int endScheme;

        if (fullUrl == null || fullUrl.isEmpty())
            throw new IllegalArgumentException("Invalid register URL");

        if (fullUrl.toLowerCase(Locale.US).contains(HTTP)) {
            endScheme = HTTP.length();
        } else if (fullUrl.toLowerCase(Locale.US).contains(HTTPS)) {
            endScheme = HTTPS.length();
        } else {
            throw new IllegalArgumentException("Register URL was not http or https???");
        }

        int slashIdx = fullUrl.indexOf('/', endScheme);
        if (slashIdx != -1)
            return fullUrl.substring(0, slashIdx);

        return null;
    }

    /**
     * Extract the path component from the supplied full URL.
     *
     * @param fullUrl URL String
     * @return path component, if one exists
     */
    public static String getPath(String fullUrl) {
        String baseUrl = getBaseUrl(fullUrl);

        if (baseUrl != null) {
            // we don't want the leading slash in the path we feed to the
            // Jersey client path() method
            int beginIndex = baseUrl.length() + 1;
            return fullUrl.substring(beginIndex, fullUrl.length());
        }

        return null;
    }

}

