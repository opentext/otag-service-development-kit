/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.otag.sdk.types.v3.notification;

import com.opentext.otag.sdk.util.StringUtil;

import java.io.Serializable;
import java.util.*;

/**
 * Type that represents a request to send a push notification on a Gateway clients
 * behalf to known mobile clients that are connected to the Gateway. These messages
 * can be actual notifications that will appear in a devices native tray, or they
 * can just be pure data for interpretation by an app hosted within our runtime, or both.
 * <p>
 * A Builder implementation is provided that adds validation to the construction of
 * requests. The recipient identifier type fields (clientIds, users, and runtimes) are used
 * additively when forming the actual push requests, duplicates are handled at the API.
 * <p>
 * Full message:
 * <p>
 *  {
 *      "broadcast": false,
 *      "clientIds": ["id1", "id2", "id3"],
 *      "users": ["userId1", "userId2", "userId3"],
 *      "otdsGroups": ["otadmins"],
 *      "runtimes": [],
 *      "title": "My Title",
 *      "summary": "Notification summary",
 *      "data": {
 *          "target": "myAppWorksApp",
 *          "summary": "Data summary",
 *          "created": 1438872035,
 *          // extend GeneralPayload to add your own fields :)
 *          "myAppWorksApp": {
 *              "action": "showView",
 *              "args": {
 *                  "viewId": "showReceivedData"
 *              }
 *          }
 *      }
 *  }
 *
 * @author Rhys Evans rhyse@opentext.com
 * @version 16.1
 *
 * @see GeneralPayload
 */
public class ClientPushNotificationRequest implements Serializable {

    /**
     * Should we send the notification to all eligible connected clients? The
     * Gateway may choose not to include clients that haven't been in touch
     * recently.
     */
    private boolean broadcast = false;

    /**
     * AppWorks uses the {@code Client} entity to record the push notification
     * registration tokens, so we must therefore identify clients to send them
     * such notifications.
     */
    private Set<String> clientIds = new HashSet<>();

    /**
     * Clients can be associated with users too, if user ids are provided they
     * will be resolved against known eligible clients.
     */
    private Set<String> users = new HashSet<>();

    /**
     * Clients can be associated with users, which may be in a particular OTDS group.
     */
    private Set<String> otdsGroups = new HashSet<>();

    /**
     * Send the request to users of specific runtimes. Runtimes are identified
     * by their unique name, and represent a particular client of the Gateway. The iOS
     * client and Android client are both examples of a runtime.
     *
     * @see Runtime
     */
    private Set<String> runtimes = new HashSet<>();

    /**
     * Notification title (optional). If included the message will be a
     * full native notification.
     */
    private String title;

    /**
     * Notification summary (optional). If included the message will be a
     * full native notification.
     */
    private String summary;

    /**
     * Represents the data content of the notification. Allows us to target a
     * specific AppWorks app hosted within the mobile runtime.
     */
    private Map<String, Object> data = new HashMap<>();

    // required for serialization
    @SuppressWarnings("unused")
    public ClientPushNotificationRequest() {
    }

    private ClientPushNotificationRequest(Builder builder) {
        broadcast = builder.broadcast;
        clientIds = builder.clientIds;
        users = builder.users;
        otdsGroups = builder.otdsGroups;
        runtimes = builder.runtimes;
        title = builder.title;
        summary = builder.summary;
        data = builder.data;
    }

    public Set<String> getClientIds() {
        return clientIds;
    }

    public Set<String> getUsers() {
        return users;
    }

    public boolean isBroadcast() {
        return broadcast;
    }

    public Set<String> getOtdsGroups() {
        return otdsGroups;
    }

