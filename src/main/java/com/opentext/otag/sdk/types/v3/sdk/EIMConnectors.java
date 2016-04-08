/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.sdk;

import com.opentext.otag.sdk.types.v3.SDKType;
import com.opentext.otag.sdk.types.v3.api.SDKCallInfo;

import java.util.ArrayList;
import java.util.List;

public class EIMConnectors extends SDKType {

    private List<EIMConnector> connectors = new ArrayList<>();

    public EIMConnectors(List<EIMConnector> connectors) {
        this.connectors = connectors;
    }

    public EIMConnectors(List<EIMConnector> connectors, SDKCallInfo sdkCallInfo) {
        super(sdkCallInfo);
        this.connectors = connectors;
    }

    public List<EIMConnector> getConnectors() {
        return connectors;
    }

    public void setConnectors(List<EIMConnector> connectors) {
        this.connectors = connectors;
    }

}
