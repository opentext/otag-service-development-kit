package com.opentext.otag.sdk.types.v3.notification.fcm;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * See https://firebase.google.com/docs/reference/admin/java/reference/com/google/firebase/messaging/ApnsConfig
 */
public class ApnsConfig implements Serializable {

    // The token is in Base64URL-encoded JWT format, specified as bearer <provider token>
    public static final String AUTHORIZATION_HEADER = "authorization";

    // A canonical UUID that identifies the notification
    public static final String APNS_ID_HEADER = "apns-id";

    // A UNIX epoch date expressed in seconds (UTC).
    public static final String APNS_EXPIRATION_HEADER = "apns-expiration";

    // 10–Send the push message immediately, or 5—Send the push message at a time that takes into account
    // power considerations for the device
    public static final String APNS_PRIORITY_HEADER = "apns-priority";

    public static final String APNS_TOPIC_HEADER = "apns-topic";

    // Multiple notifications with the same collapse identifier are displayed to the user as a single notification
    public static final String APNS_COLLAPSE_KEY_HEADER = "apns-collapse-id";

    /**
     * The Aps object is mandatory in the eventual Firebaase message so provide a default value;
     */
    private Aps aps = new Aps();

    private Map<String, String> headers = new HashMap<>();

    private Map<String, Object> payload = new HashMap<>();

    public ApnsConfig() {
    }

    public Aps getAps() {
        return aps;
    }

    public void setAps(Aps aps) {
        this.aps = aps;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }

    @JsonIgnore
    public void setCollapseKey(String awgUnreadMessagesCollapseKey) {
        headers.put(APNS_COLLAPSE_KEY_HEADER, awgUnreadMessagesCollapseKey);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApnsConfig that = (ApnsConfig) o;
        return Objects.equals(aps, that.aps) &&
                Objects.equals(headers, that.headers) &&
                Objects.equals(payload, that.payload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aps, headers, payload);
    }

    @Override
    public String toString() {
        return "ApnsConfig{" +
                "aps=" + aps +
                ", headers=" + mapToString(headers) +
                ", payload=" + mapToString(payload) +
                '}';
    }

    private String mapToString(Map<?, ?> toPrint) {
        if (toPrint == null) {
            return "[]";
        }

        return toPrint.entrySet()
                .stream()
                .map(e -> e.getKey() + "=\"" + e.getValue() + "\"")
                .collect(Collectors.joining(", "));
    }

}