    public Set<String> getRuntimes() {
        return runtimes;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public static final class Builder {
        private boolean broadcast;
        private Set<String> clientIds;
        private Set<String> users;
        private Set<String> otdsGroups;
        private Set<String> runtimes;

        private String title;
        private String summary;
        private Map<String, Object> data;

        public Builder() {
            broadcast = false;
            clientIds = new HashSet<>();
            users = new HashSet<>();
            otdsGroups = new HashSet<>();
            runtimes = new HashSet<>();
            data = new HashMap<>();
        }

        public Builder(ClientPushNotificationRequest initState) {
            broadcast = initState.broadcast;
            clientIds = initState.clientIds;
            users = initState.users;
            otdsGroups = initState.otdsGroups;
            runtimes = initState.runtimes;
            title = initState.title;
            summary = initState.summary;
            data = initState.data;
        }

        public Builder sendToAll(boolean value) {
            this.broadcast = value;
            return this;
        }

        public Builder clients(Set<String> value) {
            this.clientIds = value;
            return this;
        }

        public Builder addClient(String value) {
            this.clientIds.add(value);
            return this;
        }

        public Builder users(Set<String> value) {
            this.users = value;
            return this;
        }

        public Builder addUser(String value) {
            this.users.add(value);
            return this;
        }

        public Builder otdsGroups(Set<String> value) {
            this.otdsGroups = value;
            return this;
        }

        public Builder addGroup(String value) {
            this.otdsGroups.add(value);
            return this;
        }

        public Builder runtimes(Set<String> value) {
            this.runtimes = value;
            return this;
        }

        public Builder addRuntime(String value) {
            this.runtimes.add(value);
            return this;
        }

        public Builder title(String value) {
            this.title = value;
            return this;
        }

        public Builder summary(String value) {
            this.summary = value;
            return this;
        }

        public Builder data(Map<String, Object> value) {
            this.data = value;
            return this;
        }

        public Builder addData(String key, Object value) {
            this.data.put(key, value);
            return this;
        }

        public ClientPushNotificationRequest build() {
            // validate content, the Gateway will reject requests that aren't correctly formed
            if (title == null && summary == null && data.isEmpty())
                throw new RuntimeException(
                        "No data found in request, please supply some data or a title and summary (or both)");

            if (!broadcast && clientIds.isEmpty() && users.isEmpty() && otdsGroups.isEmpty() && runtimes.isEmpty())
                throw new RuntimeException("No recipients found to send the notification to?");

            if (broadcast && (!clientIds.isEmpty() && !users.isEmpty() && !otdsGroups.isEmpty() && !runtimes.isEmpty()))
                throw new RuntimeException("Broadcast was set to true but recipients were also provided?");

            return new ClientPushNotificationRequest(this);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientPushNotificationRequest that = (ClientPushNotificationRequest) o;

        if (broadcast != that.broadcast) return false;
        if (clientIds != null ? !clientIds.equals(that.clientIds) : that.clientIds != null) return false;
        if (users != null ? !users.equals(that.users) : that.users != null) return false;
        if (otdsGroups != null ? !otdsGroups.equals(that.otdsGroups) : that.otdsGroups != null) return false;
        if (runtimes != null ? !runtimes.equals(that.runtimes) : that.runtimes != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (summary != null ? !summary.equals(that.summary) : that.summary != null) return false;
        return data != null ? data.equals(that.data) : that.data == null;
    }

    @Override
    public int hashCode() {
        int result = (broadcast ? 1 : 0);
        result = 31 * result + (clientIds != null ? clientIds.hashCode() : 0);
        result = 31 * result + (users != null ? users.hashCode() : 0);
        result = 31 * result + (otdsGroups != null ? otdsGroups.hashCode() : 0);
        result = 31 * result + (runtimes != null ? runtimes.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        // print data keys if we have them
        String dataString = (data != null && !data.isEmpty()) ?
                StringUtil.toListString(new ArrayList<>(data.keySet()), true) : "{}";
        return "ClientPushNotificationRequest{" +
                "broadcast=" + broadcast +
                ", clientIds=" + clientIds +
                ", users=" + users +
                ", otdsGroups=" + otdsGroups +
                ", runtimes=" + runtimes +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", data.keys=" + dataString +
                '}';
    }

}