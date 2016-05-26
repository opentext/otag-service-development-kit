/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.api;

import com.opentext.otag.sdk.types.v3.SDKType;

/**
 * General success/failure outcome response.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.0.1
 */
public class SDKResponse extends SDKType {

    private boolean success;

    public SDKResponse(boolean success) {
        this.success = success;
    }

    public SDKResponse(boolean success, SDKCallInfo sdkCallInfo) {
        super(sdkCallInfo);
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SDKResponse that = (SDKResponse) o;

        return success == that.success;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (success ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SDKResponse{" +
                "success=" + success + "," +
                "callInfo=" + getSdkCallInfo() +
                '}';
    }

}
