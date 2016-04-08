/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.client;

import java.io.Serializable;
import java.util.Date;

public class ClientInfo implements Serializable {

    /**
     * desktop vs. mobile vs. web
     */
    private String type;

    /**
     * AppWorks client version.
     */
    private String version;

    /**
     * e.g. off-line, available, busy, idle
     */
    private String status;

    /**
     * Pending or complete. See Client.WipeStatus enumeration.
     */
    private String wipeStatus;

    /**
     * AppWorks app name.
     */
    private String app;

    private String os;

    private String osVersion;

    private String bitness;

    private String location;

    private String extraInfo;

    private String runtime;

    private Date firstConnect;

    private Date lastConnect;

    public ClientInfo() {
    }

    public ClientInfo(String type, String version, String status, String wipeStatus,
                      String app, String runtime, String os, String osVersion, String bitness,
                      String location, String extraInfo, Date firstConnect, Date lastConnect) {
        this.type = type;
        this.version = version;
        this.status = status;
        this.wipeStatus = wipeStatus;
        this.app = app;
        this.runtime = runtime;
        this.os = os;
        this.osVersion = osVersion;
        this.bitness = bitness;
        this.location = location;
        this.extraInfo = extraInfo;
        this.firstConnect = firstConnect;
        this.lastConnect = lastConnect;
    }

    public String getType() {
        return type;
    }

    public String getVersion() {
        return version;
    }

    public String getStatus() {
        return status;
    }

    public String getWipeStatus() {
        return wipeStatus;
    }

    public String getApp() {
        return app;
    }

    public String getRuntime() {
        return runtime;
    }

    public Date getFirstConnect() {
        return firstConnect;
    }

    public Date getLastConnect() {
        return lastConnect;
    }

    public String getOs() {
        return os;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public String getBitness() {
        return bitness;
    }

    public String getLocation() {
        return location;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientInfo that = (ClientInfo) o;

        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (wipeStatus != null ? !wipeStatus.equals(that.wipeStatus) : that.wipeStatus != null) return false;
        if (app != null ? !app.equals(that.app) : that.app != null) return false;
        if (os != null ? !os.equals(that.os) : that.os != null) return false;
        if (osVersion != null ? !osVersion.equals(that.osVersion) : that.osVersion != null) return false;
        if (bitness != null ? !bitness.equals(that.bitness) : that.bitness != null) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (extraInfo != null ? !extraInfo.equals(that.extraInfo) : that.extraInfo != null) return false;
        if (runtime != null ? !runtime.equals(that.runtime) : that.runtime != null) return false;
        if (firstConnect != null ? !firstConnect.equals(that.firstConnect) : that.firstConnect != null) return false;
        return lastConnect != null ? lastConnect.equals(that.lastConnect) : that.lastConnect == null;

    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (wipeStatus != null ? wipeStatus.hashCode() : 0);
        result = 31 * result + (app != null ? app.hashCode() : 0);
        result = 31 * result + (os != null ? os.hashCode() : 0);
        result = 31 * result + (osVersion != null ? osVersion.hashCode() : 0);
        result = 31 * result + (bitness != null ? bitness.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (extraInfo != null ? extraInfo.hashCode() : 0);
        result = 31 * result + (runtime != null ? runtime.hashCode() : 0);
        result = 31 * result + (firstConnect != null ? firstConnect.hashCode() : 0);
        result = 31 * result + (lastConnect != null ? lastConnect.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ClientInfo{" +
                "type='" + type + '\'' +
                ", version='" + version + '\'' +
                ", status='" + status + '\'' +
                ", wipeStatus='" + wipeStatus + '\'' +
                ", app='" + app + '\'' +
                ", os='" + os + '\'' +
                ", osVersion='" + osVersion + '\'' +
                ", bitness='" + bitness + '\'' +
                ", location='" + location + '\'' +
                ", extraInfo='" + extraInfo + '\'' +
                ", runtime='" + runtime + '\'' +
                ", firstConnect=" + firstConnect +
                ", lastConnect=" + lastConnect +
                '}';
    }

}
