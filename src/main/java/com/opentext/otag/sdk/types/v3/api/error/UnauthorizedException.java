/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.api.error;

import com.opentext.otag.sdk.types.v3.api.SDKCallInfo;

/**
 * 401 wrapper.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.2
 */
public class UnauthorizedException extends APIException {

    public UnauthorizedException(SDKCallInfo callInfo) {
        super(callInfo);
    }

    public UnauthorizedException(String message, Throwable cause, SDKCallInfo callInfo) {
        super(message, cause, callInfo);
        validateCallInfo(callInfo);
    }

    private void validateCallInfo(SDKCallInfo callInfo) {
        if (callInfo == null || 401 != callInfo.getResponseStatus())
            throw new IllegalArgumentException("We didn't receive SDK Call info relating to a 401 UNAUTHORIZED?");
    }

}
