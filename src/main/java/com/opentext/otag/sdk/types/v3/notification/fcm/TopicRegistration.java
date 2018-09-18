package com.opentext.otag.sdk.types.v3.notification.fcm;

import java.io.Serializable;
import java.util.Objects;

public class TopicRegistration implements Serializable {

    private String clientId;

    public TopicRegistration() {
    }

    public TopicRegistration(String clientId) {
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopicRegistration that = (TopicRegistration) o;
        return Objects.equals(clientId, that.clientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId);
    }

    @Override
    public String toString() {
        return "TopicRegistration{" +
                "clientId='" + clientId + '\'' +
                '}';
    }
}
