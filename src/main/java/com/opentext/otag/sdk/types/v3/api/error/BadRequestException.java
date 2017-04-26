/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.api.error;

import com.opentext.otag.sdk.types.v3.api.SDKCallInfo;

/**
 * 400 wrapper.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.2
 */
public class BadRequestException extends APIException {

    public BadRequestException(SDKCallInfo callInfo) {
        super(callInfo);
        validateCallInfo(callInfo);
    }

    public BadRequestException(String message, Throwable cause, SDKCallInfo callInfo) {
        super(message, cause, callInfo);
        validateCallInfo(callInfo);
    }

    private void validateCallInfo(SDKCallInfo callInfo) {
        if (callInfo == null || 400 != callInfo.getResponseStatus())
            throw new IllegalArgumentException("We didn't receive SDK Call info relating to a 400 BAD REQUEST?");
    }

}
