/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.apps;

import com.opentext.otag.sdk.types.v3.SDKType;
import com.opentext.otag.sdk.types.v3.api.SDKCallInfo;

import java.util.List;

public class Runtimes extends SDKType {

    private List<Runtime> runtimes;

    public Runtimes(List<Runtime> runtimes) {
        this.runtimes = runtimes;
    }

    public Runtimes(List<Runtime> runtimes, SDKCallInfo sdkCallInfo) {
        super(sdkCallInfo);
        this.runtimes = runtimes;
    }

    public List<Runtime> getRuntimes() {
        return runtimes;
    }

    public void setRuntimes(List<Runtime> runtimes) {
        this.runtimes = runtimes;
    }

}
