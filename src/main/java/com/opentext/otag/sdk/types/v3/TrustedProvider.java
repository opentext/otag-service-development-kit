/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3;

import java.io.Serializable;

/**
 * This class represents a service that collaborates with an AppWorks
 * Gateway deployment. The trusted provider retains an API key that may
 * be used with a sub section of the Gateways API's.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.2
 */
public class TrustedProvider extends SDKType implements Serializable {

    /**
     * Unique provider name.
     */
    private String name;

    /**
     * Gateway API key.
     */
    private String key;

    /**
     * Additional info about this provider.
     */
    private String contactInfo;

    public TrustedProvider() {
    }

    public TrustedProvider(String name, String key, String contactInfo) {
        this.name = name;
        this.key = key;
        this.contactInfo = contactInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrustedProvider that = (TrustedProvider) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TrustedProvider{" +
                "name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                '}';
    }

}

