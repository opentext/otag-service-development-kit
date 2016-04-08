/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opentext.otag.sdk.types.v3.api.SDKCallInfo;

/**
 * SDK super type, used to retain useful information about
 * the call used to retrieve the type. These types are used with the SDK clients mostly.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.0.0
 */
public class SDKType {

    /**
     * SDK call information, retained no matter the outcome.
     */
    @JsonIgnore // do not include this is JSON marshalling, it is for local debugging purposes
    private SDKCallInfo sdkCallInfo;

    public SDKType() {
    }

    public SDKType(SDKCallInfo sdkCallInfo) {
        this.sdkCallInfo = sdkCallInfo;
    }

    public SDKCallInfo getSdkCallInfo() {
        return sdkCallInfo;
    }

    public void setSdkCallInfo(SDKCallInfo sdkCallInfo) {
        this.sdkCallInfo = sdkCallInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SDKType sdkType = (SDKType) o;

        return sdkCallInfo != null ? sdkCallInfo.equals(sdkType.sdkCallInfo) : sdkType.sdkCallInfo == null;
    }

    @Override
    public int hashCode() {
        return sdkCallInfo != null ? sdkCallInfo.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "SDKType{" +
                "sdkCallInfo=" + sdkCallInfo +
                '}';
    }

}
