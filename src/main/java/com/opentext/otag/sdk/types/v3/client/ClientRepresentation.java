/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.client;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class ClientRepresentation implements Serializable {

    private String clientId;

    private ClientInfo clientInfo;

    private UserInfo userInfo;

    private DeviceInfo deviceInfo;

    public ClientRepresentation() {
    }

    public ClientRepresentation(String clientId,
                                ClientInfo clientInfo,
                                UserInfo userInfo,
                                DeviceInfo deviceInfo) {
        this.clientId = clientId;
        this.clientInfo = clientInfo;
        this.userInfo = userInfo;
        this.deviceInfo = deviceInfo;
    }

    public String getClientId() {
        return clientId;
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    @JsonIgnore
    public String getType() {
        return (clientInfo != null) ? clientInfo.getType() : "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientRepresentation that = (ClientRepresentation) o;

        if (clientId != null ? !clientId.equals(that.clientId) : that.clientId != null) return false;
        if (clientInfo != null ? !clientInfo.equals(that.clientInfo) : that.clientInfo != null) return false;
        if (userInfo != null ? !userInfo.equals(that.userInfo) : that.userInfo != null) return false;
        return deviceInfo != null ? deviceInfo.equals(that.deviceInfo) : that.deviceInfo == null;
    }

    @Override
    public int hashCode() {
        int result = clientId != null ? clientId.hashCode() : 0;
        result = 31 * result + (clientInfo != null ? clientInfo.hashCode() : 0);
        result = 31 * result + (userInfo != null ? userInfo.hashCode() : 0);
        result = 31 * result + (deviceInfo != null ? deviceInfo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ClientRepresentation{" +
                "clientId='" + clientId + '\'' +
                ", clientInfo=" + clientInfo +
                ", userInfo=" + userInfo +
                ", deviceInfo=" + deviceInfo +
                '}';
    }

}