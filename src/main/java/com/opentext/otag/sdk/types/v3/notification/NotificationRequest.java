/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.notification;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A free-form notification request that can be issued to AppWorks Gateway's connected
 * clients or users. A client represents a particular device using the Gateway's
 * services but a user is an actual user as recognised by OTDS.
 * <p>
 * These notifications are part of the legacy AppWorks functionality.
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.2
 */
public class NotificationRequest implements Serializable {

    /**
     * A message can be sent on behalf of a specific collaborating application (also known
     * as a Providers or trusted server). These trusted providers share a known secret
     * with the AppWorks Gateway, this value. It is not mandatory.
     */
    private String providerKey;

    /**
     * Free-form JSON payload.
     */
    private String jsonPayload;

    /**
     * A collection of connected client ids to issue this notification to.
     */
    private Set<String> clientIds = new HashSet<>();

    /**
     * A collection of canonical (OTDS) user id values to send the notification to.
     */
    private Set<String> userIds = new HashSet<>();

    public NotificationRequest() {
    }

    public NotificationRequest(String providerKey, String jsonPayload,
                               Set<String> clientIds, Set<String> userIds) {
        this.providerKey = providerKey;
        this.jsonPayload = jsonPayload;
        this.clientIds = clientIds;
        this.userIds = userIds;
    }

    public NotificationRequest(String jsonPayload, Set<String> clientIds, Set<String> userIds) {
        this.jsonPayload = jsonPayload;
        this.clientIds = clientIds;
        this.userIds = userIds;
    }

    public String getProviderKey() {
        return providerKey;
    }

    public String getJsonPayload() {
        return jsonPayload;
    }

    public Set<String> getClientIds() {
        return clientIds;
    }

    public Set<String> getUserIds() {
        return userIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NotificationRequest that = (NotificationRequest) o;

        if (providerKey != null ? !providerKey.equals(that.providerKey) : that.providerKey != null) return false;
        if (jsonPayload != null ? !jsonPayload.equals(that.jsonPayload) : that.jsonPayload != null) return false;
        if (clientIds != null ? !clientIds.equals(that.clientIds) : that.clientIds != null) return false;
        return userIds != null ? userIds.equals(that.userIds) : that.userIds == null;
    }

    @Override
    public int hashCode() {
        int result = providerKey != null ? providerKey.hashCode() : 0;
        result = 31 * result + (jsonPayload != null ? jsonPayload.hashCode() : 0);
        result = 31 * result + (clientIds != null ? clientIds.hashCode() : 0);
        result = 31 * result + (userIds != null ? userIds.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NotificationRequest{" +
                "providerKey='" + providerKey + '\'' +
                ", jsonPayload='" + jsonPayload + '\'' +
                ", clientIds=" + clientIds +
                ", userIds=" + userIds +
                '}';
    }
    
}

