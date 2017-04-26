/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.apps;

import com.opentext.otag.sdk.types.v3.SDKType;
import com.opentext.otag.sdk.types.v3.api.SDKCallInfo;
import com.opentext.otag.sdk.types.v3.client.ClientInfo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a client application that can make use of the Gateway API's. The
 * iOS and Android mobile apps are runtimes for example, and so is the Gateway
 * administration UI. Clients can indicate the runtime they represent on login via
 * the client representation we require.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.2
 *
 * @see ClientInfo#runtime
 */
public class Runtime extends SDKType implements Serializable {

    private String name;

    private String displayName;

    private String description;

    private String downloadUrl;

    /**
     * Used with mobile AppWorks Runtimes. Custom URI schemes can be registered with
     * the mobile OS (Android/iOS) so that links executed with a specific scheme will
     * pass data to the registered app/s. We differentiate in case multiple variants of
     * the client are installed on a single device.
     * <p>
     * For example 'x-otm' and 'x-otm-enterprise' are currently in use for the AppwWorks clients.
     */
    private String customUriScheme;

    /**
     * List of the app names associated with this runtime. If any runtimes are set
     * for an app, and its isForAllRuntimes flag is 'false' then only those
     * runtimes have access to that app.
     */
    private Set<String> apps = new HashSet<>();

    public Runtime() {
    }

    public Runtime(String name, Set<String> apps) {
        this.name = name;
        this.apps = apps;
    }

    public Runtime(String name, String displayName, String description,
                   String downloadUrl, String customUriScheme, Set<String> apps) {
        this.name = name;
        this.displayName = displayName;
        this.description = description;
        this.downloadUrl = downloadUrl;
        this.customUriScheme = customUriScheme;
        this.apps = apps;
    }

    public Runtime(SDKCallInfo sdkCallInfo, String name, String displayName, String description,
                   String downloadUrl, String customUriScheme, Set<String> apps) {
        super(sdkCallInfo);
        this.name = name;
        this.displayName = displayName;
        this.description = description;
        this.downloadUrl = downloadUrl;
        this.customUriScheme = customUriScheme;
        this.apps = apps;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public String getCustomUriScheme() {
        return customUriScheme;
    }

    public Set<String> getApps() {
        return apps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Runtime runtime = (Runtime) o;

        return name != null ? name.equals(runtime.name) : runtime.name == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Runtime{" +
                "name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", description='" + description + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", customUriScheme='" + customUriScheme + '\'' +
                ", apps=" + apps +
                '}';
    }

}

