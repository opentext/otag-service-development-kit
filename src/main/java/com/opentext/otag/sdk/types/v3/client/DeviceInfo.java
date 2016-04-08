/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.client;

import java.io.Serializable;

public class DeviceInfo implements Serializable {

    private String deviceID;

    /**
     * iOS app id, the bundle id.
     */
    private String appID;

    private String model;

    /**
     * Has this iOS device (if it is an iOS device) been jailbroken?
     */
    private Boolean jailBreakStatus;

    /**
     * Used for both the GCM (Google Cloud Messaging) and APNS (Apple Push
     * Notification Service) communication with devices.
     */
    private String cloudPushKey;

    public DeviceInfo() {
    }

    public DeviceInfo(String deviceID, String appID, String model,
                      Boolean jailBreakStatus, String cloudPushKey) {
        this.deviceID = deviceID;
        this.appID = appID;
        this.model = model;
        this.jailBreakStatus = jailBreakStatus;
        this.cloudPushKey = cloudPushKey;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public String getAppID() {
        return appID;
    }

    public String getModel() {
        return model;
    }

    public Boolean getJailBreakStatus() {
        return jailBreakStatus;
    }

    public String getCloudPushKey() {
        return cloudPushKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeviceInfo that = (DeviceInfo) o;

        if (deviceID != null ? !deviceID.equals(that.deviceID) : that.deviceID != null) return false;
        if (appID != null ? !appID.equals(that.appID) : that.appID != null) return false;
        if (model != null ? !model.equals(that.model) : that.model != null) return false;
        if (jailBreakStatus != null ? !jailBreakStatus.equals(that.jailBreakStatus) : that.jailBreakStatus != null)
            return false;
        return cloudPushKey != null ? cloudPushKey.equals(that.cloudPushKey) : that.cloudPushKey == null;
    }

    @Override
    public int hashCode() {
        int result = deviceID != null ? deviceID.hashCode() : 0;
        result = 31 * result + (appID != null ? appID.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (jailBreakStatus != null ? jailBreakStatus.hashCode() : 0);
        result = 31 * result + (cloudPushKey != null ? cloudPushKey.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "deviceID='" + deviceID + '\'' +
                ", appID='" + appID + '\'' +
                ", model='" + model + '\'' +
                ", jailBreakStatus=" + jailBreakStatus +
                ", cloudPushKey='" + cloudPushKey + '\'' +
                '}';
    }

}
