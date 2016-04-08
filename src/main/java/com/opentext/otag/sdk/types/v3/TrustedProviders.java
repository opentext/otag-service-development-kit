/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3;

import com.opentext.otag.sdk.types.v3.api.SDKCallInfo;

import java.util.ArrayList;
import java.util.List;

public class TrustedProviders extends SDKType {

    private List<TrustedProvider> trustedProviders = new ArrayList<>();

    public TrustedProviders() {}

    public TrustedProviders(List<TrustedProvider> trustedProviders) {
        this.trustedProviders = trustedProviders;
    }

    public TrustedProviders(List<TrustedProvider> trustedProviders, SDKCallInfo sdkCallInfo) {
        super(sdkCallInfo);
        this.trustedProviders = trustedProviders;
    }

    public List<TrustedProvider> getTrustedProviders() {
        return trustedProviders;
    }

}
