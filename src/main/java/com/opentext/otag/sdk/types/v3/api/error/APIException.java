/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.api.error;

import com.opentext.otag.sdk.types.v3.api.SDKCallInfo;
import com.opentext.otag.sdk.util.StringUtil;

/**
 * Error response wrapper.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.2
 */
public class APIException extends RuntimeException {

    private SDKCallInfo callInfo;

    public APIException(SDKCallInfo callInfo) {
        this.callInfo = callInfo;
    }

    public APIException(String message, SDKCallInfo callInfo) {
        super(message);
        this.callInfo = callInfo;
    }

    public APIException(String message, Throwable cause, SDKCallInfo callInfo) {
        super(message, cause);
        this.callInfo = callInfo;
    }

    @Override
    public String getMessage() {
        if (callInfo != null) {
            if (!StringUtil.isNullOrEmpty(callInfo.getErrorMessage()))
                return callInfo.getErrorMessage();

            Exception exception = callInfo.getException();
            if (exception != null && !StringUtil.isNullOrEmpty(exception.getMessage()))
                return exception.getMessage();
        }
        return super.getMessage();
    }

    public SDKCallInfo getCallInfo() {
        return callInfo;
    }

    public void setCallInfo(SDKCallInfo callInfo) {
        this.callInfo = callInfo;
    }

    public int getStatus() {
        return (callInfo != null) ? callInfo.getResponseStatus() : -1;
    }

    @Override
    public String toString() {
        return "APIException{" +
                "callInfo=" + callInfo +
                '}';
    }

}
