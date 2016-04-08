/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.settings;

import com.opentext.otag.sdk.types.v3.SDKType;
import com.opentext.otag.sdk.types.v3.api.SDKCallInfo;

import java.util.ArrayList;
import java.util.List;

public class Settings extends SDKType {

    private List<Setting> settings = new ArrayList<>();

    public Settings(List<Setting> settings) {
        this.settings = settings;
    }

    public Settings(List<Setting> settings, SDKCallInfo sdkCallInfo) {
        super(sdkCallInfo);
        this.settings = settings;
    }

    public List<Setting> getSettings() {
        return settings;
    }

    public void setSettings(List<Setting> settings) {
        this.settings = settings;
    }

}
